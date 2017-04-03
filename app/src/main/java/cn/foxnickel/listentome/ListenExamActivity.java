package cn.foxnickel.listentome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.util.Log;
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

import cn.foxnickel.listentome.adapter.ListenExamAnswerAdapter;
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
    private ListenExamAnswerAdapter mAnswerAdapter;
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
        hearingIssueBeen = new HearingIssueBean[10][10];
        hearingIssueBeen[0][0] = new HearingIssueBean(0, 0, 0, " Section A\n" +
                "　　Directions:In this section, you will hear two long conversations. At" +
                " the end of each conversation, you will hear four questions. Both the " +
                "conversation and the questions will be spoken only once. After you hear a " +
                "question, you must choose the best answer from the four choices marked A),B),C)and D)." +
                " Then mark the corresponding letter on Answer Sheet 1 with a single line through the centre.\n" +
                "Questions 1 to 4 are based on the conversation you have just heard.\n" +
                "　1.A) It tries to predict the possible trends of global climate change.\n" +
                "　　B) It studies the impacts of global climate change on people’s lives.\n" +
                "　　C) It links the science of climate change to economic and policy issues.\n" +
                "　　D) It focuses on the efforts countries can make to deal with global warming.", 'A', "It links the science of climate change to economic and policy issues.", 1, 2, 2, 1);
        hearingIssueBeen[1][0] = new HearingIssueBean(0, 0, 0,
                "  2.A) It will take a long time before a consensus is reached on its impact.\n" +
                        "　　B) It would be more costly to deal with its consequences than to avoid it.\n" +
                        "　　C) It is the most pressing issue confronting all countries.\n" +
                        "　　D) It is bound to cause endless disputes among nations.", 'B', "It would be more costly to deal with its consequences than to avoid it.", 1, 2, 2, 1);
        hearingIssueBeen[2][0] = new HearingIssueBean(0, 0, 0,
                "  3.A) The transition to low-carbon energy systems.\n" +
                        "　　B) The cooperation among world major powers.\n" +
                        "　　C) The signing of a global agreement.\n" +
                        "　　D) The raising of people’s awareness.", 'A', "The transition to low-carbon energy systems.", 1, 2, 2, 1);
        hearingIssueBeen[3][0] = new HearingIssueBean(0, 0, 0,
                "  4.A) Carry out more research on it.\n" +
                        "　　B) Plan well in advance.\n" +
                        "　　C) Cut down energy consumption.\n" +
                        "　　D) Adopt new technology.", 'C', "Plan well in advance.", 1, 2, 2, 1);

        hearingIssueBeen[4][0] = new HearingIssueBean(0, 0, 0,
                "　Questions 5 to 8 are based on the conversation you have just heard.\n" +
                        "  5.A) When luck plays a role.\n" +
                        "　　B) What determines success.\n" +
                        "　　C) Whether practice makes perfect.\n" +
                        "　　D) How important natural talent is.", 'B', "What determines success.", 1, 2, 2, 3);
        hearingIssueBeen[4][1] = new HearingIssueBean(0, 0, 0,
                "　6.A) It knocks at your door only once in a while.\n" +
                        "　  B) It is something that no one can possibly create.\n" +
                        "　　C) It comes naturally out of one’s self-confidence.\n" +
                        "　　D) It means being good at seizing opportunities.", 'D', "It means being good at seizing opportunities. ", 1, 2, 2);
        hearingIssueBeen[4][2] = new HearingIssueBean(0, 0, 0,
                "　7.A) Luck rarely contributes to a person’s success.\n" +
                        "　　B) One must have natural talent to be successful.\n" +
                        "　　C) One should always be ready to seize opportunities.\n" +
                        "　　D) Practice is essential to becoming good at something.", 'D', " Practice is essential to becoming good at something.", 1, 2, 2);
        hearingIssueBeen[5][0] = new HearingIssueBean(0, 0, 0,
                "Section B\n" +
                        "Directions: In this section, you will hear two passages. At the end of " +
                        "each passage, you will hear three or four questions. Both the passage and " +
                        "the questions will be spoken only once. After you hear a question, you must " +
                        "choose the best answer from the four choices marked A), B), C) and D). Then " +
                        "mark the corresponding letter on Answer Sheet 1 with a single line through " +
                        "the centre.\nQuestions 9 to 12 are based on the passage you have just heard.\n" +
                        "  9.A) The stump of a giant tree.\n" +
                        "　　B) A huge piece of rock.\n" +
                        "　　C) The peak of a mountain.\n" +
                        "　　D) A tall chimney.", 'D', "To stump of a giant tree. ", 1, 2, 2, 4);
        hearingIssueBeen[5][1] = new HearingIssueBean(0, 0, 0,
                " 10.A) Human activity.\n" +
                        "　　B) Wind and water.\n" +
                        "　　C) Chemical processes.\n" +
                        "　　D) Fire and fury.", 'B', "Wind and water. ", 1, 2, 2);
        hearingIssueBeen[5][2] = new HearingIssueBean(0, 0, 0,
                "　11.A) It is a historical monument.\n" +
                        "　　B) It was built in ancient times.\n" +
                        "　　C) It is Indians’ sacred place for worship.\n" +
                        "　　D) It was created by supernatural powers.", 'D', "It was created by supernatural powers. ", 1, 2, 2);
        hearingIssueBeen[5][3] = new HearingIssueBean(0, 0, 0,
                "　12.A) By sheltering them in a cave.\n" +
                        "　　B) By killing the attacking bears.\n" +
                        "　　C) By lifting them well above the ground.\n" +
                        "　　D) By taking them to the top of a mountain.", 'C', "By lifting them well above the ground.", 1, 2, 2);
        hearingIssueBeen[6][0] = new HearingIssueBean(0, 0, 0,
                "Questions 13 to 15 are based on the passage you have just heard.\n" +
                        " 13.A)They will buy something from the convenience stores.\n" +
                        "　　B) They will take advantage of the time to rest a while.\n" +
                        "　　C) They will have their vehicles washed or serviced.\n" +
                        "　　D) They will pick up some souvenirs or gift items.", 'A', "They will buy something from the convenience stores.", 1, 2, 2, 3);
        hearingIssueBeen[6][1] = new HearingIssueBean(0, 0, 0,
                "　14.A) They can bring only temporary pleasures.\n" +
                        "　　B) They are meant for the extremely wealthy.\n" +
                        "　　C) They should be done away with altogether.\n" +
                        "　　D) They may eventually drive one to bankruptcy.", 'A', "They can bring only temporary pleasures.", 1, 2, 2);
        hearingIssueBeen[6][2] = new HearingIssueBean(0, 0, 0,
                " 15.A) A good way to socialize is to have daily lunch with one’s colleagues.\n" +
                        "　　B) Retirement savings should come first in one’s family budgeting.\n" +
                        "　　C) A vacation will be affordable if one saves 20 dollars a week.\n" +
                        "　　D) Small daily savings can make a big difference in one’s life.", 'D', "Small daily savings an make a big difference in one's life.", 1, 2, 2);
        hearingIssueBeen[7][0] = new HearingIssueBean(0, 0, 0,
                "Section C\n" +
                        "Directions:In this section, you will hear three recordings of lectures or talks followed by three or four questions. The recordings will be played only once. After you hear a question, you must choose the best answer from the four choices marked A), B), C) and D). Then mark the corresponding letter on Answer Sheet 1 with a single line through the centre.\n" +
                        "Questions 16 to 18 are based on the recording you have just heard.\n" +
                        " 16.A) They should be done away with.\n" +
                        "　　B) They are necessary in our lives.\n" +
                        "　　C) They enrich our experience.\n" +
                        "　　D) They are harmful to health.", 'D', "They are necessary in our lives.", 1, 2, 2, 3);
        hearingIssueBeen[7][1] = new HearingIssueBean(0, 0, 0,
                " 17.A) They feel stressed out even without any challenges in life.\n" +
                        "　　B) They feel too overwhelmed to deal with life’s problems.\n" +
                        "　　C) They are anxious to free themselves from life’s troubles.\n" +
                        "　　D) They are exhausted even without doing any heavy work.", 'D', "They feel too overwhelmed to deal with life's problem.", 1, 2, 2);
        hearingIssueBeen[7][2] = new HearingIssueBean(0, 0, 0,
                " 18.A) They expand our mind.\n" +
                        "　　B) They prolong our lives.\n" +
                        "　　C) They narrow our focus.\n" +
                        "　　D) They lessen our burdens.", 'D', "", 1, 2, 2);
        hearingIssueBeen[8][0] = new HearingIssueBean(0, 0, 0,
                "Questions 19 to 22 are based on the conversation you have just heard.\n" +
                        " 19.A) It is not easily breakable.\n" +
                        "　　B) It came from a 3D printer.\n" +
                        "　　C) It represents the latest style.\n" +
                        "　　D) It was made by a fashion designer.", 'B', "It came from a 3D printer.", 1, 2, 2, 4);
        hearingIssueBeen[8][1] = new HearingIssueBean(0, 0, 0,
                " 20.A) When she had just graduated from her college.\n" +
                        "　　B) When she attended a conference in New York\n" +
                        "　　C) When she was studying at a fashion design school.\n" +
                        "　　D) When she attended a fashion show nine months ago.", 'C', "When she was studying at a fashion design school.", 1, 2, 2);
        hearingIssueBeen[8][2] = new HearingIssueBean(0, 0, 0,
                " 21.A) It was difficult to print.\n" +
                        "　　B) It was hard to come by.\n" +
                        "　　C) It was hard and breakable.\n" +
                        "　　D) It was extremely expensive.", 'C', "It was hard and breakable.", 1, 2, 2);
        hearingIssueBeen[8][3] = new HearingIssueBean(0, 0, 0,
                " 22.A)It is the latest model of a 3D printer.\n" +
                        "　　B)It is a plastic widely used in 3D printing.\n" +
                        "　　C)It gives fashion designers room for imagination.\n" +
                        "　　D)It marks a breakthrough in printing material. ", 'D', "It marks a breakthrough in printing material.", 1, 2, 2);
        hearingIssueBeen[9][0] = new HearingIssueBean(0, 0, 0,
                "　Questions 23 to 25 are based on the recording you have just heard.\n" +
                        " 23.A)They arise from the advances in technology.\n" +
                        "　　B)They have not been examined in detail so far.\n" +
                        "　　C)They are easy to solve with modern technology.\n" +
                        "　　D)They can’t be solved without government support.", 'A', "They arise from the advances in technology.", 1, 2, 2, 3);
        hearingIssueBeen[9][1] = new HearingIssueBean(0, 0, 0,
                " 24.A)It is attractive to entrepreneurs.\n" +
                        "　　B)It demands huge investment.\n" +
                        "　　C)It focuses on new products.\n" +
                        "　　D)It is intensely competitive.", 'D', "It is intensively competitive.", 1, 2, 2);
        hearingIssueBeen[9][2] = new HearingIssueBean(0, 0, 0,
                "　25.A)Cooperation with big companies.\n" +
                        "　　B)Recruiting more qualified staff.\n" +
                        "　　C)In-service training of IT personnel.\n" +
                        "　　D)Sharing of costs with each other.", 'D', "Sharing of costs with each other.", 1, 2, 2);
        initMediaPlayer();
        mMediaPlayer.start();
        for (int j = 0; j < hearingIssueBeen[questionIndex][0].getLength(); j++) {
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
                startActivity(new Intent(ListenExamActivity.this, MainActivity.class));
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

        if (questionIndex == hearingIssueBeen.length - 1) {
            createDialog();
        } else {
            questionIndex++;
            if (questionIndex == 1) {
                mLast.setVisibility(View.VISIBLE);
                mLast.setClickable(true);
            }
            list.clear();
            for (int i = 0; i < hearingIssueBeen[questionIndex][0].getLength(); i++) {
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
        for (int i = 0; i < hearingIssueBeen[questionIndex][0].getLength(); i++) {
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
        startActivity(new Intent(ListenExamActivity.this, MainActivity.class));
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
                        mMediaPlayer.pause();
                        initAnswer();
                        break;
                    case Dialog.BUTTON_NEGATIVE:
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
        mEditor.commit();
        mGrade.setText(mGrade.getText().toString() + grade + "分");
        mAnswerAdapter = new ListenExamAnswerAdapter(answerList, ListenExamActivity.this);
        mRecyclerView.setAdapter(mAnswerAdapter);
    }
}
