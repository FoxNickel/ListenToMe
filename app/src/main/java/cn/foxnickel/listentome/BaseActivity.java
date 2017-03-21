package cn.foxnickel.listentome;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import cn.foxnickel.listentome.utils.AESUtils;
import cn.foxnickel.listentome.utils.Constants;

/**
 * Created by Night on 2017/3/5.
 * Desc:
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!hasPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS)) {
            requestPermissions(Constants.WRITE_EXTERNAL_CODE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS);
        }
    }

    /**
     * 检查权限
     *
     * @param permissions
     * @return
     */
    public boolean hasPermissions(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请权限
     *
     * @param permissions
     */
    public void requestPermissions(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.WRITE_EXTERNAL_CODE:
                dosomething();
                break;
            default:
                break;
        }
    }

    /**
     * 对SDCard进行操作
     */
    public void dosomething() {
    }




}
