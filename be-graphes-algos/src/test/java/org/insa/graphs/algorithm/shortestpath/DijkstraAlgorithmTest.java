package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;


public class DijkstraAlgorithmTest {

	 // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e;

    private static ShortestPathData data0_single, data0_impossible, data0_short, data0_long, data2_single, data2_impossible, data2_short, data2_long;
    
    // Some paths...
    private static Path shortPath, longPath;
    
    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""); 
                //speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[5];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        //a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        //c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        //d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        
        //avec arcinspector 0 (le 1er)
        data0_single =new ShortestPathData(graph, nodes[0], nodes[0], ArcInspectorFactory.getAllFilters().get(0) );
        data0_short =new ShortestPathData(graph, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(0) );
        data0_long =new ShortestPathData(graph, nodes[0], nodes[3], ArcInspectorFactory.getAllFilters().get(0) );
        data0_impossible =new ShortestPathData(graph, nodes[0], nodes[4], ArcInspectorFactory.getAllFilters().get(0) );

        //avec arc inspector 2 (le 3e)
        data2_single =new ShortestPathData(graph, nodes[0], nodes[0], ArcInspectorFactory.getAllFilters().get(2) );
        data2_short =new ShortestPathData(graph, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(2) );
        data2_long =new ShortestPathData(graph, nodes[0], nodes[3], ArcInspectorFactory.getAllFilters().get(2) );
        data2_impossible =new ShortestPathData(graph, nodes[0], nodes[4], ArcInspectorFactory.getAllFilters().get(2) );

        shortPath = new Path(graph, Arrays.asList(new Arc[] { a2b }));
        longPath = new Path(graph, Arrays.asList(new Arc[] { a2c, c2d_2 }));
        }
    @Test
    public void DijkstraTest() {

        ShortestPathSolution sol=null;
        DijkstraAlgorithm D_A_0_single=new DijkstraAlgorithm(data0_single);
        DijkstraAlgorithm D_A_0_impossible=new DijkstraAlgorithm(data0_impossible);
        DijkstraAlgorithm D_A_0_short=new DijkstraAlgorithm(data0_short);
        DijkstraAlgorithm D_A_0_long=new DijkstraAlgorithm(data0_long);

        DijkstraAlgorithm D_A_2_single=new DijkstraAlgorithm(data2_single);
        DijkstraAlgorithm D_A_2_impossible=new DijkstraAlgorithm(data2_impossible);
        DijkstraAlgorithm D_A_2_short=new DijkstraAlgorithm(data2_short);
        DijkstraAlgorithm D_A_2_long=new DijkstraAlgorithm(data2_long);
        

        sol=D_A_0_single.run();
        assertEquals(sol.getPath().isValid(),true);
        assertEquals(sol.getPath().isEmpty(),true);
        assertEquals(sol.getStatus(),Status.OPTIMAL);
        
        
        sol=D_A_0_short.run();
        assertEquals(sol.getPath().size(),shortPath.size());
        assertEquals(sol.getPath().getOrigin().getId(),shortPath.getOrigin().getId());        
        assertEquals(sol.getPath().getArcs().get(0).getDestination().getId(),shortPath.getArcs().get(0).getDestination().getId());        
        assertEquals(sol.getStatus(),Status.OPTIMAL);
        assertEquals((int)sol.getPath().getLength(),(int)shortPath.getLength());

        sol=D_A_0_long.run();
        assertEquals(sol.getPath().size(),longPath.size());
        assertEquals(sol.getPath().getOrigin().getId(),longPath.getOrigin().getId());        
        assertEquals(sol.getPath().getArcs().get(1).getDestination().getId(),longPath.getArcs().get(1).getDestination().getId());        
        assertEquals(sol.getStatus(),Status.OPTIMAL);
        assertEquals((int)sol.getPath().getLength(),(int)longPath.getLength());

        sol=D_A_0_impossible.run();
        assertEquals(sol.getStatus(),Status.INFEASIBLE);

       
        sol=D_A_2_single.run();
        assertEquals(sol.getPath().isValid(),true);
        assertEquals(sol.getPath().isEmpty(),true);
        assertEquals(sol.getStatus(),Status.OPTIMAL);

        sol=D_A_2_short.run();
        assertEquals(sol.getPath().size(),shortPath.size());
        assertEquals(sol.getPath().getOrigin().getId(),shortPath.getOrigin().getId());        
        assertEquals(sol.getPath().getArcs().get(0).getDestination().getId(),shortPath.getArcs().get(0).getDestination().getId());        
        assertEquals(sol.getStatus(),Status.OPTIMAL);
        assertEquals((int)sol.getPath().getMinimumTravelTime(),(int)shortPath.getMinimumTravelTime());

        sol=D_A_2_long.run();
        assertEquals(sol.getPath().size(),longPath.size());
        assertEquals(sol.getPath().getOrigin().getId(),longPath.getOrigin().getId());        
        assertEquals(sol.getPath().getArcs().get(1).getDestination().getId(),longPath.getArcs().get(1).getDestination().getId());        
        assertEquals(sol.getStatus(),Status.OPTIMAL);
        assertEquals((int)sol.getPath().getMinimumTravelTime(),(int)longPath.getMinimumTravelTime());
        
        sol=D_A_2_impossible.run();
        assertEquals(sol.getStatus(),Status.INFEASIBLE);

        
    }
    

}
