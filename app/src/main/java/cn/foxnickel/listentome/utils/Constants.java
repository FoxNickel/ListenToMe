package cn.foxnickel.listentome.utils;

import android.os.Environment;

/**
 * Created by Night on 2017/3/5.
 * Desc:
 */

public class Constants {
    public static final int WRITE_EXTERNAL_CODE=0X01;
    public static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/avaltor.jpg";
    public static final String QUESTION_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/question_image.jpg";
}
