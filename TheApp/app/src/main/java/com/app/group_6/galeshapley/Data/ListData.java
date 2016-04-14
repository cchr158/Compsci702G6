package com.app.group_6.galeshapley.Data;

/**
 * Created by Yiying Sun(Richard) on 12/04/2016.
 */
public class ListData {
    private String string1;
    private int ID;
    private String string2;
    //type == 0 Hospital, type == 1 Student type == 2 result;
    private int type;

    public ListData(String string1, String string2) {
        this.string1 = string1;
        this.string2 = string2;

    }


    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }
}
