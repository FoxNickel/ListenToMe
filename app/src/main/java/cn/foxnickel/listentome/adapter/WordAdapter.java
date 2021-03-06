/**
 * Created by Night on 2017/3/29.
 * Desc:
 */
package cn.foxnickel.listentome.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import cn.foxnickel.listentome.ListenExamActivity;
import cn.foxnickel.listentome.MyWordActivity;
import cn.foxnickel.listentome.R;
import cn.foxnickel.listentome.bean.ListenExamBean;
import cn.foxnickel.listentome.bean.Word;
import cn.foxnickel.listentome.bean.WordBean;
import cn.foxnickel.listentome.dao.ListenToMeDataBaseHelper;

/**
 * @author Night
 * @since 2017-03-29
 */
public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private final Context context;
    private List<Word> items;
    private MediaPlayer mPlayer;
    private SQLiteOpenHelper mDataBaseHelper;

    public WordAdapter(List<Word> items, Context context) {
        this.items = items;
        this.context = context;
        mDataBaseHelper = new ListenToMeDataBaseHelper(context, "ListenToMeDB.db", null, 1);
        mDataBaseHelper.getWritableDatabase();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_item, parent, false);
        return new ViewHolder(v, (TextView) v.findViewById(R.id.tv_pop_src),
                (TextView) v.findViewById(R.id.tv_pop_phonetic),
                (TextView) v.findViewById(R.id.tv_explain),
                (ImageView) v.findViewById(R.id.iv_sound),
                (ImageView) v.findViewById(R.id.iv_over_flow),
                (ImageView) v.findViewById(R.id.iv_bookmark)
        );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Word word = items.get(position);
        holder.wordName.setText(word.getWordName());
        holder.wordPhonetic.setText(word.getWordPhoetic());
        holder.wordExplain.setText(word.getWordExplain());
        if (word.getIsMark() == 1)
            holder.bookMark.setVisibility(View.VISIBLE);
        else
            holder.bookMark.setVisibility(View.GONE);
        holder.wordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initMediaPlayer(word.getWordAudio());
                startSoundAnim(view);
                mPlayer.start();
                new tvThread().start();
                mPlayer.reset();
            }
        });
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu p = new PopupMenu(context, holder.overflow);
                p.getMenuInflater().inflate(R.menu.wordmenu, p.getMenu());
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
                        switch (item.getItemId()) {
                            case R.id.delete:
                                db.execSQL("delete from WordCollection where WordId=" +
                                        "(select WordId from Word where WordName=?)", new String[]{word.getWordName()});
                                db.execSQL("delete from Word where WordName=?", new String[]{word.getWordName()});
                                MyWordActivity.mList.remove(position);
                                MyWordActivity.mWordAdapter.notifyDataSetChanged();
                                break;
                            case R.id.marker:
                                if (holder.bookMark.getVisibility() == View.GONE) {
                                    db.execSQL("update WordCollection set WorkMark=?  where WorkMark=?", new String[]{"0", "1"});
                                    db.execSQL("update WordCollection set WorkMark=? where WordId=" +
                                            "(select WordId from Word where WordName=?)", new String[]{"1", word.getWordName()});
                                    MyWordActivity.mList.clear();
                                    mDataBaseHelper = new ListenToMeDataBaseHelper(context, "ListenToMeDB.db", null, 1);
                                    Cursor cursor = db.rawQuery("select WordName from Word where WordId=" +
                                            "(select WordId from WordCollection where WorkMark=?)", new String[]{"1"});
                                    String markWordName = null;
                                    if (cursor.moveToFirst()) {
                                        markWordName = cursor.getString(cursor.getColumnIndex("WordName"));
                                    }
                                    cursor = db.rawQuery("select * from Word ", null);
                                    if (cursor.moveToFirst()) {
                                        do {
                                            String wordName = cursor.getString(cursor.getColumnIndex("WordName"));
                                            String wordPhonetic = cursor.getString(cursor.getColumnIndex("WordPhoneticText"));
                                            String wordExplain = cursor.getString(cursor.getColumnIndex("WordExplain"));
                                            String wordAudio = cursor.getString(cursor.getColumnIndex("WordPhoneticAudio"));
                                            Word w = new Word(wordName, wordPhonetic, wordAudio, wordExplain);
                                            if (wordName.equals(markWordName)) {
                                                w.setIsMark(1);
                                            }
                                            MyWordActivity.mList.add(w);
                                        } while (cursor.moveToNext());
                                    }
                                    cursor.close();
                                    MyWordActivity.mWordAdapter.notifyDataSetChanged();
                                } else {
                                    db.execSQL("update WordCollection set WorkMark=?  where WorkMark=?", new String[]{"0", "1"});
                                    holder.bookMark.setVisibility(View.GONE);
                                }

                                break;

                        }
                        return true;
                    }
                });
                makePopForceShowIcon(p);
                p.show();
            }
        });
    }

    private void makePopForceShowIcon(PopupMenu popupMenu) {
        try {
            Field mFieldPopup = popupMenu.getClass().getDeclaredField("mPopup");
            mFieldPopup.setAccessible(true);
            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popupMenu);
            mPopup.setForceShowIcon(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mPlayer.reset();
            }
        }
    };

    class tvThread extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
            try {
                sleep(3000);
                mHandler.sendEmptyMessage(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView wordName, wordPhonetic, wordExplain;
        public ImageView wordAudio, overflow, bookMark;

        public ViewHolder(View rootView, TextView wordName, TextView wordPhonetic, TextView wordExplain, ImageView wordAudio, ImageView overflow, ImageView bookMark) {
            super(rootView);
            this.rootView = rootView;
            this.wordName = wordName;
            this.wordPhonetic = wordPhonetic;
            this.wordExplain = wordExplain;
            this.wordAudio = wordAudio;
            this.overflow = overflow;
            this.bookMark = bookMark;
            clickEvents();
        }

        private void clickEvents() {

        }

    }
}


