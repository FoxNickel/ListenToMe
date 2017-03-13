package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mQuestionImage;
        private ImageButton mAddToCollection, mDownload;
        private TextView mQuestionName, mQuestionDescription, mQuestionGrade;

        public ViewHolder(View itemView) {
            super(itemView);
            mQuestionImage = (ImageView) itemView.findViewById(R.id.question_image);
            mAddToCollection = (ImageButton) itemView.findViewById(R.id.add_to_listen_collection);
            mDownload = (ImageButton) itemView.findViewById(R.id.download);
            mQuestionName = (TextView) itemView.findViewById(R.id.question_name);
            mQuestionDescription = (TextView) itemView.findViewById(R.id.question_description);
            mQuestionGrade = (TextView) itemView.findViewById(R.id.queston_grade);
        }
    }
}
