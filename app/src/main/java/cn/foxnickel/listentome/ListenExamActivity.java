package cn.foxnickel.listentome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.foxnickel.listentome.adapter.ListenExamAdater;
import cn.foxnickel.listentome.bean.HearingIssueBean;

/**
 * Created by Night on 2017/3/24.
 * Desc:
 */

public class ListenExamActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListenExamAdater mExamAdater;
    private ImageView mVolume;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_exam);
        mVolume = (ImageView) findViewById(R.id.iv_listen_exam_volume);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_question);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<HearingIssueBean> list = new ArrayList<>();
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        list.add(new HearingIssueBean(0, 0, 0, "sdfsdfsdf", "sdsdfsdf", "sdfsdfsdfsdf", 0, 0, 0));
        mExamAdater = new ListenExamAdater(list, this);
        mRecyclerView.setAdapter(mExamAdater);
        clcickEvents();
    }

    private void clcickEvents() {
        mVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSoundAnim(view);
            }
        });
    }

    public void startSoundAnim(View view) {
        addScaleAnim(view, 1000, null);
    }

    private void addScaleAnim(View view, long duration, final AnimationEndListener listener) {
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.5f, 1f, 1.2f, 1f);
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.5f, 1f, 1.2f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animX, animY);
        animatorSet.setDuration(duration);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) {
                    listener.onAnimationEnd(animation);
                }
            }
        });
        animatorSet.start();
    }

    public interface AnimationEndListener {
        void onAnimationEnd(Animator animation);
    }

}
