package cn.foxnickel.listentome.bean;

/**
 * Created by Night on 2017/3/12.
 * Desc:
 */

public class ListenExamBean {
    String mImagePath,mQuestionName,mQuestionDescription,mGrade;

    public ListenExamBean(String imagePath, String questionName, String questionDescription, String grade) {
        mImagePath = imagePath;
        mQuestionName = questionName;
        mQuestionDescription = questionDescription;
        mGrade = grade;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getQuestionName() {
        return mQuestionName;
    }

    public void setQuestionName(String questionName) {
        mQuestionName = questionName;
    }

    public String getQuestionDescription() {
        return mQuestionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        mQuestionDescription = questionDescription;
    }

    public String getGrade() {
        return mGrade;
    }

    public void setGrade(String grade) {
        mGrade = grade;
    }
}
