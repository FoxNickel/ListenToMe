package cn.foxnickel.listentome;

import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import cn.foxnickel.listentome.fragment.ListenFragmet;
import cn.foxnickel.listentome.fragment.ProfileFragmet;
import cn.foxnickel.listentome.fragment.SocialFragmet;
import cn.foxnickel.listentome.fragment.SpeechFragmet;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar mBottomNavigationBar;
    private ListenFragmet mListenFragmet;
    private SpeechFragmet mSpeechFragmet;
    private SocialFragmet mSocialFragmet;
    private ProfileFragmet mProfileFragmet;
    public static int mBackClickTimes=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNavBar();
        initTab();
        mBottomNavigationBar.setTabSelectedListener(this);
    }


    /*初始化底部导航栏*/
    private void initBottomNavBar(){
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_nav_bar);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//适应大小
        mBottomNavigationBar.setBarBackgroundColor(R.color.colorPrimary);
        mBottomNavigationBar.addItem(new BottomNavigationItem(
                R.drawable.listen_fill,R.string.bottom_nav_listen)//点击之后的图标
                .setInactiveIconResource(R.drawable.listen)//未点击的图标
                .setActiveColorResource(R.color.appNameColor))//点击之后的字体颜色
                .addItem(new BottomNavigationItem(
                        R.drawable.speech_fill,R.string.bottom_nav_speech)
                        .setInactiveIconResource(R.drawable.speech)
                        .setActiveColorResource(R.color.appNameColor))
                .addItem(new BottomNavigationItem(
                        R.drawable.social_fill,R.string.bottom_nav_social)
                        .setInactiveIconResource(R.drawable.social)
                        .setActiveColorResource(R.color.appNameColor))
                .addItem(new BottomNavigationItem(
                        R.drawable.profile_fill,R.string.bottom_nav_profile)
                        .setInactiveIconResource(R.drawable.profile)
                        .setActiveColorResource(R.color.appNameColor))
                .setFirstSelectedPosition(0)//默认显示面板
                .initialise();//初始化
    }

    /*初始化底部导航栏的默认选中Tab*/
    private void initTab(){
        mListenFragmet = new ListenFragmet();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, mListenFragmet);
        transaction.commit();
    }

    /*Tab被选中时的监听事件*/
    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position){
            case 0:
                if(mListenFragmet == null){
                    mListenFragmet = new ListenFragmet();
                }
                transaction.replace(R.id.content_main, mListenFragmet);
                break;
            case 1:
                if(mSpeechFragmet == null){
                    mSpeechFragmet = new SpeechFragmet();
                }
                transaction.replace(R.id.content_main, mSpeechFragmet);
                break;
            case 2:
                if(mSocialFragmet == null){
                    mSocialFragmet = new SocialFragmet();
                }
                transaction.replace(R.id.content_main, mSocialFragmet);
                break;
            case 3:
                if(mProfileFragmet == null){
                    mProfileFragmet = new ProfileFragmet();
                }
                transaction.replace(R.id.content_main, mProfileFragmet);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mBackClickTimes=0;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish() {
        mBackClickTimes++;
        if (mBackClickTimes == 1) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, mListenFragmet);
            transaction.commit();
            Toast.makeText(this, "再按一次即可退出", Toast.LENGTH_LONG).show();
        } else if (mBackClickTimes == 2) {
            super.finish();
        }
    }
}
