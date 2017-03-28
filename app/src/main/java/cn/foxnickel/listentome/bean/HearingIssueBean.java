package cn.foxnickel.listentome.bean;

/**
 * Created by Night on 2017/3/24.
 * Desc:
 */

public class HearingIssueBean {
    private int HIId;
    private int examId;
    private int HAId;
    private String HIContent, HIAnalysis;
    private int HILevel;
    private int HIGrade;
    private int HIState;

    private boolean isChecked;
    private char nowAnswer, HIAnswer;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public HearingIssueBean(int HIId, int examId, int HAId, String HIContent, char HIAnswer, String HIAnalysis, int HILevel, int HIGrade, int HIState) {
        this.HIId = HIId;
        this.examId = examId;
        this.HAId = HAId;
        this.HIContent = HIContent;
        this.HIAnswer = HIAnswer;
        this.HIAnalysis = HIAnalysis;
        this.HILevel = HILevel;
        this.HIGrade = HIGrade;
        this.HIState = HIState;
        isChecked = false;
    }

    public int getHIId() {
        return HIId;
    }

    public void setHIId(int HIId) {
        this.HIId = HIId;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getHAId() {
        return HAId;
    }

    public void setHAId(int HAId) {
        this.HAId = HAId;
    }

    public String getHIContent() {
        return HIContent;
    }

    public void setHIContent(String HIContent) {
        this.HIContent = HIContent;
    }

    public char getHIAnswer() {
        return HIAnswer;
    }

    public void setHIAnswer(char HIAnswer) {
        this.HIAnswer = HIAnswer;
    }

    public String getHIAnalysis() {
        return HIAnalysis;
    }

    public void setHIAnalysis(String HIAnalysis) {
        this.HIAnalysis = HIAnalysis;
    }

    public int getHILevel() {
        return HILevel;
    }

    public void setHILevel(int HILevel) {
        this.HILevel = HILevel;
    }

    public int getHIGrade() {
        return HIGrade;
    }

    public void setHIGrade(int HIGrade) {
        this.HIGrade = HIGrade;
    }

    public int getHIState() {
        return HIState;
    }

    public void setHIState(int HIState) {
        this.HIState = HIState;
    }

    public char getNowAnswer() {
        return nowAnswer;
    }

    public void setNowAnswer(char nowAnswer) {
        this.nowAnswer = nowAnswer;
    }
}
