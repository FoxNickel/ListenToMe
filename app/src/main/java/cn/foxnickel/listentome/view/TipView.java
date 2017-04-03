package cn.foxnickel.listentome.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import cn.foxnickel.listentome.ListenExamActivity;
import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.dao.ListenToMeDataBaseHelper;
import cn.foxnickel.listentome.translate.Result;

/**
 * Created by GuDong on 3/15/16 18:26.
 * Contact with gudong.name@gmail.com.
 */
public class TipView extends LinearLayout {
    private static final String TAG = "TipView";
    private static final int DURATION_TIME = 300;

    private View mContentView;
    private RelativeLayout mRlInner;
    private View rootView;
    private TextView mTvSrc;
    private TextView mTvPhonetic;
    private LinearLayout mLlDst;
    private LinearLayout mLlSrc;
    private ImageView mIvFavorite;
    private ImageView mIvSound;
    private ImageView mIvDone;
    private TextView mTvPoint;
    public MediaPlayer mPlayer;
    Result mResult = null;
    private ListenToMeDataBaseHelper mDataBaseHelper;
    private ITipViewListener mListener;
    private String wordExplain, wordAudio;

    public TipView(Context context) {
        this(context, null);
        mDataBaseHelper = new ListenToMeDataBaseHelper(context, "ListenToMeDB.db", null, 1);
    }

    public TipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TipView view = (TipView) View.inflate(context, R.layout.pop_view, this);
        rootView = view;
        mDataBaseHelper = new ListenToMeDataBaseHelper(context, "ListenToMeDB.db", null, 1);
        mRlInner = (RelativeLayout) view.findViewById(R.id.rl_pop_inner);
        mTvSrc = (TextView) view.findViewById(R.id.tv_pop_src);
        mTvPoint = (TextView) view.findViewById(R.id.tv_point);
        mTvPhonetic = (TextView) view.findViewById(R.id.tv_pop_phonetic);
        mLlSrc = (LinearLayout) view.findViewById(R.id.ll_pop_src);
        mLlDst = (LinearLayout) view.findViewById(R.id.ll_pop_dst);
        mIvFavorite = (ImageView) view.findViewById(R.id.iv_favorite);
        mIvSound = (ImageView) view.findViewById(R.id.iv_sound);
        mIvDone = (ImageView) view.findViewById(R.id.iv_done);
        mContentView = view.findViewById(R.id.pop_view_content_view);
        initView();
        addListener();
    }

    public void error(String error) {
        mIvFavorite.setVisibility(INVISIBLE);
        mLlDst.setVisibility(INVISIBLE);
        mLlSrc.setVisibility(INVISIBLE);
        mTvPoint.setVisibility(VISIBLE);
        mTvPoint.setText(error);
    }


    public void initMediaPlayer(String mp3URL) {
        wordAudio = mp3URL;
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mp3URL);
            mPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //设置显示动画
    public void startWithAnim() {
        ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mContentView, "translationY", -700, 0);
        translationAnim.setDuration(DURATION_TIME);
        translationAnim.start();
    }

    public void closeWithAnim(@NonNull final OnAnimListener listener) {
        ObjectAnimator translationAnim = ObjectAnimator.ofFloat(mContentView, "translationY", 0, -700);
        translationAnim.start();
        translationAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                listener.onCloseAnimEnd(animation);
            }
        });
    }

    public interface OnAnimListener {
        void onCloseAnimEnd(Animator animation);
    }

    //为每个释义设置内容
    public void addExplain(String explains) {
        wordExplain = explains;
        mLlDst.addView(ViewUtil.getWordsView(getContext(), explains, android.R.color.white, false));
    }

    private void initView() {
        mIvFavorite.setVisibility(View.VISIBLE);
        mIvDone.setVisibility(View.VISIBLE);
        mIvSound.setVisibility(View.VISIBLE);
        mTvSrc.setVisibility(View.VISIBLE);
        mTvPhonetic.setVisibility(VISIBLE);
        mTvPoint.setVisibility(VISIBLE);
        mIvFavorite.setTag(false);
    }

    public void setText(String text) {
        mTvSrc.setText(text);
    }

    public String getText() {
        return mTvSrc.getText().toString().trim();
    }

    private void addListener() {
        mIvFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Boolean) view.getTag() == false) {
                    setFavoriteBackground(R.drawable.ic_favorite_pink_24dp, true);
                    SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
                    db.execSQL("insert into Word(WordName,WordPhoneticText" +
                                    ",WordExplain,WordPhoneticAudio) values(?,?,?,?)",
                            new String[]{mTvSrc.getText().toString().trim(), mTvPhonetic.getText().toString().trim(),
                                    wordExplain, wordAudio});
                    Cursor cursor = db.rawQuery("select WordId from Word where WordName=?", new String[]{mTvSrc.getText().toString().trim()});
                    cursor.moveToNext();
                    int wordId = cursor.getInt(cursor.getColumnIndex("WordId"));
                    db.execSQL("insert into WordCollection(UserId,WordId,WorkMark" +
                                    ") values(?,?,?)",
                            new String[]{"1", wordId + "", "0"});
                    cursor.close();
                } else {
                    setFavoriteBackground(R.drawable.ic_favorite_border_white_24dp, false);
                    SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
                    db.execSQL("delete from WordCollection where WordId=" +
                            "(select WordId from Word where WordName=?)", new String[]{mTvSrc.getText().toString().trim()});
                    db.execSQL("delete from Word where WordName=?", new String[]{mTvSrc.getText().toString().trim()});
                }
            }


        });

        mIvSound.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startSoundAnim(v);
                mPlayer.start();
            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    startSoundAnim(v);
                }
            }
        });
    }

    public void startSoundAnim(View view) {
        addScaleAnim(view, 1000, null);
    }

    private void addScaleAnim(View view, long duration, final ListenExamActivity.AnimationEndListener listener) {
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

    public void setFavoriteBackground(@DrawableRes int drawableSrc, Boolean b) {
        mIvFavorite.setImageResource(drawableSrc);
        mIvFavorite.setTag(b);
    }

    public void setListener(ITipViewListener mListener) {
        this.mListener = mListener;
    }

    public interface ITipViewListener {
        void removeTipView();

        void onRemove();
    }

    //设置单词名称
    private void setQuery(String title) {
        mTvSrc.setText(title);
    }

    //设置单词解释
    public void setPhonetic(String phonetic) {
        if (!TextUtils.isEmpty(phonetic)) {
            mTvPhonetic.setVisibility(View.VISIBLE);
            mTvPhonetic.setText("[" + phonetic + "]");
        } else {
            mTvPhonetic.setVisibility(View.VISIBLE);
        }
    }

    public void setPoint(String text) {
        mTvPoint.setText(text);
    }

    private float lastX;
    float downX = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                lastX = downX;
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                //Logger.i(TAG,"moveX is "+moveX);
                rootView.offsetLeftAndRight((int) (moveX - lastX));
                lastX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                //就隐藏掉
                if ((Math.abs(upX - downX)) > 300) {
                    rootView.offsetLeftAndRight(mRlInner.getRight());
                    mListener.removeTipView();
                    mListener.onRemove();
                } else {
                    rootView.scrollTo(0, 0);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public static View getWordsView(Context context, String word, int color, boolean isTextSelectable) {
        TextView tv = new TextView(context);
        tv.setTextColor(ContextCompat.getColor(context, color));
        tv.setPadding(0, 6, 0, 6);
        tv.setTextSize(16);
        tv.setTextIsSelectable(isTextSelectable);
        tv.setGravity(Gravity.LEFT);
        tv.setText(word);
        return tv;
    }

}
