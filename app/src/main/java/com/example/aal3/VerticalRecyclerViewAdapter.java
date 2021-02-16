package com.example.aal3;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aal3.Models.HorizontalModel;
import com.example.aal3.Models.VerticalModel;

import java.util.ArrayList;
import java.util.Iterator;

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.VerticalRVViewHolder> {

    Context context;
    ArrayList<VerticalModel> arrayList;
    public static final String CardPreferences = "CardPrefs";
    SharedPreferences sharedpreferences;

    public VerticalRecyclerViewAdapter(Context context, ArrayList<VerticalModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        sharedpreferences = context.getSharedPreferences(CardPreferences, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public VerticalRecyclerViewAdapter.VerticalRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical, parent, false);

        return new VerticalRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalRecyclerViewAdapter.VerticalRVViewHolder holder, int position) {

        VerticalModel verticalModel = arrayList.get(position);
        String type = verticalModel.getDesignType();
        int height = verticalModel.getHeight();
        boolean is_scrollable = verticalModel.isIs_scrollable();
        ArrayList<HorizontalModel> singleItem = verticalModel.getCards();

        // Removing Cards with "Dismiss Now" selected
        if (type.equals("HC3")) {
            Iterator<HorizontalModel> iterator = singleItem.iterator();
            while (iterator.hasNext()) {
                HorizontalModel p = iterator.next();
                if (!sharedpreferences.getBoolean(p.getName(), true)) iterator.remove();
            }
        }

        HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(context, singleItem, type, height, is_scrollable);

        // Setting up adapter of Horizontal RecyclerView
        // Handles  'is_scrollable' attribute
        if (!is_scrollable && type.equals("HC1")) {
            final GridLayoutManager layoutManager = new GridLayoutManager(context, singleItem.size());
            holder.recyclerView.setLayoutManager(layoutManager);
            holder.recyclerView.setAdapter(horizontalRecyclerViewAdapter);
            holder.recyclerView.setClipToPadding(false);
            holder.recyclerView.setClipChildren(false);
        } else {
            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setAdapter(horizontalRecyclerViewAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VerticalRVViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;

        public VerticalRVViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.horizontalRecyclerView);
        }
    }
}
