package com.app.group_6.galeshapley.Data;

import com.app.group_6.galeshapley.Matching;

import java.util.ArrayList;

/**
 * Created by Yiying Sun(Richard) on 9/04/2016.
 * Edited by Callan Christophersen on 18/04/2016.
 */
/*
public class Hospital {

    private String hospitalName;
    private ArrayList<MedStudent> preferanceList;
    private MedStudent myCurrentStudent;


    public Hospital() {

    }

    public Hospital(String hospitalName, ArrayList<MedStudent> preferanceList) {
        this.hospitalName = hospitalName;

        this.preferanceList = preferanceList;
        MedStudent noStudentYet = new MedStudent();
        noStudentYet.setStudentID(0);
        preferanceList.add(0, noStudentYet);
    }

    public ArrayList<MedStudent> getPreferanceList() {
        return this.preferanceList;
    }

    public String getHospitalName() {
        return this.hospitalName;
    }

    public MedStudent getMyStudent() {
        return this.myCurrentStudent;
    }

    public void setMyStudent(MedStudent getHim) {
        this.myCurrentStudent = getHim;
    }

    public int getStudentRanking(MedStudent input) {
        for (int i = 0; i < preferanceList.size(); i++) {
            MedStudent temp = preferanceList.get(i);
            if (temp.getStudentID() == (input.getStudentID())) {
                return i;
            }
        }
        return -1;
    }

    public boolean askStudent(MedStudent target, Matching matching) {
        return target.considerOffer(this, matching);
    }

}
*/