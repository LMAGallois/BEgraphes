package org.insa.graphs.model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * 
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 *
 */
public class Path {

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     *
     */
   public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
        List<Arc> arcs_suc = new ArrayList<Arc>();
        int cpt=0;
        double min;
        List<Arc> arcs = new ArrayList<Arc>();
        Arc arc_c=null;
        if(nodes.size()==0) {
        	return new Path(graph);
        } else if (nodes.size()==1) {
        	return new Path(graph,nodes.get(0));
        }else {
	        for(Node n:nodes ) {
        		cpt++;
        		if(n.hasSuccessors()) {
        			arcs_suc=n.getSuccessors();
    	        	min=Double.POSITIVE_INFINITY;
    	        	for (Arc arc:arcs_suc) {
    	        		if(cpt<nodes.size()) {
    	        			Node node=arc.getDestination();
    	            		if (node.equals(nodes.get(cpt))) {
    	            			if(arc.getMinimumTravelTime()<min) {
    	            				min=arc.getMinimumTravelTime();
    	            				arc_c=arc;
    	            			}
    	            		}
    	        		}
    	        	}
    	        	
    	        	if(arc_c!=null) {
    	        		arcs.add(arc_c);
    	        	}else if(cpt!=nodes.size()) {
    		            throw new IllegalArgumentException("list as second argument not valid");
    	
    				}
    	        	arc_c=null;
        		}
	        	
	        }
	        return new Path(graph, arcs);
        }
    }


    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the shortest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     */
    public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
        List<Arc> arcs_suc = new ArrayList<Arc>();
        int cpt=0;
        double min;
        List<Arc> arcs = new ArrayList<Arc>();
        Arc arc_c=null;
        if(nodes.size()==0) {
        	return new Path(graph);
        } else if (nodes.size()==1) {
        	return new Path(graph,nodes.get(0));
        }else {
	        for(Node n:nodes ) {
        		cpt++;
        		if(n.hasSuccessors()) {
        			arcs_suc=n.getSuccessors();
    	        	min=Double.POSITIVE_INFINITY;
    	        	for (Arc arc:arcs_suc) {
    	        		if(cpt<nodes.size()) {
    	        			Node node=arc.getDestination();
    	            		if (node.equals(nodes.get(cpt))) {
    	            			if(arc.getLength()<min) {
    	            				min=arc.getLength();
    	            				arc_c=arc;
    	            			}
    	            		}
    	        		}
    	        	}
    	        	
    	        	if(arc_c!=null) {
    	        		arcs.add(arc_c);
    	        	}else if(cpt!=nodes.size()) {
    		            throw new IllegalArgumentException("list as second argument not valid");
    	
    				}
    	        	arc_c=null;
        		}
	        	
	        }
	        return new Path(graph, arcs);
        }
    }

    /**
     * Concatenate the given paths.
     * 
     * @param paths Array of paths to concatenate.
     * 
     * @return Concatenated path.
     * 
     * @throws IllegalArgumentException if the paths cannot be concatenated (IDs of
     *         map do not match, or the end of a path is not the beginning of the
     *         next).
     */
    public static Path concatenate(Path... paths) throws IllegalArgumentException {
        if (paths.length == 0) {
            throw new IllegalArgumentException("Cannot concatenate an empty list of paths.");
        }
        final String mapId = paths[0].getGraph().getMapId();
        for (int i = 1; i < paths.length; ++i) {
            if (!paths[i].getGraph().getMapId().equals(mapId)) {
                throw new IllegalArgumentException(
                        "Cannot concatenate paths from different graphs.");
            }
        }
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Path path: paths) {
            arcs.addAll(path.getArcs());
        }
        Path path = new Path(paths[0].getGraph(), arcs);
        if (!path.isValid()) {
            throw new IllegalArgumentException(
                    "Cannot concatenate paths that do not form a single path.");
        }
        return path;
    }

    // Graph containing this path.
    private final Graph graph;

    // Origin of the path
    private final Node origin;

    // List of arcs in this path.
    private final List<Arc> arcs;

    /**
     * Create an empty path corresponding to the given graph.
     * 
     * @param graph Graph containing the path.
     */
    public Path(Graph graph) {
        this.graph = graph;
        this.origin = null;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path containing a single node.
     * 
     * @param graph Graph containing the path.
     * @param node Single node of the path.
     */
    public Path(Graph graph, Node node) {
        this.graph = graph;
        this.origin = node;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path with the given list of arcs.
     * 
     * @param graph Graph containing the path.
     * @param arcs Arcs to construct the path.
     */
    public Path(Graph graph, List<Arc> arcs) {
        this.graph = graph;
        this.arcs = arcs;
        this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
    }

    /**
     * @return Graph containing the path.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return First node of the path.
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * @return Last node of the path.
     */
    public Node getDestination() {
        return arcs.get(arcs.size() - 1).getDestination();
    }

    /**
     * @return List of arcs in the path.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Check if this path is empty (it does not contain any node).
     * 
     * @return true if this path is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.origin == null;
    }

    /**
     * Get the number of <b>nodes</b> in this path.
     * 
     * @return Number of nodes in this path.
     */
    public int size() {
        return isEmpty() ? 0 : 1 + this.arcs.size();
    }

    /**
     * Check if this path is valid.
     * 
     * A path is valid if any of the following is true:
     * <ul>
     * <li>it is empty;</li>
     * <li>it contains a single node (without arcs);</li>
     * <li>the first arc has for origin the origin of the path and, for two
     * consecutive arcs, the destination of the first one is the origin of the
     * second one.</li>
     * </ul>
     * 
     * @return true if the path is valid, false otherwise.
     * 
     */
    public boolean isValid() {
    	boolean pre=true;
        Iterator<Arc> it=this.arcs.iterator();
        int cpt=1;
        while(it.hasNext()&&pre&&cpt<(this.arcs.size())){
        	Arc el=it.next();
        	cpt++;
    		Arc al=this.arcs.get((this.arcs.indexOf(el))+1);
        	if (el.getDestination()!=al.getOrigin()) {
        		pre=false;
        	
        	}
        	
		}
    	boolean rep=false;
    	if(this.isEmpty()) {
    		rep=true;
    	}
    	else if(this.size()==1) {
    		rep=true;
    	}
        else if(this.origin==this.arcs.get(0).getOrigin() && pre ) {
    		rep=true;
    	}
    	return rep;
    }

    /**
     * Compute the length of this path (in meters).
     * 
     * @return Total length of the path (in meters).
     * 
     */
    public float getLength() {
        Iterator<Arc> it=this.arcs.iterator();
        float i=0;
        while(it.hasNext()) {
        	Arc el=it.next();
        	i=i+el.getLength();
        }
        return i;
    }

    /**
     * Compute the time required to travel this path if moving at the given speed.
     * 
     * @param speed Speed to compute the travel time.
     * 
     * @return Time (in seconds) required to travel this path at the given speed (in
     *         kilometers-per-hour).

     */
    public double getTravelTime(double speed) {
    	double y=(this.getLength()*(float)3600)/(speed*1000);
    	return y;
    }

    /**
     * Compute the time to travel this path if moving at the maximum allowed speed
     * on every arc.
     * 
     * @return Minimum travel time to travel this path (in seconds).
     * 
     */
    public double getMinimumTravelTime() {
    	Iterator<Arc> it=this.arcs.iterator();
        double i=0;
        while(it.hasNext()) {
        	Arc el=it.next();
        	i=i+el.getMinimumTravelTime();
        }
        return i;
    }

}
