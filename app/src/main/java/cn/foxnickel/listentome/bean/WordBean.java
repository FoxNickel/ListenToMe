package cn.foxnickel.listentome.bean;

import java.util.List;

/**
 * Created by Night on 2017/3/29.
 * Desc:
 */

public class WordBean {
    public String word_name;
    public List<Symbols> symbols;

    public static class Symbols {
        public String ph_am;
        public String ph_am_mp3;
        public List<Parts> parts;

        public static class Parts {
            public String part;
            public List<String> means;
        }
    }
}
