package com.example.mareu.Controler.Ui;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.Controler.Fragment.ListReunionsFragment;
import com.example.mareu.Model.Reunion;
import com.example.mareu.R;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReunionListRecyclerViewAdapter extends RecyclerView.Adapter<ReunionListRecyclerViewAdapter.ViewHolder>
{

    //interface to get the click on the item
    public interface clickToDeleteInterface
    {
        void clickToDelete(int position);
    }

    private List<Reunion> mReunions;
    private int mDimenSize;
    private clickToDeleteInterface mClickToDeleteInterface;
    private ListReunionsFragment fr;

    public ReunionListRecyclerViewAdapter(List<Reunion> mReunions, int dimenSize, clickToDeleteInterface clickToDeleteInterface, ListReunionsFragment fragment) {
        this.mDimenSize = dimenSize;
        this.mClickToDeleteInterface = clickToDeleteInterface;
        fr = fragment;
        mReunions = fr.mReunions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_one_reunion, parent, false);
        return new ViewHolder(v, mClickToDeleteInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Reunion reunion = fr.mReunions.get(position);
        holder.updateInfos(reunion);
    }

    @Override
    public int getItemCount() {
        return fr.mReunions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.item_list_name)
        public TextView mName;
        @BindView(R.id.item_list_mails)
        public TextView mMails;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mButtonDelete;
        @BindView(R.id.item_list_avatar)
        public ImageView mAvatar;

        private clickToDeleteInterface mClickToDeleteInterface;


        public ViewHolder(@NonNull View itemView, clickToDeleteInterface clickToDeleteInterface) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mClickToDeleteInterface = clickToDeleteInterface;
            mButtonDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickToDeleteInterface.clickToDelete(getAdapterPosition());
        }

        /**
         * Update the elements of the reunion
         * @param reunion
         */
        public void updateInfos (Reunion reunion)
        {
            String title = reunion.getmSubject() + " - " + (reunion.getmDate()).substring(0,5) +" - " + reunion.getmTime() + " - " + reunion.getmRoom().getmName();

            if(title.length() > mDimenSize)
            {
                title = title.substring(0,mDimenSize);
                title+="...";
            }

            mName.setText(title);
            mAvatar.setImageResource(reunion.getmRoom().getmRes());

            // Cast the list in String
            String mails = "";
            for (int i = 0; i < reunion.getmEmails().size(); i ++)
            {
                mails += reunion.getmEmails().get(i) + ", ";
            }

            if(mails.length() > mDimenSize)
            {
                mails = mails.substring(0,(mDimenSize*2)-10);
                mails+="...";
            }

            mMails.setText(mails);
        }
    }
}
