package com.app.group_6.galeshapley.DAO;

import com.app.group_6.galeshapley.Matching;

import java.util.LinkedList;

/**
 * Created by csjmm on 9/04/2016.
 */
public class MedStudent {

    public Matching matching;
    private LinkedList<Hospital> preferanceList;
    private String name;
    private int studentID;
    private Hospital myCurrentHospital;
    private Hospital offer;

    public MedStudent() {

    }


    public MedStudent(int studentID, String name, LinkedList<Hospital> preferanceList) {
        this.studentID = studentID;
        this.name = name;
        this.preferanceList = preferanceList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public void setStudentID(int ID) {
        this.studentID = ID;
    }

    public void declineOffer(Hospital newOffer) {
        newOffer.setMyStudent(newOffer.getPreferanceList().get(0));
    }

    public void rejectCurrent() {
        if (this.myCurrentHospital.getHospitalID() != 0) {
            this.myCurrentHospital.setMyStudent(myCurrentHospital.getPreferanceList().get(0));
            matching.availableHospital.add(myCurrentHospital);
        }
    }
    public void acceptOffer(Hospital newOffer) {
        rejectCurrent();
        this.myCurrentHospital = newOffer;
    }

    public boolean considerOffer(Hospital newOffer, Matching matching) {
        this.matching = matching;
        if (betterThanMyHospital(newOffer)) {
            acceptOffer(newOffer);
            return true;
        } else
            declineOffer(newOffer);
        return false;
    }


    public Hospital getMyCurrentHospital() {
        return this.myCurrentHospital;
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

    public boolean betterThanMyHospital(Hospital newOffer) {
        if (getHospitalRanking(newOffer) > getHospitalRanking(getMyCurrentHospital())) {
            acceptOffer(newOffer);
            return true;
        } else
            return false;
    }

}
