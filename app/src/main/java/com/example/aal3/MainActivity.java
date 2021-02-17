package com.example.aal3;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aal3.Models.VerticalModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {


    private VerticalRecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;
    private RecyclerView verticalRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        verticalRecyclerView = findViewById(R.id.verticalRecyclerView);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        APICall();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                APICall();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void APICall() {
        // API Call
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        OkHttpRequest APICall = new OkHttpRequest();
        Request request = APICall.getResponse();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "API Call Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                if (response.message().equals("OK")) {
                    try {
                        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response.body()).string());
                        Log.d("Response received -", jsonObject.toString());

                        JSONArray cardGroupsArray = jsonObject.getJSONArray("card_groups");

                        // Converting JSONArray to ArrayList
                        ArrayList<VerticalModel> verticalList = new Gson().fromJson(String.valueOf(cardGroupsArray), new TypeToken<List<VerticalModel>>() {
                        }.getType());

                        // Setting the Adapter and SwipeRefresh
                        adapter = new VerticalRecyclerViewAdapter(MainActivity.this, verticalList);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                verticalRecyclerView.setAdapter(adapter);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "JSONException", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}