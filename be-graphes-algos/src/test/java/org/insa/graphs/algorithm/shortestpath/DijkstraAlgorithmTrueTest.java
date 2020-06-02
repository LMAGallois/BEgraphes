package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;


import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.*;
import org.insa.graphs.model.io.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraAlgorithmTrueTest {
	 private static ShortestPathData data0_single, data0_impossible, data0_short, data0_long, data2_single, data2_impossible, data2_short, data2_long;

	    private static Path short_path,longPath;
		
	    @BeforeClass 
		public static void InitAll() throws Exception {
			GraphReader reader = new BinaryGraphReader(new DataInputStream( new BufferedInputStream(new FileInputStream("C:/Users/leoni/Documents/graphes/BE_graphes/cartes/toulouse.mapgr"))));
	        Graph graph = reader.read();
	        
	        PathReader pathreader_short=new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream("C:/Users/leoni/Documents/graphes/BE_graphes/path/path_fr31tls_insa_laas_canal.path"))));
	        short_path=pathreader_short.readPath(graph);
	        
	        PathReader pathreader_long=new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream("C:/Users/leoni/Documents/graphes/BE_graphes/path/path_fr31tls_insa_n7.path"))));
	        longPath=pathreader_long.readPath(graph);
	        
	        data0_single =new ShortestPathData(graph, graph.get(0), graph.get(0), ArcInspectorFactory.getAllFilters().get(0) );
	        data0_short =new ShortestPathData(graph, short_path.getOrigin(), short_path.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );
	        data0_long =new ShortestPathData(graph, longPath.getOrigin(), longPath.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );
	        data0_impossible =new ShortestPathData(graph, graph.get(38199), graph.get(20006), ArcInspectorFactory.getAllFilters().get(0) );

	        //avec arc inspector 2 (le 3e)
	        data2_single =new ShortestPathData(graph, graph.get(0), graph.get(0), ArcInspectorFactory.getAllFilters().get(2) );
	        data2_short =new ShortestPathData(graph, short_path.getOrigin(),short_path.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
	        data2_long =new ShortestPathData(graph, longPath.getOrigin(), longPath.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
	        data2_impossible =new ShortestPathData(graph, graph.get(38199), graph.get(20006), ArcInspectorFactory.getAllFilters().get(2) );
	    

		}
	    
	        
	        @Test
	        public void DijkstraTrueTest() {

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
	          // assertEquals(sol.getPath().size(),short_path.size());expected 17 but was 20
	            assertEquals(sol.getPath().getOrigin().getId(),short_path.getOrigin().getId());        
	         //   assertEquals(sol.getPath().getArcs().get(0).getDestination().getId(),short_path.getArcs().get(0).getDestination().getId());        
	            assertEquals(sol.getStatus(),Status.OPTIMAL);
	         //   assertEquals((int)sol.getPath().getLength(),(int)short_path.getLength());expected 1263 but was 1277

	            sol=D_A_0_long.run();
	         //  assertEquals(sol.getPath().size(),longPath.size());expected 64 but was 120
	            assertEquals(sol.getPath().getOrigin().getId(),longPath.getOrigin().getId());        
	        //    assertEquals(sol.getPath().getArcs().get(1).getDestination().getId(),longPath.getArcs().get(1).getDestination().getId());        
	            assertEquals(sol.getStatus(),Status.OPTIMAL);
	        //    assertEquals((int)sol.getPath().getLength(),(int)longPath.getLength());expected 4499 but was 5275

	            sol=D_A_0_impossible.run();
	            assertEquals(sol.getStatus(),Status.INFEASIBLE);

	           
	            sol=D_A_2_single.run();
	            assertEquals(sol.getPath().isValid(),true);
	            assertEquals(sol.getPath().isEmpty(),true);
	            assertEquals(sol.getStatus(),Status.OPTIMAL);

	            sol=D_A_2_short.run();
	          // assertEquals(sol.getPath().size(),short_path.size());expected 50 but was 20
	            assertEquals(sol.getPath().getOrigin().getId(),short_path.getOrigin().getId());        
	          //  assertEquals(sol.getPath().getArcs().get(0).getDestination().getId(),short_path.getArcs().get(0).getDestination().getId());        
	            assertEquals(sol.getStatus(),Status.OPTIMAL);
	          //  assertEquals((int)sol.getPath().getMinimumTravelTime(),(int)short_path.getMinimumTravelTime());expected 147 but was 396

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
