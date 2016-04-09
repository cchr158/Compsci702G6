package com.app.group_6.galeshapley.DAO;

import java.util.LinkedList;

/**
 * Created by csjmm on 9/04/2016.
 */
public class Medstudent {

    private LinkedList<Hospital> preferanceList;
    private String name;
    private int studentID;
    private Hospital myCurrentHospital;

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

    public void acceptOffer(Hospital newOffer) {
        this.myCurrentHospital = newOffer;
    }

    public Hospital getMyCurrentHospital() {
        return this.myCurrentHospital;
    }

    public void freeMyHospital() {
        Hospital noHospitalYet = new Hospital();
        myCurrentHospital = noHospitalYet;
    }

    public int getHospitalRanking(Hospital input) {
        for (int i = 0; i < preferanceList.size(); i++) {
            Hospital temp = preferanceList.get(i);
            if (temp.getHospitalID() == (input.getHospitalID())) {
                return i;
            }
        }
        return -1;
    }

    public boolean compareWithMyHospital(Hospital newOffer) {
        if (getHospitalRanking(newOffer) > getHospitalRanking(getMyCurrentHospital())) {
            acceptOffer(newOffer);
            return true;
        } else
            return false;
    }

}
