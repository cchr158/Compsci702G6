package com.app.group_6.galeshapley.DAO;

import java.util.ArrayList;

/**
 * Created by csjmm on 9/04/2016.
 */
public class Hospital {

    private String hospitalName;
    private ArrayList<Medstudent> preferanceList;
    private Medstudent myCurrentStudent;
    private int hospitalID;

    public Hospital(int hospitalID, String hospitalName, ArrayList<Medstudent> preferanceList) {
        this.hospitalName = hospitalName;
        this.hospitalID = hospitalID;
        this.preferanceList = preferanceList;
        Medstudent noStudentYet = new Medstudent();
        preferanceList.add(0, noStudentYet);
    }


    public int getStudentRanking(Medstudent input) {
        for (int i = 0; i < preferanceList.size(); i++) {
            Medstudent temp = preferanceList.get(i);
            if (temp.getStudentID() == (input.getStudentID())) {
                return i;
            }
        }
        return -1;
    }

    public Medstudent getMyStudent() {
        return this.myCurrentStudent;
    }

    public void setMyStudent(Medstudent getHim) {
        this.myCurrentStudent = getHim;
    }

    public void freeMyStudent() {
        Medstudent noStudentYet = new Medstudent();
        myCurrentStudent = noStudentYet;
    }

    public boolean compareWithMyStudent(Medstudent newPurpose) {
        if (getStudentRanking(newPurpose) > getStudentRanking(getMyStudent())) {
            setMyStudent(newPurpose);
            return true;
        } else
            return false;
    }


}
