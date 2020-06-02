package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;



import java.util.Collections;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.AbstractSolution.Status;
public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    protected Label[] create_label_tab(Graph graph, ShortestPathData data) {
        //init 1 on met tous les labels avec marque à faux et cout à l'infini

    	Label[] label_tab=new Label[graph.size()];
        
        for (Node noeud:graph.getNodes()) {
        	label_tab[noeud.getId()]=new Label(noeud, false,Double.POSITIVE_INFINITY ,null);
        }
        return label_tab;
    }
    
    protected void traitement_couts(Label[] label_tab,Arc arc,Label l_present, BinaryHeap<Label> tas) {
    	Node y=arc.getDestination();
    	Label l_y=label_tab[y.getId()];
    	
    	double cout_origin;
    	double new_cout;
		cout_origin=l_y.getCost();
		if(cout_origin==Double.POSITIVE_INFINITY) {
            //System.out.println("ccC");

			l_y.setCost(l_present.getCost()+data.getCost(arc));
            //System.out.println(l_present.getCost()+data.getCost(arc));
			l_y.setDad(arc);
			notifyNodeReached(y);
			tas.insert(l_y);
			System.out.println("ce noeud rentre dans le tas \n"+y.getId());
		}else {
            //System.out.println("ccD");

			if(!l_y.getMarque()) {
	            //System.out.println("ccE");

				new_cout=l_present.getCost()+data.getCost(arc);
				if(new_cout<cout_origin) {
					tas.remove(l_y);
					l_y.setCost(new_cout);
					l_y.setDad(arc);
					tas.insert(l_y);
					System.out.println("ce noeud rentre dans le tas \n"+y.getId());
				}
			}
		}
		System.out.println("ceci est la taille du tas \n"+tas.size());    	
    }
    
    @Override 
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        
        Label[] label_tab=create_label_tab(graph,data);
        
        //init 2: on traite le sommet
        label_tab[data.getOrigin().getId()].setCost(0);
        BinaryHeap<Label> tas=new BinaryHeap<Label>();
        tas.insert(label_tab[data.getOrigin().getId()]);
        
        this.notifyOriginProcessed(label_tab[data.getOrigin().getId()].getSommet());
        
        // iter
        //jusqu'à tas vide ou dest trouvée
        Label l_present=null;
        Node present;
        //System.out.println("ccA");
        int count=0;
        while(!tas.isEmpty() && label_tab[data.getDestination().getId()].getMarque()==false) {
            //System.out.println("ccB");
            int count2=0;
        	
        	l_present=tas.deleteMin();
        	present=l_present.getSommet();
        	label_tab[present.getId()].setMarque(true);
        	System.out.println("ceci est le cout du label marqué \n"+l_present.getCost());
            //System.out.println(label_tab[present.getId()].getMarque());

        	notifyNodeMarked(present);
        	
        	for(Arc arc:present.getSuccessors()) {
        		count2++;
        		if (!data.isAllowed(arc)) {
                	continue;
                }
        		traitement_couts(label_tab,arc,l_present,tas);      
        	}
        	count++;
            System.out.println("ceci est le nb de successeurs \n"+count2);
        }
        System.out.println("ceci est le nb d'iter \n"+count);
        if (label_tab[data.getDestination().getId()].getMarque() == false) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
            System.out.println("NOpe");
        }else {

            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc=label_tab[data.getDestination().getId()].getDad();
            while (arc!=null) {
                //System.out.println("ccE2");

                arcs.add(arc);
                arc = label_tab[arc.getOrigin().getId()].getDad();
            }
            Collections.reverse(arcs);
            //System.out.println("ccFFF");
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
            if(solution.getPath().isValid()) {
            	System.out.println("Le chemin est trouvé");
            }
        }
        
        return solution;
    }
    
  
    
}
