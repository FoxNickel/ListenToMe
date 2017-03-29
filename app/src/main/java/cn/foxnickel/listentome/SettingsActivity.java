package cn.foxnickel.listentome;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    private Button mLogout;
    private TextView mClearCache, mUpdate, mContactUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//显示返回上级的箭头
        getSupportActionBar().setDisplayShowTitleEnabled(false);//将actionbar原有的标题去掉
        /*返回上级*/
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
    }

    private void initView() {
        mLogout = (Button) findViewById(R.id.logout);
        mLogout.setOnClickListener(this);
        mClearCache = (TextView) findViewById(R.id.setting_clear_cache);
        mUpdate = (TextView) findViewById(R.id.setting_update);
        mContactUs = (TextView) findViewById(R.id.setting_contact_us);

        mClearCache.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mContactUs.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("remember_pwd", false);
                editor.remove("UserPhone");
                editor.remove("UserPwd");
                editor.apply();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.setting_clear_cache:
                ToastUtils.showShortToast("缓存已清除");
                break;
            case R.id.setting_update:
                ToastUtils.showShortToast("已经是最新版");
                break;
            case R.id.setting_contact_us:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("联系我们");
                builder.setItems(R.array.contact_us, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String items[] = getResources().getStringArray(R.array.contact_us);
                        ToastUtils.showShortToast(items[which]);
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
    }
}
