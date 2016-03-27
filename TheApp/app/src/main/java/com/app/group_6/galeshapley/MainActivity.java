package com.app.group_6.galeshapley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button exitB;
    Button nextB;
    Button prevB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exitB = (Button) findViewById(R.id.Exit);
        nextB = (Button) findViewById(R.id.Next);
        exitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //switch to next activity
            }
        });

    }
}

/*
 * Author(s): Callan Christophersen
 *
 * This is an implementation of the Gale-Shapley algorithm for finding a stable maximum matching in a complete bipartite graph.
 */

class GaleShapley {
    private Collection<Blue> blues = new LinkedList<Blue>();
    private Pink[] pinks;

    private class Blue {
        private Collection<Pink> preferanceList = new LinkedList<Pink>();
        private Pink myPink;

        Pink getMyWomen() {
            return this.myPink;
        }

        private void setMyPink(Pink w) {
            this.myPink = w;
        }

        Collection<Pink> getPreferanceList() {
            return this.preferanceList;
        }

        public Blue(String string, GaleShapley gs) {
            this.myPink = null;
            setPref(string, gs);
        }

        private void setPref(String string, GaleShapley gs) {
            String temp = "";
            int[] tempList = new int[pinks.length];
            int j = 0;
            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) != ' ' && i == string.length() - 1) {
                    temp += string.charAt(i);
                    tempList[j++] = Integer.parseInt(temp);
                } else if (string.charAt(i) != ' ') {
                    temp += string.charAt(i);
                } else {
                    tempList[j++] = Integer.parseInt(temp);
                    temp = "";
                }
            }
            for (int i = 0; i < tempList.length; i++) {
                this.preferanceList.add(gs.getPinks()[tempList[i] - 1]);
            }
        }
    }

    private class Pink {
        private Blue myBlue;
        private Blue[] preferanceList;
        private GaleShapley gs;
        private int id;

        int getID() {
            return this.id;
        }

        Blue getMyMan() {
            return this.myBlue;
        }

        // returns true is m comes before myBlue in the preference list
        boolean compareToMyBlue(Blue m) {
            for (int i = 0; i < this.preferanceList.length; i++) {
                if (this.preferanceList[i].equals(this.myBlue)) {
                    return false;
                }
                if (this.preferanceList[i].equals(m)) {
                    return true;
                }
            }
            return false;
        }

        private void setMyBlue(Blue m) {
            this.myBlue = m;
            m.setMyPink(this);
        }

        public Pink(int numberOfMen, GaleShapley gs, int i) {
            this.preferanceList = new Blue[numberOfMen];
            this.myBlue = null;
            this.gs = gs;
            this.id = i;
        }

        private void setPreferances(String string) {
            String temp = "";
            int[] tempList = new int[this.preferanceList.length];
            int j = 0;
            for (int i = 0; i < string.length(); i++) {
                if (i == string.length() - 1 && string.charAt(i) != ' ') {
                    temp += string.charAt(i);
                    tempList[j++] = Integer.parseInt(temp);
                } else if (string.charAt(i) != ' ') {
                    temp += string.charAt(i);
                } else {
                    tempList[j++] = Integer.parseInt(temp);
                    temp = "";
                }
            }
            Blue[] pL = gs.getBlues().toArray(
                    new Blue[this.preferanceList.length]);
            for (int i = 0; i < tempList.length; i++) {
                this.preferanceList[i] = pL[tempList[i] - 1];
            }
        }

        private boolean isFree() {
            return this.myBlue == null;
        }
    }

    Pink[] getPinks() {
        return pinks;
    }

    Collection<Blue> getBlues() {
        return this.blues;
    }

    public GaleShapley(int numberOfPairs, String[] strs) {
        int i = 0;
        pinks = new Pink[numberOfPairs];
        for (; i < numberOfPairs; i++) {
            pinks[i] = new Pink(numberOfPairs, this, i + 1);
        }
        for (i = 0; i < numberOfPairs; i++) {
            blues.add(new Blue(strs[i], this));
        }
        for (int j = 0; j < numberOfPairs; j++) {
            pinks[j].setPreferances(strs[j + i]);
        }
    }

    private void GaleShapleyAlg() {
        Collection<Blue> blues = new LinkedList<Blue>(this.blues);
        Blue m;
        Pink w;
        while (!blues.isEmpty()) {
            m = ((LinkedList<Blue>) blues).poll();
            w = (Pink) ((LinkedList<Pink>) m.getPreferanceList()).poll();
            if (w.isFree()) {
                w.setMyBlue(m);
            } else {
                if (w.compareToMyBlue(m)) {
                    blues.add(w.getMyMan());
                    w.setMyBlue(m);
                } else {
                    blues.add(m);
                }
            }
        }
    }

    private void printMatching() {
        for (Blue m : this.blues) {
            System.out.println(m.getMyWomen().getID());
        }
    }

    // returns true if s contains white space.
    private static boolean checkForWhiteSpace(String s) {
        Pattern p = Pattern.compile("\\s");
        Matcher m = p.matcher(s);
        return m.find();
    }

    public static void main(String[] args) {
        String[] strs;
        int instance = 0;
        BufferedReader in;
        ArrayList<String> list = new ArrayList<String>();

        in = new BufferedReader(new InputStreamReader(System.in));
        String temp = "";
        try {
            do {
                temp = in.readLine();
                list.add(temp);
            } while (!temp.equals("0"));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        args = list.toArray(new String[list.size()]);

        for (int i = 0; i < args.length; i++) {
            if (!checkForWhiteSpace(args[i])) {
                if (Integer.parseInt(args[i]) > 0) {
                    int j = Integer.parseInt(args[i]);
                    int k = j * 2;
                    strs = new String[k];
                    System.arraycopy(args, i + 1, strs, 0, k);
                    System.out.println("Instance " + (++instance) + ":");
                    GaleShapley gs = new GaleShapley(j, strs);
                    gs.GaleShapleyAlg();
                    gs.printMatching();
                }
            }
        }
    }

}