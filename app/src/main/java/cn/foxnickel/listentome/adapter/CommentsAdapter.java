package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ImageUtils;

import java.util.List;

import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.bean.Comment;

/**
 * Created by Administrator on 2017/3/28.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Context mContext;
    private List<Comment> mCommentList;
    private OnItemClickListener itemClickListener;

    public CommentsAdapter(Context context, List<Comment> commentList) {
        mContext = context;
        mCommentList = commentList;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.comment_recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        holder.mUsername.setText(comment.getUserName());
        String commentTime = comment.getCommDate().substring(0, comment.getCommDate().indexOf(".")).replace("T", " ");
        holder.mTime.setText(commentTime);
        holder.mCommContent.setText(comment.getCommContent());
        Bitmap userPic = ImageUtils.getBitmap(mContext.getResources(), R.drawable.pic7);
        userPic = ImageUtils.toRound(userPic);
        holder.mUserPic.setImageBitmap(userPic);
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mUserPic;
        private TextView mUsername, mTime, mCommContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mUserPic = (ImageView) itemView.findViewById(R.id.head_picture);
            mUsername = (TextView) itemView.findViewById(R.id.username);
            mTime = (TextView) itemView.findViewById(R.id.detail_time);
            mCommContent = (TextView) itemView.findViewById(R.id.contents);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getLayoutPosition());
        }
    }
}
