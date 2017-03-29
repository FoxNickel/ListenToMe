package cn.foxnickel.listentome.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.foxnickel.listentome.ListenExamActivity;
import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.translate.Result;
import cn.foxnickel.listentome.utils.MyApplication;

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

    private ITipViewListener mListener;

    public TipView(Context context) {
        this(context, null);
    }

    public TipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TipView view = (TipView) View.inflate(context, R.layout.pop_view, this);
        rootView = view;
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

    private void addListener() {
        mIvFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Boolean) view.getTag() == false) {
                    setFavoriteBackground(R.drawable.ic_favorite_pink_24dp);
                    mIvFavorite.setTag(true);
                } else {
                    setFavoriteBackground(R.drawable.ic_favorite_border_white_24dp);
                    mIvFavorite.setTag(false);
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
    public void setFavoriteBackground(@DrawableRes int drawableSrc) {
        mIvFavorite.setImageResource(drawableSrc);
    }

    public void setListener(ITipViewListener mListener) {
        this.mListener = mListener;
    }

    public interface ITipViewListener {

        void onClickFavorite(View view);

        void onClickPlaySound(View view, String playSound);

        void onClickTipFrame(View view);

        public abstract void setContent(String content);
        /**
         * set up favorite view state  base on it change background of favorite view
         *
         * @param mIvFavorite
         * @param result
         */
        void onInitFavorite(ImageView mIvFavorite, Result result);

        void removeTipView(Result result);

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
                    mListener.removeTipView(mResult);
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
