package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.foxnickel.listentome.ListenExamActivity;
import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.bean.ListenExamBean;

/**
 * Created by Administrator on 2017/3/8.
 */

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ListenExamBean> mListenExamList;
    private LayoutInflater mInflater;
    public OnItemClickListener itemClickListener;
    private OnItemClickListener mItemClickListener;

    public QuestionRecyclerAdapter(Context context, ArrayList<ListenExamBean> listenExamList) {
        mContext = context;
        mListenExamList = listenExamList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.exam_recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ListenExamBean listenExamBean = mListenExamList.get(position);
        holder.mQuestionImage.setImageURI(Uri.parse(listenExamBean.getImagePath()));
        holder.mQuestionName.setText(listenExamBean.getQuestionName());
        holder.mQuestionDescription.setText(listenExamBean.getQuestionDescription());
        holder.mQuestionGrade.setText(listenExamBean.getGrade());
    }

    @Override
    public int getItemCount() {
        return mListenExamList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mQuestionImage, mAddToCollection, mDownload;
        private TextView mQuestionName, mQuestionDescription, mQuestionGrade;
        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    mDownload.setImageDrawable(mContext.getResources().getDrawable(R.drawable.download_completed));
                    Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
                }
            }
        };

        public ViewHolder(View itemView) {
            super(itemView);
            mQuestionImage = (ImageView) itemView.findViewById(R.id.question_image);
            mAddToCollection = (ImageButton) itemView.findViewById(R.id.add_to_listen_collection);
            mDownload = (ImageButton) itemView.findViewById(R.id.download);
            mQuestionName = (TextView) itemView.findViewById(R.id.question_name);
            mQuestionDescription = (TextView) itemView.findViewById(R.id.question_description);
            mQuestionGrade = (TextView) itemView.findViewById(R.id.queston_grade);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ListenExamActivity.class));
                }
            });
            mAddToCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAddToCollection.setImageDrawable(mContext.getResources().getDrawable(R.drawable.add_exam_heart_red));
                }
            });
            mDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                                Message message = new Message();
                                message.what = 1;
                                mHandler.sendMessage(message);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }
}
