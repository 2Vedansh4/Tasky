package com.example.tasky;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder> {
    public interface ButtonClickListener {
        void onButtonClicked(int position);
    }
    public interface CuttonClickListener {
        void onCuttonClicked(int position);
    }

    private ButtonClickListener buttonClickListener;
    private  CuttonClickListener cuttonClickListener;


    Context context;
    ArrayList<ContactModel> arrContacts;

    RecyclerContactAdapter(Context context, ArrayList<ContactModel> arrContacts) {
        this.context = context;
        this.arrContacts = arrContacts;
    }

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }
    public void setCuttonClickListener(CuttonClickListener cuttonClickListener) {
        this.cuttonClickListener = cuttonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNumber.setText(arrContacts.get(position).number);
        holder.txtName.setText(arrContacts.get(position).name);
    }

    @Override
    public int getItemCount() {
        return arrContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtNumber;
        Button delete;
        Button completetask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textView1);
            txtNumber = itemView.findViewById(R.id.textView2);
            delete = itemView.findViewById(R.id.delete_button);
            completetask = itemView.findViewById(R.id.completetask);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (buttonClickListener != null) {
                            buttonClickListener.onButtonClicked(position);

                        }
                    }
                }
            });
            completetask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (cuttonClickListener != null) {
                            cuttonClickListener.onCuttonClicked(position);

                        }

                    }
                }
            });
        }
    }
}


