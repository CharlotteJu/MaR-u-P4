package com.example.mareu.Controler.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MailListRecyclerViewAdapter extends RecyclerView.Adapter<MailListRecyclerViewAdapter.ViewHolder>
{

    private List<String> mMails;

    public MailListRecyclerViewAdapter(List<String> mMails) {
        this.mMails = mMails;
    }

    @Override
    public MailListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_one_mail, parent, false);
        return new MailListRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String mail = mMails.get(position);
        holder.updateInfos(mail);
    }


    @Override
    public int getItemCount() {
        return mMails.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.fom_mail)
        public TextView mMail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Display the mail
         */
        public void updateInfos (String mail)
        {
            mMail.setText(mail);
        }
    }
}
