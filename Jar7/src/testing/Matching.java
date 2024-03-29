package testing;

/*
 * Author: Callan Christophersen
 * UPI: cchr158
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.applet.*;
import java.awt.*;
import java.beans.*;
import java.lang.*;
import java.math.*;
import java.net.*;
import java.awt.im.spi.*;
import java.awt.datatransfer.*;
import java.nio.file.*;
import java.util.*;
import com.oracle.*;
import com.oracle.jrockit.*;
import com.oracle.util.*;
import com.sun.*;

public class Matching{
	
	
	private String[] GaleShapleyAlg() {
		Collection<Blue> bls = new LinkedList<Blue>(blues);
		Blue m;
		Pink w;
		while (!bls.isEmpty()) {
			m = ((LinkedList<Blue>) bls).poll();
			w = (Pink) ((LinkedList<Pink>) m.getPreferanceList()).poll();
			if (w.isFree()) {
				w.setMyBlue(m);
			} else {
				if (w.compareToMyBlue(m)) {
					bls.add(w.getMyMan());
					w.setMyBlue(m);
				} else {
					bls.add(m);
				}
			}
		}
		return absMatching.printMatching();
	}
	
	public static String[] GS(LinkedList<String> b, LinkedList<String> p) {
		b = absMatching.clearWhiteSpace(b);
		p = absMatching.clearWhiteSpace(p);
		return (new Matching(b, p)).GaleShapleyAlg();
	}
	
	public Matching(LinkedList<String> b, LinkedList<String> p) {
		absMatching.setupPinks(p,this);
		absMatching.setupBlues(b);
		absMatching.setPinksPrefs(p);
	}
	
	public static void main(String[] args){
		LinkedList<String> bs = new LinkedList<String>();
		bs.add("Frank1");
		bs.add("Kate1,Mary2,Rhea3,Jill4");
		bs.add("Dennis2");
		bs.add("Mary2,Jill4,Rhea3,Kate1");
		bs.add("Mac3");
		bs.add("Kate1,Rhea3,Jill4,Mary2");
		bs.add("Charlie4");
		bs.add("Rhea3,Mary2,Kate1,Jill4");
		LinkedList<String> ps = new LinkedList<String>();
		ps.add("Rhea3");
		ps.add("Kate111,Mac3,Dennis2,Charlie4");
		ps.add("Mary2");
		ps.add("Mac3,Charlie4,Dennis2,Frank1");
		ps.add("Kate1");
		ps.add("Dennis2,Mac3,Charlie4,Frank1");
		ps.add("Jill4");
		ps.add("Charlie4,Dennis2,Frank1,Mac3");
		String [] s = Matching.GS(bs, ps);
		for(int i=0; i<s.length; i+=2){
			System.out.println(s[i]+" "+s[i+1]);
		}
	}
	static LinkedList<Blue> blues = new LinkedList<Blue>();
	
	interface Pink{
		String getID();

		Blue getMyMan();

		boolean compareToMyBlue(Blue m);

		void setMyBlue(Blue m);

		void setPreferances(String prefList);

		boolean isFree();
	}
	static Pink[] pinks=null;
	interface Blue{
		Pink getMyWomen();

		void setMyPink(Pink w);

		Collection<Pink> getPreferanceList();

		String getID();

		Blue setPref(String prefList);
	}
	
}

abstract class absMatching extends Matching{
	public absMatching(LinkedList<String> b, LinkedList<String> p) {
		super(b, p);
	}

	static Pink[] getPinks() {
		return pinks;
	}

	static Collection<Blue> getBlues() {
		return blues;
	}

	static String[] printMatching() {
		LinkedList<String> out = new LinkedList<String>();
		for (Blue m : blues) {
			//System.out.println(m.id+" "+m.getMyWomen().getID());
			out.add(m.getID());
			out.add(m.getMyWomen().getID());
		}
		return out.toArray(new String[out.size()]);
	}

	static void setPinksPrefs(LinkedList<String> p) {
		for (int i = 0, j = 1; i < pinks.length && j < p.size();) {
			pinks[i].setPreferances(p.get(j));
			i++;
			j += 2;
		}
	}

	static void setupBlues(LinkedList<String> b) {
		for (int i = 0; i < b.size(); i += 2) {
			final int ii=i;
			final LinkedList<String> bf = b;
			blues.add(new Blue(){
				private Collection<Pink> preferanceList = new LinkedList<Pink>();
				private Pink myPink = null;
				private String id = bf.get(ii);

				public Pink getMyWomen() {
					return this.myPink;
				}
				public String getID(){
					return this.id;
				}
				
				public Blue setPref(String prefList) {
					String[] pinkIDs = prefList.split(",");
					for (int i = 0; i < pinkIDs.length; i++) {
						int j = 0;
						while (!pinks[j].getID().equals(pinkIDs[i]))
							j++;
						this.preferanceList.add(pinks[j]);
					}
					return this;
				}
				public Collection<Pink> getPreferanceList() {
					return this.preferanceList;
				}
				public void setMyPink(Pink w) {
					this.myPink = w;
				}
			}.setPref(b.get(ii+1)));
		}
	}

	
	static void setupPinks(LinkedList<String> p, Matching gs) {
		pinks = new Pink[p.size() / 2];
		int name = 0;
		for (int i = 0; i < p.size() / 2; i++) {
			final Matching temp = gs;
			final int n = name;
			final LinkedList<String> pf = p;
			pinks[i] = new Pink(){
				private Blue myBlue = null ;
				private Blue[] preferanceList = new Blue[pf.size() / 2];
				private Matching gs = temp;
				private String id = pf.get(n);

				public String getID() {
					return this.id;
				}

				public Blue getMyMan() {
					return this.myBlue;
				}

				public boolean compareToMyBlue(Blue m) {
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

				public void setMyBlue(Blue m) {
					this.myBlue = m;
					m.setMyPink(this);
				}

				public void setPreferances(String prefList) {
					String[] blueIDs = prefList.split(",");
					for (int i = 0; i < blueIDs.length; i++) {
						int j = 0;
						while (!(blues.get(j).getID().equals(blueIDs[i])))
							j++;
						this.preferanceList[i] = blues.get(j);
					}
				}

				public boolean isFree() {
					return this.myBlue == null;
				}
				
			};

			name += 2;
		}
	}

	static LinkedList<String> clearWhiteSpace(LinkedList<String> l) {
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
}
