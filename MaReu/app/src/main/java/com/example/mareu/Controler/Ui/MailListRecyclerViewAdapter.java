package com.example.mareu.Controler.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MailListRecyclerViewAdapter extends RecyclerView.Adapter<MailListRecyclerViewAdapter.ViewHolder>
{

    public interface MailsToDelete{
        void clickToDelete(int position);
    }

    private List<String> mMails;
    private MailsToDelete mMailsToDelete;

    public MailListRecyclerViewAdapter(List<String> mMails, MailsToDelete mailsToDelete) {
        this.mMails = mMails;
        this.mMailsToDelete = mailsToDelete;
    }

    @Override
    public MailListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_one_mail, parent, false);
        return new MailListRecyclerViewAdapter.ViewHolder(v, mMailsToDelete);
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.fom_mail)
        public TextView mMail;
        @BindView(R.id.fom_delete_button)
        public ImageButton mDeleteButton;

        private MailsToDelete mMailsToDelete;

        public ViewHolder(@NonNull View itemView, MailsToDelete mailsToDelete) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mMailsToDelete = mailsToDelete;
            mDeleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mMailsToDelete.clickToDelete(getAdapterPosition());

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
