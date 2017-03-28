package cn.foxnickel.listentome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.foxnickel.listentome.adapter.ListenAnswerAdapter;
import cn.foxnickel.listentome.adapter.ListenExamAdater;
import cn.foxnickel.listentome.bean.HearingIssueBean;

/**
 * Created by Night on 2017/3/24.
 * Desc:通过
 */

public class ListenExamActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListenExamAdater mExamAdater;
    private ListenAnswerAdapter mAnswerAdapter;
    private ImageView mVolume;
    private Toolbar mToolbar;
    private RelativeLayout mRelativeLayout;
    private Chronometer mCountTime;
    private TextView mAnsweredNum, mGrade;
    public static int questionIndex = 0;//记录现在在第几题
    private Button mLast, mNext;
    private ArrayList<HearingIssueBean> list;
    private ArrayList<HearingIssueBean> answerList;
    private LinearLayout mLinearLayout;
    public static HearingIssueBean[][] hearingIssueBeen;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_exam);
        init();
        back();
        clcickEvents();
    }

    private void init() {
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_bt);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl_pop_inner);
        mGrade = (TextView) findViewById(R.id.tv_listen_grade);
        mVolume = (ImageView) findViewById(R.id.iv_listen_exam_volume);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_question);
        mCountTime = (Chronometer) findViewById(R.id.ct_count_time);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAnsweredNum = (TextView) findViewById(R.id.tv_answered_num);
        mLast = (Button) findViewById(R.id.bt_last_question);
        mNext = (Button) findViewById(R.id.bt_next_question);
        mCountTime.start();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        list = new ArrayList<>();
        answerList = new ArrayList<>();
        questionIndex = 0;
        hearingIssueBeen = new HearingIssueBean[10][7];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 7; j++) {
                hearingIssueBeen[i][j] = new HearingIssueBean(0, 0, 0, " Questions 1 to 4 are based on the conversation you have just heard.\n" +
                        "1. A)Project organizer\n" +
                        "   B)Public relations officer.\n" +
                        "   C)Marketing manager.\n" +
                        "   D)Market research consultant.", 'D', "Market research consultant", 1, 2, 2);
            }
        hearingIssueBeen[1][2].setHIContent("sdgsdf");
        initMediaPlayer();
        mMediaPlayer.start();
        for (int j = 0; j < hearingIssueBeen[questionIndex].length; j++) {
            list.add(hearingIssueBeen[questionIndex][j]);
        }
        mExamAdater = new ListenExamAdater(list, this, mAnsweredNum);
        mRecyclerView.setAdapter(mExamAdater);
        Utils.init(this);
    }

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "1.suf");
            mMediaPlayer.setDataSource(file.getPath());
            mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
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
                mCountTime.stop();
                finish();
            }
        });
    }

    private void clcickEvents() {
        mVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSoundAnim(view);
                mMediaPlayer.reset();
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

        mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() + 20000);

        if (questionIndex == 2) {
            createDialog();
        } else {
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


    }

    private void startLastQuestion() {
        if (questionIndex == 1) {
            mLast.setVisibility(View.INVISIBLE);
            mLast.setClickable(false);
        }
        questionIndex--;
        list.clear();
        for (int i = 0; i < hearingIssueBeen[questionIndex].length; i++) {
            list.add(hearingIssueBeen[questionIndex][i]);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    private void createDialog() {
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        Toast.makeText(ListenExamActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                        //mMediaPlayer.pause();
                        initAnswer();
                        break;
                    case Dialog.BUTTON_NEGATIVE:
                        Toast.makeText(ListenExamActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("确定提交答案查看解析?"); //设置内容
        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        builder.setPositiveButton("确认", dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
        builder.create().show();
    }

    private void initAnswer() {
        mEditor = mPreferences.edit();
        mCountTime.stop();
        mLinearLayout.setVisibility(View.GONE);
        mMediaPlayer.pause();
        mRelativeLayout.setVisibility(View.GONE);
        mGrade.setVisibility(View.VISIBLE);
        int grade = 0, sum = 0;
        ToastUtils.showShortToast(hearingIssueBeen.length + " " + hearingIssueBeen[1].length);
        for (int i = 0; i < hearingIssueBeen.length; i++)
            for (int j = 0; j < hearingIssueBeen[i].length; j++) {
                if (hearingIssueBeen[i][j] != null) {
                    answerList.add(hearingIssueBeen[i][j]);
                    if (hearingIssueBeen[i][j].getNowAnswer() == hearingIssueBeen[i][j].getHIAnswer()) {
                        grade++;
                    }
                    sum++;
                }
            }
        grade = (int) (grade * 1.0 / sum * 100 + 0.5);
        mEditor.putInt("grade", grade);
        mGrade.setText(mGrade.getText().toString() + grade + "分");
        mAnswerAdapter = new ListenAnswerAdapter(answerList, ListenExamActivity.this);
        mRecyclerView.setAdapter(mAnswerAdapter);
    }
}
