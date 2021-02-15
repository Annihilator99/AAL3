package com.example.aal3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aal3.Models.CTA;
import com.example.aal3.Models.HorizontalModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<HorizontalModel> arrayList;
    String type;
    int cardHeight;
    boolean is_scrollable;

    public HorizontalRecyclerViewAdapter(Context context, ArrayList<HorizontalModel> arrayList, String type, int height, boolean is_scrollable) {
        this.context = context;
        this.arrayList = arrayList;
        this.type = type;
        this.cardHeight = height;
        this.is_scrollable = is_scrollable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case HorizontalModel.HC1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_display_card, parent, false);
                return new HC1ViewHolder(view);

            case HorizontalModel.HC3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.big_display_card, parent, false);
                return new HC3ViewHolder(view);

            case HorizontalModel.HC5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card, parent, false);
                return new HC5ViewHolder(view);

            case HorizontalModel.HC6:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_card_with_arrow, parent, false);
                return new HC6ViewHolder(view);

            case HorizontalModel.HC9:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_width_card, parent, false);
                return new HC9ViewHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {

        switch (type) {
            case "HC1":
                return HorizontalModel.HC1;
            case "HC3":
                return HorizontalModel.HC3;
            case "HC5":
                return HorizontalModel.HC5;
            case "HC6":
                return HorizontalModel.HC6;
            case "HC9":
                return HorizontalModel.HC9;
        }

        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final HorizontalModel horizontalModel = arrayList.get(position);

        if (horizontalModel != null) {
            switch (holder.getItemViewType()) {
                case HorizontalModel.HC1:
                    HC1ViewHolder hc1ViewHolder = (HC1ViewHolder) holder;
                    hc1ViewHolder.text.setText(horizontalModel.getTitle());
                    String hc1ImgURL = null;
                    hc1ImgURL = horizontalModel.getIcon().getImage_url();
                    Picasso.with(context).load(hc1ImgURL).into(hc1ViewHolder.img);

                    break;

                case HorizontalModel.HC3:
                    HC3ViewHolder hc3ViewHolder = (HC3ViewHolder) holder;

//                    final Gson gson= new Gson();
//                    final CTA cta = gson.fromJson(horizontalModel.getCta().getAsJsonObject().toString(),CTA.class);
                    final ArrayList<CTA> cta = new Gson().fromJson(horizontalModel.getCta().toString(), new TypeToken<List<CTA>>() {
                    }.getType());

                    Log.d("cta", cta.get(0).getText());


                    hc3ViewHolder.button.setText(cta.get(0).getText());
                    hc3ViewHolder.button.setTextColor(Color.parseColor(cta.get(0).getText_color()));
                    hc3ViewHolder.button.setBackgroundColor(Color.parseColor(cta.get(0).getBg_color()));
                    hc3ViewHolder.button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            openURL(cta.get(0).getAction_url());
                        }
                    });

                    hc3ViewHolder.title.setText(horizontalModel.getTitle());
                    hc3ViewHolder.description.setText(horizontalModel.getDescription());

                    String hc3ImgURL = null;
                    if (horizontalModel.getIcon() != null) {
                        hc3ImgURL = horizontalModel.getIcon().getImage_url();
                    }

                    Picasso.with(context).load(hc3ImgURL).into(hc3ViewHolder.img);

                    break;

                case HorizontalModel.HC5:
                    HC5ViewHolder hc5ViewHolder = (HC5ViewHolder) holder;
                    Log.d("image data ", horizontalModel.getBg_image().toString());
                    String hc5ImgURL = null;
                    hc5ImgURL = horizontalModel.getBg_image().getImage_url();
                    Picasso.with(context).load(hc5ImgURL).into(hc5ViewHolder.img);
                    break;

                case HorizontalModel.HC6:
                    HC6ViewHolder hc6ViewHolder = (HC6ViewHolder) holder;
                    hc6ViewHolder.text.setText(horizontalModel.getTitle());
                    String hc6ImgURL = null;
                    hc6ImgURL = horizontalModel.getIcon().getImage_url();
                    Picasso.with(context).load(hc6ImgURL).into(hc6ViewHolder.img);
                    break;

                case HorizontalModel.HC9:
                    HC9ViewHolder hc9ViewHolder = (HC9ViewHolder) holder;

                    String hc9ImgURL = null;
                    hc9ImgURL = horizontalModel.getBg_image().getImage_url();
                    hc9ViewHolder.img.getLayoutParams().height = (int) (cardHeight * (context.getResources().getDisplayMetrics().density));
                    Picasso.with(context).load(hc9ImgURL).into(hc9ViewHolder.img);

                    break;
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (horizontalModel.getUrl() != null) {
                    openURL(horizontalModel.getUrl());
                }
            }
        });

    }

    private void openURL(String url) {
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HC1ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView img;
        CardView cardView;

        public HC1ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text = itemView.findViewById(R.id.smallDisplayCardTitle);
            this.img = itemView.findViewById(R.id.smallDisplayCardIcon);
            this.cardView = itemView.findViewById(R.id.HC1CardView);
        }
    }

    public class HC3ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView img;

        Button button;

        public HC3ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.bigDisplayCardTitle);
            this.description = itemView.findViewById(R.id.bigDisplayCardDescription);
            this.img = itemView.findViewById(R.id.HC3Image);
            this.button = itemView.findViewById(R.id.bigDisplayCardButton);

        }
    }

    public class HC5ViewHolder extends RecyclerView.ViewHolder {


        ImageView img;

        public HC5ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.HC5Image);
        }
    }

    public class HC6ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView img;

        public HC6ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text = itemView.findViewById(R.id.HC6Title);
            this.img = itemView.findViewById(R.id.HC6Image);
        }
    }

    public class HC9ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public HC9ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.HC9Image);

        }
    }

//    public class HorizontalRVViewHolder extends RecyclerView.ViewHolder {
//
//        TextView textViewTitle;
//        ImageView imageView;
//
//
//        public HorizontalRVViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textViewTitle = itemView.findViewById(R.id.txtTitle2);
//            imageView = itemView.findViewById(R.id.image);
//        }
//
//    }


}
