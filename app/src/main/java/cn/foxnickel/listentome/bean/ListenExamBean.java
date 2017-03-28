package cn.foxnickel.listentome.bean;

/**
 * Created by Night on 2017/3/12.
 * Desc:
 */

public class ListenExamBean {
    String mImagePath,mQuestionName,mQuestionDescription,mGrade,mResourcesPath;


    public ListenExamBean(String imagePath, String questionName, String questionDescription, String grade) {
        mImagePath = imagePath;
        mQuestionName = questionName;
        mQuestionDescription = questionDescription;
        mGrade = grade;
    }

    public ListenExamBean(String imagePath, String questionName, String questionDescription, String grade, String resourcesPath) {
        mImagePath = imagePath;
        mQuestionName = questionName;
        mQuestionDescription = questionDescription;
        mGrade = grade;
        mResourcesPath = resourcesPath;
    }

    public String getResourcesPath() {
        return mResourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        mResourcesPath = resourcesPath;
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
