/**
 * Created by Night on 2017/3/27.
 * Desc:
 */
package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.bean.HearingIssueBean;

/**
 * @author Night
 * @since 2017-03-27
 */
public class ListenExamAnswerAdapter extends RecyclerView.Adapter<ListenExamAnswerAdapter.ViewHolder> {
    private final Context context;
    private List<HearingIssueBean> items;

    public ListenExamAnswerAdapter(List<HearingIssueBean> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listen_answer_item, parent, false);
        return new ViewHolder(v, (TextView) v.findViewById(R.id.tv_question), (TextView) v.findViewById(R.id.tv_my_answer),
                (TextView) v.findViewById(R.id.tv_listen_right_answer), (TextView) v.findViewById(R.id.tv_analysis));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HearingIssueBean item = items.get(position);
        holder.question.setText(item.getHIContent());
        holder.myAnswer.setText(item.getNowAnswer() + "");
        holder.rightAnswer.setText(item.getHIAnswer() + "");
        holder.answerAnalysis.setText(holder.answerAnalysis.getText().toString() + item.getHIAnalysis());
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        private TextView question, myAnswer, rightAnswer, answerAnalysis;

        public ViewHolder(View rootView, TextView question, TextView myAnswer, TextView rightAnswer, TextView answerAnalysis) {
            super(rootView);
            this.rootView = rootView;
            this.question = question;
            this.myAnswer = myAnswer;
            this.rightAnswer = rightAnswer;
            this.answerAnalysis = answerAnalysis;
        }
    }
}
