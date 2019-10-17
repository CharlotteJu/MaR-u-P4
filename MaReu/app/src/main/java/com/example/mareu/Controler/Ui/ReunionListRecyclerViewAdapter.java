package com.example.mareu.Controler.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.Model.Reunion;
import com.example.mareu.R;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReunionListRecyclerViewAdapter extends RecyclerView.Adapter<ReunionListRecyclerViewAdapter.ViewHolder>
{

    private List<Reunion> mReunions;

    public ReunionListRecyclerViewAdapter(List<Reunion> mReunions) {
        this.mReunions = mReunions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_one_reunion, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Reunion reunion = mReunions.get(position);
        holder.updateInfos(reunion);
    }



    @Override
    public int getItemCount() {
        return mReunions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.item_list_name)
        public TextView mName;
        @BindView(R.id.item_list_mails)
        public TextView mMails;
        @BindView(R.id.item_list_delete_button)
        public Button mButtonDelete;
        @BindView(R.id.item_list_avatar)
        public ImageView mAvatar;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /**
         * Update the elements of the reunion
         * @param reunion
         */
        public void updateInfos (Reunion reunion)
        {
            mName.setText(reunion.getmRoom() + " - " + reunion.getmTime() + " - " + reunion.getmSubject());

            // Cast the list in String
            String mails = "";
            for (int i = 0; i < reunion.getmEmails().size(); i ++)
            {
                mails += reunion.getmEmails().get(i) + ", ";
            }

            mMails.setText(mails);
        }
    }
}
