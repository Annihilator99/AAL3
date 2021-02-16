package com.example.aal3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Objects;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<HorizontalModel> arrayList;
    String type;
    int cardHeight;
    boolean is_scrollable;
    public static final String CardPreferences = "CardPrefs";
    SharedPreferences sharedpreferences;

    public HorizontalRecyclerViewAdapter(Context context, ArrayList<HorizontalModel> arrayList, String type, int height, boolean is_scrollable) {
        this.context = context;
        this.arrayList = arrayList;
        this.type = type;
        this.cardHeight = height;
        this.is_scrollable = is_scrollable;
        sharedpreferences = context.getSharedPreferences(CardPreferences, Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        // Different Layouts for different Cards
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final HorizontalModel horizontalModel = arrayList.get(position);

        // Different ViewHolders for different Cards
        if (horizontalModel != null) {
            switch (holder.getItemViewType()) {
                case HorizontalModel.HC1:
                    HC1ViewHolder hc1ViewHolder = (HC1ViewHolder) holder;

                    if (horizontalModel.getTitle() != null) {
                        hc1ViewHolder.title.setText(horizontalModel.getTitle());
                    }

                    if (horizontalModel.getIcon().getImage_url() != null) {
                        Picasso.with(context).load(Objects.requireNonNull(horizontalModel.getIcon().getImage_url())).into(hc1ViewHolder.img);
                    }

                    break;

                case HorizontalModel.HC3:
                    final HC3ViewHolder hc3ViewHolder = (HC3ViewHolder) holder;

                    // Converts JsonArray to ArrayList of CTA
                    final ArrayList<CTA> cta = new Gson().fromJson(horizontalModel.getCta().toString(), new TypeToken<List<CTA>>() {
                    }.getType());

                    Log.d("cta", cta.get(0).getText());

                    if (cta.get(0).getText() != null) {
                        hc3ViewHolder.actionButton.setText(cta.get(0).getText());
                    }

                    if (cta.get(0).getText_color() != null) {
                        hc3ViewHolder.actionButton.setTextColor(Color.parseColor(cta.get(0).getText_color()));
                    }

                    if (cta.get(0).getBg_color() != null) {
                        hc3ViewHolder.actionButton.setBackgroundColor(Color.parseColor(cta.get(0).getBg_color()));
                    }

                    if (horizontalModel.getTitle() != null) {
                        hc3ViewHolder.title.setText(horizontalModel.getTitle());
                    }

                    if (horizontalModel.getDescription() != null) {
                        hc3ViewHolder.description.setText(horizontalModel.getDescription());
                    }

                    hc3ViewHolder.sideView.setVisibility(View.GONE);

                    // Action Button ClickListener
                    hc3ViewHolder.actionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (cta.get(0).getAction_url() != null) {
                                openURL(cta.get(0).getAction_url());
                            }
                        }
                    });


                    // Long Press Click Listener of Big Display card
                    hc3ViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            // Slide Transition
                            TransitionManager.beginDelayedTransition(hc3ViewHolder.sideView,
                                    new AutoTransition());
                            hc3ViewHolder.sideView.setVisibility(View.VISIBLE);

                            // Dismiss Now Click Listener
                            hc3ViewHolder.dismissLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    // Stores info in Shared Preferences
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putBoolean(horizontalModel.getName(), false);
                                    editor.apply();

                                    arrayList.remove(position);
                                    notifyDataSetChanged();
                                }
                            });


                            // Remind Later Click Listener
                            hc3ViewHolder.remindLinearLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    arrayList.remove(position);
                                    notifyDataSetChanged();
                                }
                            });
                            return true;
                        }
                    });

                    if (horizontalModel.getBg_image() != null && horizontalModel.getBg_image().getAspect_ratio() != null) {

                        // Setting Dimensions of Image using 'aspect_ratio'
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        ((MainActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        int w = displayMetrics.widthPixels - (int) (20 * context.getResources().getDisplayMetrics().density);
                        int l = (int) (w / (Double.parseDouble(horizontalModel.getBg_image().getAspect_ratio())));
                        Picasso.with(context).load(horizontalModel.getBg_image().getImage_url()).resize(w, l).into(hc3ViewHolder.img);
                    }

                    break;

                case HorizontalModel.HC5:
                    HC5ViewHolder hc5ViewHolder = (HC5ViewHolder) holder;

                    if (horizontalModel.getBg_image().getImage_url() != null) {
                        Picasso.with(context).load(horizontalModel.getBg_image().getImage_url()).into(hc5ViewHolder.img);
                    }

                    break;

                case HorizontalModel.HC6:
                    HC6ViewHolder hc6ViewHolder = (HC6ViewHolder) holder;

                    if (horizontalModel.getTitle() != null) {
                        hc6ViewHolder.text.setText(horizontalModel.getTitle());
                    }

                    if (horizontalModel.getIcon().getImage_url() != null) {
                        Picasso.with(context).load(horizontalModel.getIcon().getImage_url()).into(hc6ViewHolder.img);
                    }
                    break;

                case HorizontalModel.HC9:
                    HC9ViewHolder hc9ViewHolder = (HC9ViewHolder) holder;

                    if (horizontalModel.getBg_image().getImage_url() != null && horizontalModel.getBg_image().getAspect_ratio() != null) {
                        // Setting Dimensions of Image using 'aspect_ratio
                        int l = (int) (cardHeight * (context.getResources().getDisplayMetrics().density));
                        int w = (int) (l * (Double.parseDouble(horizontalModel.getBg_image().getAspect_ratio())));
                        Picasso.with(context).load(horizontalModel.getBg_image().getImage_url()).resize(w, l).into(hc9ViewHolder.img);
                    }

                    break;
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
    }


    // Function to open URL onCLick
    private void openURL(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class HC1ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView img;
        CardView cardView;

        public HC1ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.HC1Title);
            this.img = itemView.findViewById(R.id.HC1Icon);
            this.cardView = itemView.findViewById(R.id.HC1CardView);
        }
    }

    public static class HC3ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView img;
        LinearLayout sideView;
        CardView cardView;
        LinearLayout remindLinearLayout;
        LinearLayout dismissLinearLayout;

        Button actionButton;

        public HC3ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.HC3Title);
            this.description = itemView.findViewById(R.id.HC3Description);
            this.img = itemView.findViewById(R.id.HC3Image);
            this.actionButton = itemView.findViewById(R.id.HC3ActionButton);
            this.sideView = itemView.findViewById(R.id.HC3SideView);
            this.cardView = itemView.findViewById(R.id.HC3CardView);
            this.remindLinearLayout = itemView.findViewById(R.id.remindLaterLL);
            this.dismissLinearLayout = itemView.findViewById(R.id.dismissLL);

        }
    }

    public static class HC5ViewHolder extends RecyclerView.ViewHolder {


        ImageView img;

        public HC5ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.HC5Image);
        }
    }

    public static class HC6ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView img;

        public HC6ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text = itemView.findViewById(R.id.HC6Title);
            this.img = itemView.findViewById(R.id.HC6Image);
        }
    }

    public static class HC9ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public HC9ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.HC9Image);

        }
    }
}
