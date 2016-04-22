/**
 * 
 */
package GaleShapley;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Callan Christophersen
 *
 */
public class Matching {
	static Collection hospitalList;
	public static void main(String[] args) {
		LinkedList<String> bs = new LinkedList<String>();
		bs.add("A");
		bs.add("1");
		bs.add("B");
		bs.add("2");
		bs.add("C");
		bs.add("3");
		LinkedList<String> ps = new LinkedList<String>();
		ps.add("1");
		ps.add("A");
		ps.add("2");
		ps.add("B");
		ps.add("3");
		ps.add("C");
		Matching.Matching(bs, ps);
		for (int i = 0; i < hospitalList.size(); i++) {
			System.out.print(((blue) ((ArrayList) hospitalList).get(i)).getId());
			System.out.print(": " + ((blue) ((ArrayList) hospitalList).get(i)).getPink().getId());
		}
	}

	static void Matching(Collection blues, Collection pinks) {
		hospitalList = new LinkedList();
		// studentList = new LinkedList();
		addBlues(blues);
		addPinks(pinks);
		GaleShapley.galeShapley(hospitalList);
	}

	private static void addPinks(Collection pinks) {
		for (int i = 0; i < pinks.size(); i++) {
			for (int j = 0; j < hospitalList.size(); j++) {
				Collection temp = ((blue) ((LinkedList) hospitalList).get(j)).getPreferanceList();
				for (int k = 0; k < temp.size(); k++) {
					if (((pink) ((LinkedList) temp).get(k)).getId().equals((String) ((LinkedList) pinks).get(i))) {
						((pink) ((LinkedList) temp).get(k)).setPreferanceList(((LinkedList) pinks).get(++i));
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
				private Collection preferanceList;
				private pink myPink = null;

				public pink getPink() {
					return this.myPink;
				}

				public void setPink(pink p) {
					this.myPink = p;
				}

				public Collection getPreferanceList() {
					return preferanceList;
				}

				private blue setPreferanceList(Object preferanceList) {
					this.preferanceList = new LinkedList();
					Object[] prefArray = ((String) preferanceList).split(",");
					for (int i = 0; i < prefArray.length; i++) {
						final int temp = i;
						this.preferanceList.add(new pink() {
							private String id = (String) prefArray[temp];
							private Collection preferanceList;
							private blue myBlue = null;

							public String getId() {
								return id;
							}

							blue getMyBlue() {
								return this.myBlue;
							}

							boolean compareToMyBlue(blue b) {
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

							void setBlue(blue b) {
								this.myBlue = b;
								b.setPink(this);
							}

							public void setPreferanceList(Object preferanceList) {
								this.preferanceList = new LinkedList();
								Object[] prefArray = ((String) preferanceList).split(", ");
								for (int i = 0; i < prefArray.length; i++) {
									for (int j = 0; j < hospitalList.size(); j++) {
										if (((blue) ((LinkedList) hospitalList).get(j)).getId()
												.equals((String) (prefArray[i]))) {
											this.preferanceList.add(((blue) ((LinkedList) hospitalList).get(j)));
											break;
										}
									}

								}
							}

							boolean isFree() {
								return this.myBlue == null;
							}

							Collection getPreferanceList() {
								return preferanceList;
							}

							boolean equals(pink p) {
								return this.id.equals(p.getId());
							}
						});
					}
					return this;
				}

				public String getId() {
					return id;
				}

				boolean equals(blue b) {
					return this.id.equals(b.getId());
				}
			}.setPreferanceList(((LinkedList) blues).get(i + 1)));
		}

	}

	interface blue {
		void setPink(pink p);

		String getId();

		pink getPink();

		Collection getPreferanceList();
	}

	interface pink {
		String getId();

		void setPreferanceList(Object preferanceList);
	}

	
}
interface GaleShapley {
	static void galeShapley(Collection hospitalList) {
		Collection blues = new LinkedList(hospitalList);
		blue m;
		pink w;
		while (!blues.isEmpty()) {
			m = (blue) (((LinkedList) hospitalList).poll());
			w = (pink) (((LinkedList) m.getPreferanceList()).poll());
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
		Matching.hospitalList=blues;
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

	pink setPreferanceList(Object preferanceList);

	boolean isFree();

	Collection getPreferanceList();
}