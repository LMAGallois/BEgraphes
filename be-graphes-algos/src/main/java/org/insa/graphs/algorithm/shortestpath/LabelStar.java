package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class LabelStar extends Label implements Comparable<Label> {

	private double cost_to_dest;
	
	public LabelStar(Node noeud, boolean marque, double cout, double cout2, Arc pap) {
		super(noeud, marque, cout, pap);
		this.cost_to_dest=cout2;
	}
	
	public double getCostToDest() {
		return this.cost_to_dest;
	}
	
	public void setCostToDest(double cout2) {
		cost_to_dest=cout2;
	}
	
	public double getTotalCost() {
		return super.getCost()+this.getCostToDest();
	}
	

	public int compareTo(LabelStar postit) {
		int res=Double.compare(this.getTotalCost(), postit.getTotalCost());
		if (res==0) {
			res=Double.compare(this.getCostToDest(), postit.getCostToDest());
		}
		return res;
	}
	
}
