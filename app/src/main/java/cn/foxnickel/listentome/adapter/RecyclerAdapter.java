package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cn.foxnickel.listentome.R;

/**
 * Created by Administrator on 2017/3/8.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;

    public RecyclerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.exam_recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
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
