package GaleShapleV1_1;

/*
 * Author: Callan Christophersen
 * UPI: cchr158
 * 
 * This is an implementation of the Gale-Shapley algorithm for finding a stable maximum matching in a complete bipartite graph.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matching {
	public static LinkedList<Blue> blues = new LinkedList<Blue>();
	private Pink[] pinks;

	private class Blue {
		private Collection<Pink> preferanceList = new LinkedList<Pink>();
		private Pink myPink;
		private String id;

		Pink getMyWomen() {
			return this.myPink;
		}

		private void setMyPink(Pink w) {
			this.myPink = w;
		}

		Collection<Pink> getPreferanceList() {
			return this.preferanceList;
		}

		public Blue(String id, String prefList) {
			this.myPink = null;
			this.id = id;
			setPref(prefList);
		}

		private void setPref(String prefList) {
			String[] pinkIDs = prefList.split(",");
			for (int i = 0; i < pinkIDs.length; i++) {
				int j = 0;
				while (!pinks[j].id.equals(pinkIDs[i]))
					j++;
				this.preferanceList.add(pinks[j]);
			}
		}
	}

	private class Pink {
		private Blue myBlue;
		private Blue[] preferanceList;
		private Matching gs;
		private String id;

		String getID() {
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

		public Pink(int numberOfMen, Matching gs, String i) {
			this.preferanceList = new Blue[numberOfMen];
			this.myBlue = null;
			this.gs = gs;
			this.id = i;
		}

		private void setPreferances(String prefList) {
			String[] blueIDs = prefList.split(",");
			for (int i = 0; i < blueIDs.length; i++) {
				int j = 0;
				while (!(blues.get(j).id.equals(blueIDs[i])))
					j++;
				this.preferanceList[i] = blues.get(j);
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

	private String[] printMatching() {
		LinkedList<String> out = new LinkedList<String>();
		for (Blue m : this.blues) {
			//System.out.println(m.id+" "+m.getMyWomen().getID());
			out.add(m.id);
			out.add(m.getMyWomen().getID());
		}
		return out.toArray(new String[out.size()]);
	}

	public Matching(LinkedList<String> b, LinkedList<String> p) {
		setupPinks(p);
		setupBlues(b);
		setPinksPrefs(p);
	}

	private void setPinksPrefs(LinkedList<String> p) {
		for (int i = 0, j = 1; i < pinks.length && j < p.size();) {
			pinks[i].setPreferances(p.get(j));
			i++;
			j += 2;
		}
	}

	private void setupBlues(LinkedList<String> b) {
		for (int i = 0; i < b.size(); i += 2) {
			blues.add(new Blue(b.get(i), b.get(i+1)));
		}
	}

	private void setupPinks(LinkedList<String> p) {
		pinks = new Pink[p.size() / 2];
		int name = 0;
		for (int i = 0; i < p.size() / 2; i++) {
			pinks[i] = new Pink(p.size() / 2, this, p.get(name));
			name += 2;
		}
	}

	public static String[] GS(LinkedList<String> b, LinkedList<String> p) {
		b = clearWhiteSpace(b);
		p = clearWhiteSpace(p);
		Matching gs = new Matching(b, p);
		gs.GaleShapleyAlg();
		return gs.printMatching();

	}

	private static LinkedList<String> clearWhiteSpace(LinkedList<String> l) {
		String[] lArray = l.toArray(new String[l.size()]);
		l = new LinkedList<String>();
		for (int i = 0; i < lArray.length; i++) {
			char[] c = lArray[i].toCharArray();
			for (int j = 0; j < c.length; j++) {
				if (c[j] == ' ')
					c[j] = '\0';
			}
			String s="";
			for(int j=0; j<c.length; j++){
				s +=c[j];
			}
			l.add(s);
		}
		return l;
	}
	//This is for testing only. Do not include in Lib.
//	public static void main(String[] args){
//		LinkedList<String> bs = new LinkedList<String>();
//		bs.add("Frank");
//		bs.add("Kate,Mary,Rhea,Jill");
//		bs.add("Dennis");
//		bs.add("Mary,Jill,Rhea,Kate");
//		bs.add("Mac");
//		bs.add("Kate,Rhea,Jill,Mary");
//		bs.add("Charlie");
//		bs.add("Rhea,Mary,Kate,Jill");
//		LinkedList<String> ps = new LinkedList<String>();
//		ps.add("Rhea");
//		ps.add("Frank,Mac,Dennis,Charlie");
//		ps.add("Mary");
//		ps.add("Mac,Charlie,Dennis,Frank");
//		ps.add("Kate");
//		ps.add("Dennis,Mac,Charlie,Frank");
//		ps.add("Jill");
//		ps.add("Charlie,Dennis,Frank,Mac");
//		String [] s = Matching.GS(bs, ps);
//		for(int i=0; i<s.length; i+=2){
//			System.out.println(s[i]+" "+s[i+1]);
//		}
//	}	
}