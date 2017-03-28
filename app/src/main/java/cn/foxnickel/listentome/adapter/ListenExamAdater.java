/**
 * Created by Night on 2017/3/23.
 * Desc:
 */
package cn.foxnickel.listentome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.foxnickel.listentome.ListenExamActivity;
import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.bean.HearingIssueBean;

/**
 * @author Night
 * @since 2017-03-23
 */
public class ListenExamAdater extends RecyclerView.Adapter<ListenExamAdater.ViewHolder> {
    private Context mContext;
    private List<HearingIssueBean> mList;
    private TextView mTextView;

    public ListenExamAdater(List<HearingIssueBean> items, Context context, TextView t) {
        this.mList = items;
        this.mContext = context;
        mTextView = t;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View wrapper = LayoutInflater.from(mContext).inflate(R.layout.listen_exam_item, parent, false);
        return new ViewHolder(wrapper, (TextView) wrapper.findViewById(R.id.tv_question_num),
                (ImageView) wrapper.findViewById(R.id.im_answer_bg_a),
                (ImageView) wrapper.findViewById(R.id.im_answer_bg_b),
                (ImageView) wrapper.findViewById(R.id.im_answer_bg_c),
                (ImageView) wrapper.findViewById(R.id.im_answer_bg_d),
                (ImageView) wrapper.findViewById(R.id.im_a_a),
                (ImageView) wrapper.findViewById(R.id.im_a_b),
                (ImageView) wrapper.findViewById(R.id.im_a_c),
                (ImageView) wrapper.findViewById(R.id.im_a_d), mTextView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HearingIssueBean hearingIssueBean = mList.get(position);
        holder.answer_bg_c.setVisibility(View.GONE);
        holder.answer_bg_d.setVisibility(View.GONE);
        holder.answer_bg_a.setVisibility(View.GONE);
        holder.answer_bg_b.setVisibility(View.GONE);
        if (hearingIssueBean.isChecked()) {
            switch (hearingIssueBean.getNowAnswer()) {
                case 'A':
                    holder.answer_bg_a.setVisibility(View.VISIBLE);
                    break;
                case 'B':
                    holder.answer_bg_b.setVisibility(View.VISIBLE);
                    break;
                case 'C':
                    holder.answer_bg_c.setVisibility(View.VISIBLE);
                    break;
                case 'D':
                    holder.answer_bg_c.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
        holder.question.setText(hearingIssueBean.getHIContent());
        clilckEvents(holder, position, hearingIssueBean);
    }

    private void clilckEvents(final ViewHolder holder, final int position, final HearingIssueBean h) {
        holder.a_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListenExamActivity.hearingIssueBeen[ListenExamActivity.questionIndex][position].setNowAnswer('A');
                ListenExamActivity.hearingIssueBeen[ListenExamActivity.questionIndex][position].setChecked(true);
                holder.answer_bg_a.setVisibility(View.VISIBLE);
                holder.answer_bg_c.setVisibility(View.GONE);
                holder.answer_bg_b.setVisibility(View.GONE);
                holder.answer_bg_d.setVisibility(View.GONE);
                if (holder.mCountNum[ListenExamActivity.questionIndex][position] == 0) {
                    holder.mCountNum[ListenExamActivity.questionIndex][position] = 1;
                    String[] a = mTextView.getText().toString().trim().split("/");
                    int b = Integer.parseInt(a[0]);
                    b++;
                    StringBuilder s = new StringBuilder();
                    s.append(b + "/" + a[1]);
                    mTextView.setText(s);
                }
            }
        });
        holder.a_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListenExamActivity.hearingIssueBeen[ListenExamActivity.questionIndex][position].setNowAnswer('B');
                ListenExamActivity.hearingIssueBeen[ListenExamActivity.questionIndex][position].setChecked(true);
                holder.answer_bg_b.setVisibility(View.VISIBLE);
                holder.answer_bg_c.setVisibility(View.GONE);
                holder.answer_bg_a.setVisibility(View.GONE);
                holder.answer_bg_d.setVisibility(View.GONE);
                if (holder.mCountNum[ListenExamActivity.questionIndex][position] == 0) {
                    holder.mCountNum[ListenExamActivity.questionIndex][position] = 1;
                    String[] a = mTextView.getText().toString().trim().split("/");
                    int b = Integer.parseInt(a[0]);
                    b++;
                    StringBuilder s = new StringBuilder();
                    s.append(b + "/" + a[1]);
                    mTextView.setText(s);
                }
            }
        });
        holder.a_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListenExamActivity.hearingIssueBeen[ListenExamActivity.questionIndex][position].setNowAnswer('C');
                ListenExamActivity.hearingIssueBeen[ListenExamActivity.questionIndex][position].setChecked(true);
                holder.answer_bg_c.setVisibility(View.VISIBLE);
                holder.answer_bg_b.setVisibility(View.GONE);
                holder.answer_bg_a.setVisibility(View.GONE);
                holder.answer_bg_d.setVisibility(View.GONE);
                if (holder.mCountNum[ListenExamActivity.questionIndex][position] == 0) {
                    holder.mCountNum[ListenExamActivity.questionIndex][position] = 1;
                    String[] a = mTextView.getText().toString().trim().split("/");
                    int b = Integer.parseInt(a[0]);
                    b++;
                    StringBuilder s = new StringBuilder();
                    s.append(b + "/" + a[1]);
                    mTextView.setText(s);
                }
            }
        });
        holder.a_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListenExamActivity.hearingIssueBeen[ListenExamActivity.questionIndex][position].setNowAnswer('D');
                ListenExamActivity.hearingIssueBeen[ListenExamActivity.questionIndex][position].setChecked(true);
                holder.answer_bg_d.setVisibility(View.VISIBLE);
                holder.answer_bg_a.setVisibility(View.GONE);
                holder.answer_bg_b.setVisibility(View.GONE);
                holder.answer_bg_c.setVisibility(View.GONE);
                if (holder.mCountNum[ListenExamActivity.questionIndex][position] == 0) {
                    holder.mCountNum[ListenExamActivity.questionIndex][position] = 1;
                    String[] a = mTextView.getText().toString().trim().split("/");
                    int b = Integer.parseInt(a[0]);
                    b++;
                    StringBuilder s = new StringBuilder();
                    s.append(b + "/" + a[1]);
                    mTextView.setText(s);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView a_a, a_b, a_c, a_d;
        public View rootView;
        private TextView question, questionNum;
        private ImageView answer_bg_a, answer_bg_b, answer_bg_c, answer_bg_d;
        private int[][] mCountNum = new int[30][7];//记录题目是否已做的数组

        public ViewHolder(View rootView, TextView question, ImageView a_b_a, ImageView a_b_b, ImageView a_b_c, ImageView a_b_d, ImageView a_a, ImageView a_b, ImageView a_c, ImageView a_d, TextView t) {
            super(rootView);
            this.rootView = rootView;
            this.question = question;
            answer_bg_a = a_b_a;
            answer_bg_b = a_b_b;
            answer_bg_c = a_b_c;
            answer_bg_d = a_b_d;
            this.a_a = a_a;
            this.a_b = a_b;
            this.a_c = a_c;
            this.a_d = a_d;
            questionNum = t;
            answer_bg_c.setVisibility(View.GONE);
            answer_bg_d.setVisibility(View.GONE);
            answer_bg_a.setVisibility(View.GONE);
            answer_bg_b.setVisibility(View.GONE);

        }


    }
}
