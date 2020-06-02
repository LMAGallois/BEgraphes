package org.insa.graphs.algorithm.shortestpath;

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

public class AS_Dij_SansOracleTest {

	private static ShortestPathData  dataBLF0_short, dataBLF0_long, dataBLF2_short, dataBLF2_long;
	private static ShortestPathData  dataAS0_short, dataAS0_long, dataAS2_short, dataAS2_long;
	private static ShortestPathData  dataD0_short, dataD0_long, dataD2_short, dataD2_long;
	private static Graph graph;

    private static Path short_path,longPath;
	
    @BeforeClass 
	public static void InitAll() throws Exception {
		GraphReader reader = new BinaryGraphReader(new DataInputStream( new BufferedInputStream(new FileInputStream("C:/Users/leoni/Documents/graphes/BE_graphes/cartes/haute-garonne.mapgr"))));
        graph = reader.read();
        
        PathReader pathreader_short=new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream("C:/Users/leoni/Documents/graphes/BE_graphes/path/path_fr31_insa_bikini_canal.path"))));
        short_path=pathreader_short.readPath(graph);
        
        PathReader pathreader_long=new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream("C:/Users/leoni/Documents/graphes/BE_graphes/path/path_fr31_insa_aeroport_time.path"))));
        longPath=pathreader_long.readPath(graph);
        
        dataD0_short =new ShortestPathData(graph, short_path.getOrigin(), short_path.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );
        dataD0_long =new ShortestPathData(graph, longPath.getOrigin(), longPath.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );

        //avec arc inspector 2 (le 3e)
        dataD2_short =new ShortestPathData(graph, short_path.getOrigin(),short_path.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
        dataD2_long =new ShortestPathData(graph, longPath.getOrigin(), longPath.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
    
        dataAS0_short =new ShortestPathData(graph, short_path.getOrigin(), short_path.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );
        dataAS0_long =new ShortestPathData(graph, longPath.getOrigin(), longPath.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );

        //avec arc inspector 2 (le 3e)
        dataAS2_short =new ShortestPathData(graph, short_path.getOrigin(),short_path.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
        dataAS2_long =new ShortestPathData(graph, longPath.getOrigin(), longPath.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
        
        dataBLF0_short =new ShortestPathData(graph, short_path.getOrigin(), short_path.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );
        dataBLF0_long =new ShortestPathData(graph, longPath.getOrigin(), longPath.getDestination(), ArcInspectorFactory.getAllFilters().get(0) );

        //avec arc inspector 2 (le 3e)
        dataBLF2_short =new ShortestPathData(graph, short_path.getOrigin(),short_path.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
        dataBLF2_long =new ShortestPathData(graph, longPath.getOrigin(), longPath.getDestination(), ArcInspectorFactory.getAllFilters().get(2) );
	}
    
        
        @Test
        public void AStarTest() {

            BellmanFordAlgorithm D_A_0_shortBLF=new BellmanFordAlgorithm(dataBLF0_short);
            BellmanFordAlgorithm D_A_0_longBLF=new BellmanFordAlgorithm(dataBLF0_long);

            BellmanFordAlgorithm D_A_2_shortBLF=new BellmanFordAlgorithm(dataBLF2_short);
            BellmanFordAlgorithm D_A_2_longBLF=new BellmanFordAlgorithm(dataBLF2_long);
            
            AStarAlgorithm D_A_0_shortAS=new AStarAlgorithm(dataAS0_short);
            AStarAlgorithm D_A_0_longAS=new AStarAlgorithm(dataAS0_long);

            AStarAlgorithm D_A_2_shortAS=new AStarAlgorithm(dataAS2_short);
            AStarAlgorithm D_A_2_longAS=new AStarAlgorithm(dataAS2_long);
            
            DijkstraAlgorithm D_A_0_shortD=new DijkstraAlgorithm(dataD0_short);
            DijkstraAlgorithm D_A_0_longD=new DijkstraAlgorithm(dataD0_long);

            DijkstraAlgorithm D_A_2_shortD=new DijkstraAlgorithm(dataD2_short);
            DijkstraAlgorithm D_A_2_longD=new DijkstraAlgorithm(dataD2_long);
            
            ShortestPathSolution solBLF=null;

            ShortestPathSolution solAS=null;

            ShortestPathSolution solD=null;
            
            //limite inf : BLF (ca aurait pu etre maxlength de graphstat
            //limite sup: vol d'oiseau
            
            solBLF=D_A_0_shortBLF.run();
            solAS=D_A_0_shortAS.run();
            solD=D_A_0_shortD.run();
            double dist=short_path.getOrigin().getPoint().distanceTo(short_path.getDestination().getPoint());
            
        	//System.out.println(D_A_0_shortBLF.getInputData().getMode());
        	//System.out.println(D_A_0_shortBLF.getInputData().getMode());

            System.out.println("pour as :\n");
            if(solAS.getPath().getLength()>dist) {
            	System.out.println("c'est normal avec limite sup \n");
            }
            if(solAS.getPath().getLength()<=solBLF.getPath().getLength()) {
            	System.out.println("c'est normal avec limite inf \n");
            }
            
            System.out.println("pour d :\n");
            if(solD.getPath().getLength()>dist) {
            	System.out.println("c'est normal avec limite sup \n");
            }
            if(solD.getPath().getLength()<=solBLF.getPath().getLength()) {
            	System.out.println("c'est normal avec limite inf \n");
            }
            
            solBLF=D_A_0_longBLF.run();
            solAS=D_A_0_longAS.run();
            solD=D_A_0_longD.run();     
            dist=longPath.getOrigin().getPoint().distanceTo(longPath.getDestination().getPoint());

            System.out.println("pour as :\n");
            if(solAS.getPath().getLength()>dist) {
            	System.out.println("c'est normal avec limite sup \n");
            }
            if(solAS.getPath().getLength()<=solBLF.getPath().getLength()) {
            	System.out.println("c'est normal avec limite inf \n");
            }
            
            System.out.println("pour d :\n");
            if(solD.getPath().getLength()>dist) {
            	System.out.println("c'est normal avec limite sup \n");
            }
            if(solD.getPath().getLength()<=solBLF.getPath().getLength()) {
            	System.out.println("c'est normal avec limite inf \n");
            }
            
            solBLF=D_A_2_shortBLF.run();
            solAS=D_A_2_shortAS.run();
            solD=D_A_2_shortD.run();
            double vitesse=Double.min(graph.getGraphInformation().getMaximumSpeed(),D_A_2_shortBLF.getInputData().getMaximumSpeed());
            double time=(double)3.6*short_path.getOrigin().getPoint().distanceTo(short_path.getDestination().getPoint())/vitesse;
            
            System.out.println("pour as :\n");
            if(solAS.getPath().getMinimumTravelTime()>time) {
            	System.out.println("c'est normal avec limite sup \n");
            }
            /*if(solAS.getPath().getMinimumTravelTime()<=solBLF.getPath().getMinimumTravelTime()) {
            	System.out.println("c'est normal avec limite inf \n");
            }*/
            
            System.out.println("pour d :\n");
            if(solD.getPath().getMinimumTravelTime()>time) {
            	System.out.println("c'est normal avec limite sup \n");
            }
            if(solD.getPath().getMinimumTravelTime()<=solBLF.getPath().getMinimumTravelTime()) {
            	System.out.println("c'est normal avec limite inf \n");
            }
            
            solBLF=D_A_2_longBLF.run();
            solAS=D_A_2_longAS.run();
            solD=D_A_2_longD.run();     
            vitesse=Double.min(graph.getGraphInformation().getMaximumSpeed(),D_A_2_longBLF.getInputData().getMaximumSpeed());
            time=(double)3.6*short_path.getOrigin().getPoint().distanceTo(longPath.getDestination().getPoint())/vitesse;
            
            System.out.println("pour as :\n");
            if(solAS.getPath().getMinimumTravelTime()>time) {
            	System.out.println("c'est normal avec limite sup \n");
            }
            //System.out.println(solAS.getPath().getLength());
        	//System.out.println(solBLF.getPath().getLength());
        	//System.out.println(D_A_2_longBLF.getInputData().getMode());
        	//System.out.println(D_A_2_longAS.getInputData().getMode()); 
            //System.out.println(solBLF.getPath().getMinimumTravelTime()); BLF utilise la vitesse maximale autorisée par le grpahe
            //System.out.println(solAS.getPath().getMinimumTravelTime()); tandis que AS utilise le minimum entre celle ci et la vitesse max de D_A_2 qui est cette dernière donc forcément BLF plus rapide mais si on prend vitesse max du graphe pour AS aussi alors pareil
            
            /*if(solAS.getPath().getMinimumTravelTime()<=solBLF.getPath().getMinimumTravelTime()) {
            	System.out.println("c'est normal avec limite inf \n");
            }*/
            
            System.out.println("pour d :\n");
            if(solD.getPath().getMinimumTravelTime()>time) {
            	System.out.println("c'est normal avec limite sup \n");
            }
            if(solD.getPath().getMinimumTravelTime()<=solBLF.getPath().getMinimumTravelTime()) {
            	System.out.println("c'est normal avec limite inf \n");
            }
           
	}
}
