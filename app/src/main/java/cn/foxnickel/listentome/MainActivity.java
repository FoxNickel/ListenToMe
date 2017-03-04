package cn.foxnickel.listentome;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import cn.foxnickel.listentome.fragment.ListenFragmet;
import cn.foxnickel.listentome.fragment.ProfileFragmet;
import cn.foxnickel.listentome.fragment.SocialFragmet;
import cn.foxnickel.listentome.fragment.SpeechFragmet;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar mBottomNavigationBar;
    private ListenFragmet mListenFragmet;
    private SpeechFragmet mSpeechFragmet;
    private SocialFragmet mSocialFragmet;
    private ProfileFragmet mProfileFragmet;
    private Toolbar mToolbar;
    private SearchView mSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        initBottomNavBar();
        initTab();
        mBottomNavigationBar.setTabSelectedListener(this);
    }

    /*初始化ToolBar*/
    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSearchView = (SearchView) findViewById(R.id.search);
        mToolbar.setTitle("听力频道");//进入app时标题显示听力频道
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(mToolbar);
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
                mToolbar.setVisibility(View.VISIBLE);
                mToolbar.setTitle("听力频道");//点击听力模块时标题显示听力频道
                mSearchView.setVisibility(GONE);
                if(mListenFragmet == null){
                    mListenFragmet = new ListenFragmet();
                }
                transaction.replace(R.id.content_main, mListenFragmet);
                break;
            case 1:
                mToolbar.setVisibility(View.VISIBLE);
                mToolbar.setTitle("口语天地");//点击口语模块时标题显示口语天地
                mSearchView.setVisibility(GONE);
                if(mSpeechFragmet == null){
                    mSpeechFragmet = new SpeechFragmet();
                }
                transaction.replace(R.id.content_main, mSpeechFragmet);
                break;
            case 2:
                mToolbar.setVisibility(GONE);
                mSearchView.setVisibility(View.VISIBLE);
                if(mSocialFragmet == null){
                    mSocialFragmet = new SocialFragmet();
                }
                transaction.replace(R.id.content_main, mSocialFragmet);
                break;
            case 3:
                mToolbar.setVisibility(View.VISIBLE);
                mToolbar.setTitle("个人中心");//点击我的时标题显示个人中心
                mSearchView.setVisibility(GONE);
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
}
