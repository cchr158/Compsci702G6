package com.app.group_6.galeshapley;

import com.app.group_6.galeshapley.DAO.Hospital;
import com.app.group_6.galeshapley.DAO.MedStudent;

import java.util.ArrayList;

/**
 * Created by csjmm on 9/04/2016.
 */
public class Matching {

    public ArrayList<Hospital> availableHospital;
    private ArrayList<Hospital> hospitalList;
    private MedStudent tempS;
    private Hospital tempH;
    private ArrayList<MedStudent> studentList;

    public Matching(ArrayList<Hospital> hospitalList, ArrayList<MedStudent> studentList) {
        this.hospitalList = hospitalList;
        this.studentList = studentList;
        this.availableHospital = hospitalList;
    }

    public void GaleShapley() {
        while (availableHospital.size() != 0) {

            for (Hospital n : availableHospital) {

                tempS = n.getPreferanceList().get(n.getPreferanceList().size() - 1);
                if (n.askStudent(tempS, this)) {
                    n.setMyStudent(tempS);
                    availableHospital.remove(n);
                } else
                    ;
            }


        }
    }

}
