package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.bean.SocialBean;

/**
 * Created by Night on 2017/3/9.
 * Desc:
 */

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.ViewHolder> {
    private ArrayList<SocialBean> mSocialBeanArrayList;
    private LayoutInflater mInflater;
    private Context mContext;
    public OnItemClickListener itemClickListener;

    public SocialAdapter(ArrayList<SocialBean> socialBeanArrayList, Context context) {
        mSocialBeanArrayList = socialBeanArrayList;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public SocialAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View wrapper = mInflater.inflate(R.layout.item_social, parent, false);
        return new ViewHolder(
                wrapper,
                (ImageView) wrapper.findViewById(R.id.iv_avatar),
                (TextView) wrapper.findViewById(R.id.tv_username),
                (ImageView) wrapper.findViewById(R.id.iv_collection),
                (TextView) wrapper.findViewById(R.id.tv_contents),
                (TextView) wrapper.findViewById(R.id.tv_comments),
                (TextView) wrapper.findViewById(R.id.tv_applaud),
                (TextView) wrapper.findViewById(R.id.tv_terrible)
        );
    }

    @Override
    public void onBindViewHolder(SocialAdapter.ViewHolder holder, int position) {
        SocialBean socialBean = mSocialBeanArrayList.get(position);
        holder.userName.setText(socialBean.getUserName());
        holder.comments.setText(socialBean.getComment());
        holder.contents.setText(socialBean.getContents());
        holder.applaud.setText(socialBean.getApplaud());
        holder.terrible.setText(socialBean.getTerrible());
        holder.avator.setImageURI(Uri.parse(socialBean.getImagePath()));
    }

    @Override
    public int getItemCount() {
        return mSocialBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        private TextView userName, contents, comments, applaud, terrible;
        private ImageView avator, collection;

        public ViewHolder(View itemView, ImageView avator, TextView userName, ImageView collection, TextView contents, TextView comments, TextView applaud, TextView terrible) {
            super(itemView);
            cardView = (CardView) itemView;
            this.userName = userName;
            this.avator = avator;
            this.collection = collection;
            this.contents = contents;
            this.comments = comments;
            this.applaud = applaud;
            this.terrible = terrible;
            collection.setTag(false);
            clickEvents();
        }

        private void clickEvents() {
            collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((Boolean) collection.getTag() == false) {
                        openTransition();
                    } else {
                        closeTransition();
                    }
                }
            });

        }

        private void closeTransition() {
            TransitionDrawable drawable = (TransitionDrawable) collection.getDrawable();
            if ((Boolean) collection.getTag()) {
                drawable.reverseTransition(50);
                collection.setTag(false);
            }
        }

        private void openTransition() {
            TransitionDrawable drawable = (TransitionDrawable) collection.getDrawable();
            drawable.startTransition(5);
            collection.setTag(true);
        }

        @Override
        public void onClick(View view) {
            SocialBean socialBean = mSocialBeanArrayList.get(getAdapterPosition());
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
