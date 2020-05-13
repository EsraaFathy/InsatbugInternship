package com.example.insatbuginternship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.ViewHolder> {
    private Context context;
    private List<ItemModel> itemModels;
    @NonNull
    @Override
    public WordsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item_desgin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsAdapter.ViewHolder holder, int position) {
        ItemModel itemModel=itemModels.get(position);
        holder.number.setText(itemModel.getNumberOfWord());
        holder.word.setText(itemModel.getTheWord());

    }

    public WordsAdapter(Context context, ArrayList<ItemModel> itemModels) {
        this.context=context;
        this.itemModels=itemModels;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView word,number;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            word=itemView.findViewById(R.id.wordText);
            number=itemView.findViewById(R.id.numberText);
        }

    }
}
