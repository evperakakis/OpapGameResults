package com.psifiaka.light.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    private List<ListItem> listItems;
    private Context context;


    public RecyclerViewAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);

        holder.textViewDrawTime.setText(listItem.getDrawTime());
        holder.textViewDrawNo.setText(listItem.getDrawNo());
        holder.textViewDrawResults.setText(listItem.getDrawResults());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDrawTime;
        public TextView textViewDrawNo;
        public TextView textViewDrawResults;



        public ViewHolder(View itemView) {
            super(itemView);

            textViewDrawTime = itemView.findViewById(R.id.textViewDrawTime);
            textViewDrawNo = itemView.findViewById(R.id.textViewDrawNo);
            textViewDrawResults = itemView.findViewById(R.id.textViewDrawResults);

        }
    }
}
