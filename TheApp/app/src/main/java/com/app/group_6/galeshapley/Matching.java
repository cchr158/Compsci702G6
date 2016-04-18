package com.app.group_6.galeshapley;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;

//import com.app.group_6.galeshapley.Data.Hospital;
//import com.app.group_6.galeshapley.Data.MedStudent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Yiying Sun(Richard) on 9/04/2016.
 * Edited by Callan Christophersen on 17/04/2017
 */
public class Matching {

    //public ArrayList<Hospital> availableHospital;
    private Collection hospitalList;
    /*private MedStudent tempS;
    private Hospital tempH;*/
    private Collection studentList;


    /*public Matching(ArrayList<Hospital> hospitalList, ArrayList<MedStudent> studentList) {
        this.hospitalList = hospitalList;
        this.studentList = studentList;
        this.availableHospital = hospitalList;
    }*/
    public Matching(Collection hospitalList, Collection studentList) {
        this.hospitalList = hospitalList;
        this.studentList = studentList;
        //this.availableHospital = hospitalList;
    }

    public void GaleShapley() {
        Collection blues = new LinkedList(this.hospitalList);
        blue m;
        pink w;
        while (!blues.isEmpty()) {
            m = (blue)((LinkedList) this.hospitalList).poll();
            w = (pink) ((LinkedList) m.getPreferanceList()).poll();
            if (w.isFree()) {
                w.setBlue(m);
            } else {
                if (w.compareToMyBlue(m)) {
                    blues.add(w.getMyBlue());
                    w.setBlue(m);
                } else {
                    blues.add(m);
                }
            }
        }
        /*
        while (availableHospital.size() != 0) {

            for (Hospital n : availableHospital) {

                tempS = n.getPreferanceList().get(n.getPreferanceList().size() - 1);
                if (n.askStudent(tempS, this)) {
                    n.setMyStudent(tempS);
                    availableHospital.remove(n);
                } else
                    ;
            }


        }*/
    }
    Collection getHospitalList(){
        return this.hospitalList;
    }
    Collection getStudentList(){
        return this.studentList;
    }

}
interface blue{
    pink getPink();
    void setPink(pink p);
    Collection getPreferanceList();
    blue setPreferanceList(Object preferanceList);
    String getId();
}
interface pink{
    String getId();
    blue getMyBlue();
    boolean compareToMyBlue(blue b);
    void setBlue(blue b);
    pink setPreferanceList(Object preferanceList);
    boolean isFree();
    Collection getPreferanceList();
}