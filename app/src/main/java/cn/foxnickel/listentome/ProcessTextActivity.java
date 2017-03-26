package cn.foxnickel.listentome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
        Toast.makeText(ProcessTextActivity.this, text, Toast.LENGTH_LONG).show();
        popupshow();
        mTipView.setText((String) text);
        mTipView.setPhonetic("sdgs");
        mTipView.addExplain("sffsdfdf");
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
