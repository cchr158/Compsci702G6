/**
 * 
 */
package GaleShapleyV1_0;

import java.util.LinkedList;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author Callan Christophersen
 *
 */
public class Matching {
	static Collection<blue> hospitalList;

	public static void main(String[] args) {
		LinkedList<String> bs = new LinkedList<String>();
		bs.add("A");
		bs.add("3,2,1");
		bs.add("B");
		bs.add("3,2,1");
		bs.add("C");
		bs.add("3,2,1");
		LinkedList<String> ps = new LinkedList<String>();
		ps.add("1");
		ps.add("A,B,C");
		ps.add("2");
		ps.add("A,B,C");
		ps.add("3");
		ps.add("A,B,C");
		Matching.Matching(bs, ps);
		for (int i = 0; i < hospitalList.size(); i++) {
			System.out.print(((LinkedList<blue>) hospitalList).get(i).getId());
			System.out.print(": " + ((LinkedList<blue>) hospitalList).get(i).getPink().getId()+" ");
		}
	}

	static void Matching(Collection blues, Collection<String> pinks) {
		hospitalList = new LinkedList<blue>();
		// studentList = new LinkedList();
		addBlues(blues);
		addPinks(pinks);
		GaleShapley.galeShapley(hospitalList);
	}

	private static void addPinks(Collection<String> pinks) {
		for (int i = 0; i < pinks.size(); i+=2) {
			for (int j = 0; j < hospitalList.size(); j++) {
				Collection<pink> temp = ((LinkedList<blue>)hospitalList).get(j).getPreferanceList();
				for (int k = 0; k < temp.size(); k++) {
					System.out.println("from blues prefList: "+((LinkedList<pink>) temp).get(k).getId());
					System.out.println("Pinks id: "+((LinkedList<String>) pinks).get(i));
					if (((LinkedList<pink>) temp).get(k).getId().equals(((LinkedList<String>) pinks).get(i))) {
						(((LinkedList<pink>) temp).get(k)).setPreferanceList(((LinkedList<String>) pinks).get(i+1));
						System.out.println("Add "+((LinkedList<String>) pinks).get(i+1) +" to "+(((LinkedList<pink>) temp).get(k)).getId() +" Preferance list.");
						break;
					}
				}
			}
		}
	}

	private static void addBlues(Collection blues) {
		for (int i = 0; i < blues.size(); i += 2) {
			final int temp = i;
			hospitalList.add(new blue() {
				private String id = (String) (((LinkedList) blues).get(temp));
				private Collection<pink> preferanceList;
				private pink myPink = null;

				public pink getPink() {
					return this.myPink;
				}

				public void setPink(pink p) {
					this.myPink = p;
				}

				public Collection<pink> getPreferanceList() {
					return this.preferanceList;
				}

				public blue setPreferanceList(Object preferanceList) {
					this.preferanceList = new LinkedList<pink>();
					Object[] prefArray = ((String) preferanceList).split(",");
					for (int i = 0; i < prefArray.length; i++) {
						final int temp = i;
						this.preferanceList.add(new pink() {
							private String id = (String) prefArray[temp];
							private Collection <blue>preferanceList;
							private blue myBlue = null;

							public String getId() {
								return id;
							}

							public blue getMyBlue() {
								return this.myBlue;
							}

							public boolean compareToMyBlue(blue b) {
								Object[] testGroup = this.preferanceList.toArray();
								for (int i = 0; i < this.preferanceList.size(); i++) {
									if (((blue) testGroup[i]).equals(this.myBlue)) {
										return false;
									} else if (((blue) testGroup[i]).equals(b)) {
										return true;
									}
								}
								return false;
							}

							public void setBlue(blue b) {
								this.myBlue = b;
								b.setPink(this);
							}

							public void setPreferanceList(Object preferanceList) {
								this.preferanceList = new LinkedList<blue>();
								Object[] prefArray = ((String) preferanceList).split(",");
								for (int i = 0; i < prefArray.length; i++) {
									for (int j = 0; j < hospitalList.size(); j++) {
										if (((blue) ((LinkedList<blue>) hospitalList).get(j)).getId()
												.equals((String) (prefArray[i]))) {
											this.preferanceList.add(((blue) ((LinkedList<blue>) hospitalList).get(j)));
											break;
										}
									}

								}
							}

							public boolean isFree() {
								return this.myBlue == null;
							}

							public Collection<blue> getPreferanceList() {
								return this.preferanceList;
							}

							public boolean equals(Object p) {
								return this.id.equals(((pink)p).getId());
							}
						});
					}
					return this;
				}

				public String getId() {
					return id;
				}

				public boolean equals(Object b) {
					return this.id.equals(((blue)b).getId());
				}
			}.setPreferanceList(((LinkedList) blues).get(i + 1)));
		}

	}

//	interface blue {
//		void setPink(pink p);
//
//		String getId();
//
//		pink getPink();
//
//		Collection getPreferanceList();
//	}
//
//	interface pink {
//		String getId();
//
//		void setPreferanceList(Object preferanceList);
//	}


	
}
interface GaleShapley {
	static void galeShapley(Collection<blue> hospitalList) {
		Collection<blue> blues = new LinkedList<blue>(hospitalList);
		Set<blue> s = new HashSet<blue>();
		blue m;
		pink w;
		while (!blues.isEmpty()) {
			m = ((LinkedList<blue>) blues).poll();
			w = ((LinkedList<pink>) m.getPreferanceList()).poll();
			if (w.isFree()) {
				s.remove(m);
				w.setBlue(m);
				s.add(m);
			} else {
				if (w.compareToMyBlue(m)) {
					s.remove(m);
					blues.add(w.getMyBlue());
					w.setBlue(m);
					s.add(m);
				} else {
					s.remove(m);
					blues.add(m);
				}
			}
		}
		Matching.hospitalList = new LinkedList<blue>(s);
	}
	
}
interface blue {
	pink getPink();

	void setPink(pink p);

	Collection getPreferanceList();

	blue setPreferanceList(Object preferanceList);

	String getId();
}

interface pink {
	String getId();

	blue getMyBlue();

	boolean compareToMyBlue(blue b);

	void setBlue(blue b);

	void setPreferanceList(Object preferanceList);

	boolean isFree();

	Collection getPreferanceList();
}

