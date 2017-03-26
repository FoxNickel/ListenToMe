package cn.foxnickel.listentome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.foxnickel.listentome.adapter.ListenExamAdater;
import cn.foxnickel.listentome.bean.HearingIssueBean;
import cn.foxnickel.listentome.utils.Constants;

/**
 * Created by Night on 2017/3/24.
 * Desc:通过
 */

public class ListenExamActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListenExamAdater mExamAdater;
    private ImageView mVolume;
    private Toolbar mToolbar;
    private Chronometer mCountTime;
    public static int questionIndex = 0;//记录现在在第几题
    private Button mLast, mNext;
    private ArrayList<HearingIssueBean> list;
    private HearingIssueBean[][] hearingIssueBeen;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_exam);
        init();
        back();
        clcickEvents();
    }

    private void init() {
        mVolume = (ImageView) findViewById(R.id.iv_listen_exam_volume);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_question);
        mCountTime = (Chronometer) findViewById(R.id.ct_count_time);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLast = (Button) findViewById(R.id.bt_last_question);
        mNext = (Button) findViewById(R.id.bt_next_question);
        mCountTime.start();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        list = new ArrayList<>();
        questionIndex = 0;
        hearingIssueBeen = new HearingIssueBean[30][7];
        for (int i = 0; i < 30; i++)
            for (int j = 0; j < 7; j++) {
                hearingIssueBeen[i][j] = new HearingIssueBean(0, 0, 0, " Questions 1 to 4 are based on the conversation you have just heard.\n" +
                        "1. A)Project organizer\n" +
                        "   B)Public relations officer.\n" +
                        "   C)Marketing manager.\n" +
                        "   D)Market research consultant.", "D", "Market research consultant", 1, 2, 2);
            }
        hearingIssueBeen[1][2].setHIContent("sdgsdf");
        initMediaPlayer();
        mMediaPlayer.start();
        for (int j = 0; j < hearingIssueBeen[questionIndex].length; j++) {
            list.add(hearingIssueBeen[questionIndex][j]);
        }

        mExamAdater = new ListenExamAdater(list, this);
        mRecyclerView.setAdapter(mExamAdater);
    }

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "1.suf");
            mMediaPlayer.setDataSource(file.getPath());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void back() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回上级的箭头
        getSupportActionBar().setDisplayShowTitleEnabled(false);//将actionbar原有的标题去掉
        /*返回上级*/
        /**
         * 接下来可以把CountTime传给统计数据。
         */
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                mCountTime.stop();
            }
        });
    }

    private void clcickEvents() {
        mVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSoundAnim(view);
            }
        });
        mLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLastQuestion();
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNextQuestion();
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


    private void startNextQuestion() {
        questionIndex++;
        if (questionIndex == 1) {
            mLast.setVisibility(View.VISIBLE);
            mLast.setClickable(true);
        }
        list.clear();
        for (int i = 0; i < hearingIssueBeen[questionIndex].length; i++) {
            list.add(hearingIssueBeen[questionIndex][i]);
        }
        mExamAdater.notifyDataSetChanged();
    }

    private void startLastQuestion() {
        if (questionIndex == 1) {
            mLast.setVisibility(View.INVISIBLE);
            mLast.setClickable(false);
        }
        questionIndex--;
        mExamAdater.notifyDataSetChanged();

    }

    public interface AnimationEndListener {
        void onAnimationEnd(Animator animation);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }
}
