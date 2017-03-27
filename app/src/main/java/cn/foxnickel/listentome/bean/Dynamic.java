package cn.foxnickel.listentome.bean;

/**
 * Created by Administrator on 2017/3/27.
 */

public class Dynamic {
    private int DSId;
    private int UserId;
    private String DSContent;
    private int DSLike;
    private int DSDislike;
    private String DSDate;

    public int getDSId() {
        return DSId;
    }

    public void setDSId(int DSId) {
        this.DSId = DSId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getDSContent() {
        return DSContent;
    }

    public void setDSContent(String DSContent) {
        this.DSContent = DSContent;
    }

    public int getDSLike() {
        return DSLike;
    }

    public void setDSLike(int DSLike) {
        this.DSLike = DSLike;
    }

    public int getDSDislike() {
        return DSDislike;
    }

    public void setDSDislike(int DSDislike) {
        this.DSDislike = DSDislike;
    }

    public String getDSDate() {
        return DSDate;
    }

    public void setDSDate(String DSDate) {
        this.DSDate = DSDate;
    }

    @Override
    public String toString() {
        return "Dynamic{" +
                "DSId=" + DSId +
                ", UserId=" + UserId +
                ", DSContent='" + DSContent + '\'' +
                ", DSLike=" + DSLike +
                ", DSDislike=" + DSDislike +
                ", DSDate='" + DSDate + '\'' +
                '}';
    }
}
