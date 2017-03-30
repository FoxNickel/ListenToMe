package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.ImageUtils;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.foxnickel.listentome.CommentActvity;
import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.bean.Dynamic;
import cn.foxnickel.listentome.utils.GetJsonFromServerTask;
import cn.foxnickel.listentome.utils.MyApplication;

import static android.content.ContentValues.TAG;

/**
 * Created by Night on 2017/3/9.
 * Desc:
 */

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.ViewHolder> {
    private List<Dynamic> mDynamicArrayList;
    private LayoutInflater mInflater;
    private Context mContext;
    public OnItemClickListener itemClickListener;

    public SocialAdapter(List<Dynamic> dynamicList, Context context) {
        mDynamicArrayList = dynamicList;
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
                (TextView) wrapper.findViewById(R.id.tv_applaud),
                (TextView) wrapper.findViewById(R.id.tv_terrible),
                (ImageView) wrapper.findViewById(R.id.iv_applaud),
                (ImageView) wrapper.findViewById(R.id.iv_terrible)

        );
    }

    @Override
    public void onBindViewHolder(SocialAdapter.ViewHolder holder, int position) {
        Dynamic dynamic = mDynamicArrayList.get(position);
        holder.contents.setText(dynamic.getDSContent());
        holder.applaud.setText(String.valueOf(dynamic.getDSLike()));
        holder.terrible.setText(String.valueOf(dynamic.getDSDislike()));
        holder.userName.setText(String.valueOf(dynamic.getUserName()));
        Bitmap bitmap = ImageUtils.getBitmap(mContext.getResources(), R.drawable.pic9);
        bitmap = ImageUtils.toRound(bitmap);
        holder.avator.setImageBitmap(bitmap);
        holder.time.setText(dynamic.getDSDate().substring(0, dynamic.getDSDate().indexOf("T")));
        Log.i(TAG, "onBindViewHolder: dynamic: " + dynamic.toString());
    }

    @Override
    public int getItemCount() {
        return mDynamicArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView;
        private TextView userName, contents, comments, applaud, terrible, time;
        private ImageView avator, collection, iv_applaud, iv_terrible, iv_comments;

        public ViewHolder(View itemView, ImageView avator, TextView userName, ImageView collection, TextView contents, TextView applaud, TextView terrible, ImageView iv_applaud, ImageView iv_terrible) {
            super(itemView);
            cardView = (CardView) itemView;
            this.userName = userName;
            this.avator = avator;
            this.collection = collection;
            this.contents = contents;
            this.applaud = applaud;
            this.terrible = terrible;
            this.iv_applaud = iv_applaud;
            this.iv_terrible = iv_terrible;
            time = (TextView) itemView.findViewById(R.id.dynamic_time);
            iv_comments = (ImageView) itemView.findViewById(R.id.iv_comment);
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
            contents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MyApplication.getContext(), contents.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            iv_applaud.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a = Integer.parseInt(applaud.getText().toString());
                    a++;
                    applaud.setText(a + "");

                }
            });
            iv_terrible.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a = Integer.parseInt(terrible.getText().toString());
                    a++;
                    terrible.setText(a + "");
                }
            });
            /*获取评论*/
            iv_comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        int dynamicId = getLayoutPosition() + 1;
                        String url = "http://www.foxnickel.cn:3000/community/" + dynamicId + "/comments";
                        Log.i(TAG, "onClick: url: " + url);
                        String jsonStr = new GetJsonFromServerTask().execute(url).get();
                        Log.i(TAG, "onClick: comments: " + jsonStr);
                        Intent intent = new Intent(mContext, CommentActvity.class);
                        intent.putExtra("username", userName.getText());
                        intent.putExtra("contents", contents.getText());
                        intent.putExtra("time", time.getText());
                        intent.putExtra("commentJsonStr", jsonStr);
                        mContext.startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
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
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

}
