package org.insa.graphs.algorithm.shortestpath;


import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected LabelStar[] create_label_tab  (Graph graph, ShortestPathData data) {
        //init 1 on met tous les labels avec marque à faux et cout à l'infini

    	LabelStar[] label_tab=new LabelStar[graph.size()];
        
        for (Node noeud:graph.getNodes()) {
        	double cout2;
        	if(this.data.getMode()==AbstractInputData.Mode.TIME) {
            	double vitesse=Double.min(graph.getGraphInformation().getMaximumSpeed(),data.getMaximumSpeed());
            	cout2=(double)3.6*noeud.getPoint().distanceTo(data.getDestination().getPoint())/vitesse;
        	}else {
        		cout2=noeud.getPoint().distanceTo(data.getDestination().getPoint());
            	
        	}
        	label_tab[noeud.getId()]=new LabelStar(noeud, false,Double.POSITIVE_INFINITY,cout2 ,null);
        }
        return label_tab;
    }
    
    
    protected void traitement_couts(LabelStar[] label_tab,Arc arc,Label l_present, BinaryHeap<Label> tas) {
    	Node y=arc.getDestination();
    	LabelStar l_y=label_tab[y.getId()];
    	
    	double cout_origin;
    	double new_cout;
		cout_origin=l_y.getTotalCost();
		if(cout_origin==Double.POSITIVE_INFINITY) {
            //System.out.println("ccC");

			l_y.setCost(l_present.getCost()+data.getCost(arc));
			l_y.setDad(arc);
			notifyNodeReached(y);
			tas.insert(l_y);
			System.out.println("ce noeud rentre dans le tas"+y.getId());
		}else {
            //System.out.println("ccD");

			if(!l_y.getMarque()) {
	            //System.out.println("ccE");

				new_cout=l_present.getCost()+data.getCost(arc)+l_y.getCostToDest();
				if(new_cout<cout_origin) {
					tas.remove(l_y);
					l_y.setCost(l_present.getCost()+data.getCost(arc));
					l_y.setDad(arc);
					tas.insert(l_y);
					System.out.println("ce noeud rentre dans le tas"+y.getId());
				}
			}
		}
		System.out.println("ceci est la taille du tas \n"+tas.size());    	
    }
    
    protected ShortestPathSolution doRun() {
    	return super.doRun();
    }

}
