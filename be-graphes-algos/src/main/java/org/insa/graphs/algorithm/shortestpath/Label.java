package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label> {
	private Node sommet_courant;
	private boolean marque;
	private double coût;
	private Arc père;
	
	public Label(Node noeud, boolean marque, double cost, Arc pap) {
		this.sommet_courant=noeud;
    	this.marque=marque;
    	this.coût=cost;
    	this.père=pap;
	}
	
	public double getCost() {
		return this.coût;
	}
	
	public boolean getMarque() {
		return this.marque;
	}

	public void setCost(double x) {
		coût=x;
	}
	
	public void setMarque(boolean x) {
		marque=x;
	}
	
	public Node getSommet() {
		return this.sommet_courant;
	}
	
	public void setDad(Arc x) {
		père=x;
	}
	
	public Arc getDad() {
		return this.père;
	}
	@Override
	public int compareTo(Label postit) {

		return Double.compare(this.getTotalCost(), postit.getTotalCost());
	}
	
	public double getTotalCost() {
		return this.coût;
	}

}
