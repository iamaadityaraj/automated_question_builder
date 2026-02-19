package com.example.questionbuilder.Adapter;
import com.example.questionbuilder.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResponsesAdapter extends RecyclerView.Adapter<ResponsesAdapter.ResponseViewHolder> {

    private List<String> responsesList;

    public ResponsesAdapter(List<String> responsesList) {
        this.responsesList = responsesList;
    }

    @NonNull
    @Override
    public ResponseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_item, parent, false);
        return new ResponseViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ResponseViewHolder holder, int position) {
        String response = responsesList.get(position);
        holder.responseTextView.setText(response);
    }

    @Override
    public int getItemCount() {
        return responsesList.size();
    }

    public static class ResponseViewHolder extends RecyclerView.ViewHolder {
        public TextView responseTextView;

        public ResponseViewHolder(View itemView) {
            super(itemView);
            responseTextView = itemView.findViewById(R.id.responseTextView);
        }
    }
}
