package cn.foxnickel.listentome;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import cn.foxnickel.listentome.fragment.ListenFragmet;
import cn.foxnickel.listentome.fragment.ProfileFragmet;
import cn.foxnickel.listentome.fragment.SocialFragmet;
import cn.foxnickel.listentome.fragment.SpeechFragmet;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar bottomNavigationBar;
    private ListenFragmet listenFragmet;
    private SpeechFragmet speechFragmet;
    private SocialFragmet socialFragmet;
    private ProfileFragmet profileFragmet;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        initBottomNavBar();

        initTab();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void initBottomNavBar(){
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_nav_bar);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);//适应大小
        bottomNavigationBar.setBarBackgroundColor(R.color.colorPrimary);
        bottomNavigationBar.addItem(new BottomNavigationItem(
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

    private void initTab(){
        listenFragmet = new ListenFragmet();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main,listenFragmet);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position){
            case 0:
                if(listenFragmet == null){
                    listenFragmet = new ListenFragmet();
                }
                transaction.replace(R.id.content_main,listenFragmet);
                break;
            case 1:
                if(speechFragmet == null){
                    speechFragmet = new SpeechFragmet();
                }
                transaction.replace(R.id.content_main,speechFragmet);
                break;
            case 2:
                if(socialFragmet == null){
                    socialFragmet = new SocialFragmet();
                }
                transaction.replace(R.id.content_main,socialFragmet);
                break;
            case 3:
                if(profileFragmet == null){
                    profileFragmet = new ProfileFragmet();
                }
                transaction.replace(R.id.content_main,profileFragmet);
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
