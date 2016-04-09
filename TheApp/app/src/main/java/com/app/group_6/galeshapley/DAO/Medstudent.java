package com.app.group_6.galeshapley.DAO;

import java.util.LinkedList;

/**
 * Created by csjmm on 9/04/2016.
 */
public class Medstudent {

    private LinkedList<Hospital> preferanceList;
    private String name;
    private int studentID;

    public Medstudent() {
        //NoStudent
    }

    public Medstudent(int studentID, String name, LinkedList<Hospital> preferanceList) {
        this.name = name;
        this.preferanceList = preferanceList;
    }

    public String getName() {
        return this.name;
    }

    public int getStudentID() {
        return this.studentID;
    }
}
