package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.bean.ListenExamBean;

/**
 * Created by Administrator on 2017/3/12.
 */

public class ListenCollectionRecyclerAdapter extends RecyclerView.Adapter<ListenCollectionRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<ListenExamBean> mListenExamBeanList;
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void OnItemCkick(View V, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public ListenCollectionRecyclerAdapter(Context context, List<ListenExamBean> ListenExamBeanList) {
        mContext = context;
        mListenExamBeanList = ListenExamBeanList;
    }

    @Override
    public ListenCollectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.listen_collection_recycler_item, parent, false);
        return new ListenCollectionRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListenCollectionRecyclerAdapter.ViewHolder holder, int position) {
        ListenExamBean ListenExamBean = mListenExamBeanList.get(position);
        holder.mQuestionName.setText(ListenExamBean.getQuestionName());
        holder.mQuestionDescription.setText(ListenExamBean.getQuestionDescription());
        holder.mQuestionGrade.setText(ListenExamBean.getGrade());
        holder.mQuestionImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic9));
    }

    @Override
    public int getItemCount() {
        return mListenExamBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mQuestionImage;
        private TextView mQuestionName, mQuestionDescription, mQuestionGrade;

        public ViewHolder(View itemView) {
            super(itemView);
            mQuestionImage = (ImageView) itemView.findViewById(R.id.question_image);
            mQuestionName = (TextView) itemView.findViewById(R.id.question_name);
            mQuestionDescription = (TextView) itemView.findViewById(R.id.question_description);
            mQuestionGrade = (TextView) itemView.findViewById(R.id.queston_grade);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemCkick(v, getLayoutPosition());
                    }
                }
            });
        }
    }
}
