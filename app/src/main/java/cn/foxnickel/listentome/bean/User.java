package cn.foxnickel.listentome.bean;

/**
 * Created by Night on 2017/3/20.
 * Desc:
 */

public class User {
    private int userId;
    private String userName;
    private String phone;
    private String email;
    private String pwd;
    private int exp;

    public User(int userId, String userName, String phone, String email, String pwd, int exp) {
        this.userId = userId;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.pwd = pwd;
        this.exp = exp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
