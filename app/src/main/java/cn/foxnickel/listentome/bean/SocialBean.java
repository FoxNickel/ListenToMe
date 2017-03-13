package cn.foxnickel.listentome.bean;

/**
 * Created by Night on 2017/3/10.
 * Desc:
 */

public class SocialBean {
    private String mImagePath, mUserName, mContents, mComment, mApplaud, mTerrible;

    public SocialBean(String imagePath, String userName, String contents, String comment, String applaud, String terrible) {
        mImagePath = imagePath;
        mUserName = userName;
        mContents = contents;
        mComment = comment;
        mApplaud = applaud;
        mTerrible = terrible;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getContents() {
        return mContents;
    }

    public void setContents(String contents) {
        mContents = contents;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getApplaud() {
        return mApplaud;
    }

    public void setApplaud(String applaud) {
        mApplaud = applaud;
    }

    public String getTerrible() {
        return mTerrible;
    }

    public void setTerrible(String terrible) {
        mTerrible = terrible;
    }
}
