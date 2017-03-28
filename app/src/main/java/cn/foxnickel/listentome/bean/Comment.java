package cn.foxnickel.listentome.bean;

/**
 * Created by Administrator on 2017/3/28.
 */

public class Comment {
    private int UserId;
    private String CommContent;
    private String CommDate;
    private String UserName;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getCommContent() {
        return CommContent;
    }

    public void setCommContent(String commContent) {
        CommContent = commContent;
    }

    public String getCommDate() {
        return CommDate;
    }

    public void setCommDate(String commDate) {
        CommDate = commDate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "UserId=" + UserId +
                ", CommContent='" + CommContent + '\'' +
                ", CommDate='" + CommDate + '\'' +
                ", UserName='" + UserName + '\'' +
                '}';
    }
}
