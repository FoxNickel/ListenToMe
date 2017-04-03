package cn.foxnickel.listentome.bean;

/**
 * Created by Night on 2017/3/29.
 * Desc:
 */

public class Word {
    public String wordName;
    public String wordPhoetic;
    public String wordAudio;
    public String wordExplain;
    public int isMark;

    public Word(String wordName, String wordPhoetic, String wordAudio, String wordExplain) {
        this.wordName = wordName;
        this.wordPhoetic = wordPhoetic;
        this.wordAudio = wordAudio;
        this.wordExplain = wordExplain;
        isMark = 0;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getWordPhoetic() {
        return wordPhoetic;
    }

    public void setWordPhoetic(String wordPhoetic) {
        this.wordPhoetic = wordPhoetic;
    }

    public String getWordAudio() {
        return wordAudio;
    }

    public void setWordAudio(String wordAudio) {
        this.wordAudio = wordAudio;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public int getIsMark() {
        return isMark;
    }

    public void setIsMark(int isMark) {
        this.isMark = isMark;
    }
}
