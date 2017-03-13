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
import cn.foxnickel.listentome.bean.ShijuanBean;

/**
 * Created by Administrator on 2017/3/12.
 */

public class ListenCollectionRecyclerAdapter extends RecyclerView.Adapter<ListenCollectionRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<ShijuanBean> mShijuanBeanList;
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void OnItemCkick(View V, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public ListenCollectionRecyclerAdapter(Context context, List<ShijuanBean> shijuanBeanList) {
        mContext = context;
        mShijuanBeanList = shijuanBeanList;
    }

    @Override
    public ListenCollectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.listen_collection_recycler_item, parent, false);
        ListenCollectionRecyclerAdapter.ViewHolder holder = new ListenCollectionRecyclerAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListenCollectionRecyclerAdapter.ViewHolder holder, int position) {
        ShijuanBean shijuanBean = mShijuanBeanList.get(position);
        holder.mQuestionName.setText(shijuanBean.getQuestionName());
        holder.mQuestionDescription.setText(shijuanBean.getQuestionDescription());
        holder.mQuestionGrade.setText(shijuanBean.getGrade());
    }

    @Override
    public int getItemCount() {
        return mShijuanBeanList.size();
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
