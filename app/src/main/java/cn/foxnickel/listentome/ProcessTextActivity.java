package cn.foxnickel.listentome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.concurrent.ExecutionException;

import cn.foxnickel.listentome.bean.WordBean;
import cn.foxnickel.listentome.dao.ListenToMeDataBaseHelper;
import cn.foxnickel.listentome.utils.GetJsonFromServerTask;
import cn.foxnickel.listentome.utils.OkHttpManager;
import cn.foxnickel.listentome.view.TipView;

/**
 * Created by Night on 2017/3/13.
 * Desc:
 */

public class ProcessTextActivity extends Activity {
    private TextView mTextView;
    private PopupWindow mPopupWindow;
    private View mPopupView;
    private LayoutInflater mInflater;
    private TipView mTipView;
    private WindowManager wm;
    private ListenToMeDataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = (LayoutInflater) getSystemService(
                LAYOUT_INFLATER_SERVICE);
        mPopupView = mInflater.inflate(R.layout.pop_view, null);
        checkText(getIntent());
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mTipView.mPlayer.reset();
                wm.removeView(mTipView);
                finish();
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

    private void checkText(Intent intent) {
        CharSequence text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        String s = null;
        try {
            s = new GetJsonFromServerTask().execute("http://dict-co.iciba.com/api/dictionary.php?w=" + text + "&key=B9477F75F8562815285EECB45113A0F3&type=json").get();
            Log.e("TAG", s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<WordBean>() {
        }.getType();
        WordBean wordBean = gson.fromJson(s, type);
        popupshow();
        mTipView.setText((String) text);
        for (WordBean.Symbols symbol : wordBean.symbols) {
            mTipView.setPhonetic(symbol.ph_am);
            mTipView.initMediaPlayer(symbol.ph_am_mp3);
            StringBuilder stringBuilder = new StringBuilder();
            for (WordBean.Symbols.Parts parts : symbol.parts) {
                stringBuilder.append(parts.part + "." + parts.means + "\n");
            }
            mTipView.addExplain(stringBuilder.toString());
        }
        mDataBaseHelper = new ListenToMeDataBaseHelper(this, "ListenToMeDB.db", null, 1);
        SQLiteDatabase db = mDataBaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select WordId from Word where WordName=?", new String[]{mTipView.getText()});
        if (!cursor.moveToNext())
            mTipView.setFavoriteBackground(R.drawable.ic_favorite_border_white_24dp, false);
        else {
            mTipView.setFavoriteBackground(R.drawable.ic_favorite_pink_24dp, true);
        }
        cursor.close();
        new tvThread().start();
    }

    private void popupshow() {
        wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mTipView = new TipView(ProcessTextActivity.this);
        wm.addView(mTipView, getPopViewParams());
        mTipView.startWithAnim();
    }

    private WindowManager.LayoutParams getPopViewParams() {
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h = WindowManager.LayoutParams.WRAP_CONTENT;

        int flags = 0;
        int type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            type = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(w, h, type, flags, PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.TOP;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        layoutParams.gravity = Gravity.CENTER | Gravity.TOP;
        layoutParams.x = 0;
        layoutParams.y = 0;
        return layoutParams;
    }


}
