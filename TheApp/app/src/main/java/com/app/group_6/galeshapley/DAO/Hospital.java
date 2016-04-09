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

    public Hospital() {

    }
    public Hospital(int hospitalID, String hospitalName, ArrayList<Medstudent> preferanceList) {
        this.hospitalName = hospitalName;
        this.hospitalID = hospitalID;
        this.preferanceList = preferanceList;
        Medstudent noStudentYet = new Medstudent();
        preferanceList.add(0, noStudentYet);
    }


    public int getHospitalID() {
        return this.hospitalID;
    }

    public Medstudent getMyStudent() {
        return this.myCurrentStudent;
    }

    public void setMyStudent(Medstudent getHim) {
        this.myCurrentStudent = getHim;
    }


}
