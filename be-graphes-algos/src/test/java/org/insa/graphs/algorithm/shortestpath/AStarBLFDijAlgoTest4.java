package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class AStarBLFDijAlgoTest4 {

	private static ShortestPathData  dataBLF0_length, dataBLF0_time, dataBLF2_length, dataBLF2_time;
	private static ShortestPathData dataAS0_length, dataAS0_time, dataAS2_length, dataAS2_time;
	private static ShortestPathData  dataD0_length, dataD0_time, dataD2_length, dataD2_time;

    private static Path length_path,timePath;
	
    @BeforeClass 
	public static void InitAll() throws Exception {
		GraphReader reader = new BinaryGraphReader(new DataInputStream( new BufferedInputStream(new FileInputStream("C:/Users/leoni/Documents/graphes/BE_graphes/cartes/haute-garonne.mapgr"))));
        Graph graph = reader.read();
        
        PathReader pathreader_length=new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream("C:/Users/leoni/Documents/graphes/BE_graphes/path/path_fr31_insa_aeroport_length.path"))));
        length_path=pathreader_length.readPath(graph);
        
        PathReader pathreader_time=new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream("C:/Users/leoni/Documents/graphes/BE_graphes/path/path_fr31_insa_aeroport_time.path"))));
        timePath=pathreader_time.readPath(graph);
        
        dataD0_length =new ShortestPathData(graph, length_path.getOrigin(), length_path.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );
        dataD0_time =new ShortestPathData(graph, timePath.getOrigin(), timePath.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );

        //avec arc inspector 2 (le 3e)
        dataD2_length =new ShortestPathData(graph, length_path.getOrigin(),length_path.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
        dataD2_time =new ShortestPathData(graph, timePath.getOrigin(), timePath.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
    
        dataAS0_length =new ShortestPathData(graph, length_path.getOrigin(), length_path.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );
        dataAS0_time =new ShortestPathData(graph, timePath.getOrigin(), timePath.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );

        //avec arc inspector 2 (le 3e)
        dataAS2_length =new ShortestPathData(graph, length_path.getOrigin(),length_path.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
        dataAS2_time =new ShortestPathData(graph, timePath.getOrigin(), timePath.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
        
        dataBLF0_length =new ShortestPathData(graph, length_path.getOrigin(), length_path.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );
        dataBLF0_time =new ShortestPathData(graph, timePath.getOrigin(), timePath.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );

        //avec arc inspector 2 (le 3e)
        dataBLF2_length =new ShortestPathData(graph, length_path.getOrigin(),length_path.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
        dataBLF2_time =new ShortestPathData(graph, timePath.getOrigin(), timePath.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
        
	}
    
        
        @Test
        public void AStarTest() {

            BellmanFordAlgorithm D_A_0_lengthBLF=new BellmanFordAlgorithm(dataBLF0_length);
            BellmanFordAlgorithm D_A_0_timeBLF=new BellmanFordAlgorithm(dataBLF0_time);

            BellmanFordAlgorithm D_A_2_lengthBLF=new BellmanFordAlgorithm(dataBLF2_length);
            BellmanFordAlgorithm D_A_2_timeBLF=new BellmanFordAlgorithm(dataBLF2_time);
            
            AStarAlgorithm D_A_0_lengthAS=new AStarAlgorithm(dataAS0_length);
            AStarAlgorithm D_A_0_timeAS=new AStarAlgorithm(dataAS0_time);

            AStarAlgorithm D_A_2_lengthAS=new AStarAlgorithm(dataAS2_length);
            AStarAlgorithm D_A_2_timeAS=new AStarAlgorithm(dataAS2_time);
            
            DijkstraAlgorithm D_A_0_lengthD=new DijkstraAlgorithm(dataD0_length);
            DijkstraAlgorithm D_A_0_timeD=new DijkstraAlgorithm(dataD0_time);

            DijkstraAlgorithm D_A_2_lengthD=new DijkstraAlgorithm(dataD2_length);
            DijkstraAlgorithm D_A_2_timeD=new DijkstraAlgorithm(dataD2_time);
            
            ShortestPathSolution solBLF=null;
            ShortestPathSolution solBLF1=null;

            ShortestPathSolution solAS=null;
            ShortestPathSolution solAS1=null;

            ShortestPathSolution solD=null;
            ShortestPathSolution solD1=null;

           
            solBLF=D_A_0_lengthBLF.run();
            solBLF1=D_A_0_timeBLF.run();

            assertEquals(solBLF.getPath().size(),solBLF1.getPath().size());
            assertEquals((int)solBLF.getPath().getLength(),(int)solBLF1.getPath().getLength());
            
            solAS=D_A_0_lengthAS.run();
            solAS1=D_A_0_timeAS.run();
            
            assertEquals(solAS.getPath().size(),solAS1.getPath().size());
            assertEquals((int)solAS.getPath().getLength(),(int)solAS1.getPath().getLength());
            
            solD=D_A_0_lengthD.run();
            solD1=D_A_0_timeD.run();     
            
            assertEquals(solD.getPath().size(),solD1.getPath().size());
            assertEquals((int)solD.getPath().getLength(),(int)solD1.getPath().getLength());

            
            solBLF=D_A_2_lengthBLF.run();
            solBLF1=D_A_2_timeBLF.run();

            assertEquals(solBLF.getPath().size(),solBLF1.getPath().size());
            assertEquals((int)solBLF.getPath().getMinimumTravelTime(),(int)solBLF1.getPath().getMinimumTravelTime());

            solAS=D_A_2_lengthAS.run();
            solAS1=D_A_2_timeAS.run();

            assertEquals(solAS.getPath().size(),solAS1.getPath().size());
            assertEquals((int)solAS.getPath().getMinimumTravelTime(),(int)solAS1.getPath().getMinimumTravelTime());

            solD=D_A_2_lengthD.run();    
            solD1=D_A_2_timeD.run();            

            assertEquals(solD.getPath().size(),solD1.getPath().size());
            assertEquals((int)solD.getPath().getMinimumTravelTime(),(int)solD1.getPath().getMinimumTravelTime());

	}
}
