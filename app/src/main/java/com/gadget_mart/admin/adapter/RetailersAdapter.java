package com.gadget_mart.admin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.admin.R;
import com.gadget_mart.admin.async.ImageLoadTask;
import com.gadget_mart.admin.model.RetailerModel;

import java.util.List;

public class RetailersAdapter extends RecyclerView.Adapter<RetailersAdapter.RetailersHolder> {

    private List<RetailerModel> retailerModelList;
    private Context context;
    private final OnItemClickListener listener;

    public RetailersAdapter(Context context, List<RetailerModel> modelList, OnItemClickListener listener) {
        this.context = context;
        this.retailerModelList = modelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RetailersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reatiler_custom_layout, parent, false);
        return new RetailersHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RetailersHolder holder, int position) {
        holder.bind(retailerModelList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return retailerModelList.size();
    }

    public static class RetailersHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;
        private final Button enable_button;
        private final Button disable_button;
        Context ctx;

        public RetailersHolder(@NonNull View itemView, Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.retailer_image);
            textView = itemView.findViewById(R.id.retailer_text);
            enable_button = itemView.findViewById(R.id.enable_button);
            disable_button = itemView.findViewById(R.id.disable_button);
            ctx = context;
        }

        public void bind(final RetailerModel retailerModel, final OnItemClickListener listener) {
            textView.setText(retailerModel.getRetailerName());
            if(retailerModel.getIdretailers() == 1 || retailerModel.getIdretailers() == 2 || retailerModel.getIdretailers() == 3) {
                new ImageLoadTask(retailerModel.getRetailerImage(), imageView).execute();
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(retailerModel);
                }
            });

            if (retailerModel.getAvailable() == true) {
                enable_button.setClickable(false);
                enable_button.setBackground(ctx.getResources().getDrawable(R.drawable.gray_button_background));
                disable_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        enable_button.setBackground(ctx.getResources().getDrawable(R.drawable.enable_button_background));
                    }
                });
            } else {
                disable_button.setClickable(false);
                disable_button.setBackground(ctx.getResources().getDrawable(R.drawable.gray_button_background));
                enable_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        disable_button.setBackground(ctx.getResources().getDrawable(R.drawable.disable_button_background));
                    }
                });
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RetailerModel retailerModel);
    }

}
