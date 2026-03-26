/* ---------------------------------------------------------------------------------
The GraphA1NN class is the starting class for the graph-based ANN search

(c) Robert Laganiere, CSI2510 2023  
------------------------------------------------------------------------------------*/
//MY WORK 
import java.io.*;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


class GraphA1NN {
	
	private UndirectedGraph <LabelledPoint> annGraph;
    private PointSet dataset;
	//private KNN knn;
    private int S;
	
	// construct a graph from a file
    public GraphA1NN(String fvecs_filename) {

	    annGraph= new UndirectedGraph<>();
		dataset= new PointSet(PointSet.read_ANN_SIFT(fvecs_filename));
    }

	// construct a graph from a dataset
    public GraphA1NN(PointSet set){
		
	   annGraph= new UndirectedGraph<>();
       this.dataset = set;
	   //knn= new KNN(set);
    }

	public int setS (int S){
        return this.S=S;
    }
    

    //build graph with k nearestNeighbors + calls findNN from KNN class
    public void constructKNNGraph(int k) {
        
        ArrayList<LabelledPoint> pointsList = dataset.getPointsList();
        ArrayList<List<Integer> > adjacency;//= GraphA1NN.readAdjacencyFile("knn.txt", 10000);
        try {
			adjacency =  GraphA1NN.readAdjacencyFile("knn.txt", 10000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        // for all query points
        for(LabelledPoint q : pointsList) 
        //connect the query point q with a point in the list of neighbours of q
            for(int j= 0; j<k;j++){
            LabelledPoint currentPoint = dataset.getPointsList().get(adjacency.get(q.getLabel()).get(j));
                if (!annGraph.getNeighbors(q).contains((currentPoint))){// if we already have an edge there, do nothing
                    annGraph.addEdge(q, currentPoint);
                }
            }
    }
    
    
    
    
    public LabelledPoint find1NN(LabelledPoint queryPoint) {
        
        ArrayList<LabelledPoint> myArray = new ArrayList<>(S);
        ArrayList<LabelledPoint> pointsList = dataset.getPointsList();
    
        for (LabelledPoint point : pointsList) {
            point.unchecked(); // Reset checked flag for all points
        }
    
        Random random = new Random();
        LabelledPoint initialVertex = pointsList.get(random.nextInt(pointsList.size()));
        //System.out.println("initialVertex = " + initialVertex);
        double initialDistance = initialVertex.distanceTo(queryPoint);
        initialVertex.setKey(initialDistance);
        initialVertex.setIKey(queryPoint.getLabel());
        myArray.add(initialVertex);
        //System.out.println("my Array"+ myArray);
    
        while (myArray.stream().anyMatch(point -> !point.isChecked())) {

            LabelledPoint currentPoint= null;
    
            for ( LabelledPoint point : myArray ) {
                if ( !point.isChecked()){
                    currentPoint = point;
                    break;
                }
            }
            //System.out.println("currentPoint" + currentPoint);
            if (currentPoint == null) {
                break; // No more unchecked vertices in myArray
            }
    
            currentPoint.checked(); // Mark vertex as checked
            //myArray.remove(currentPoint); // Remove the checked vertex
            //System.out.println("currentPoint" + currentPoint);
            
            for (LabelledPoint neighbor : annGraph.getNeighbors(currentPoint)) {
                    //System.out.println("neighbordx : " + annGraph.getNeighbors(currentPoint));
                if (neighbor.isChecked()) {
					continue;
				}

                if (!neighbor.isChecked() && neighbor.getIKey()!= initialVertex.getLabel()) {
                    double distanceToQuery = neighbor.distanceTo(queryPoint);
                    neighbor.setKey(distanceToQuery);
                    neighbor.setIKey(currentPoint.getLabel());

                    if (myArray.size() > S) {
                            myArray.remove(S);
                    }
                    
                 myArray.add(neighbor);
                 myArray.sort(Comparator.comparingDouble(LabelledPoint::getKey));
                }  
            }
        }
    
        //System.out.println(" my array after all:  "+ myArray);
        return myArray.get(0); // Return the nearest neighbor
        
    }
    
       

	

	public static ArrayList<List<Integer> > readAdjacencyFile(String fileName, int numberOfVertices) 
	                                                                 throws Exception, IOException
	{	
		ArrayList<List<Integer> > adjacency= new ArrayList<List<Integer> >(numberOfVertices);
		for (int i=0; i<numberOfVertices; i++) 
			adjacency.add(new LinkedList<>());
		
		// read the file line by line
	    String line;
        try (BufferedReader flightFile = new BufferedReader( new FileReader(fileName))) {
            // each line contains the vertex number followed 
            // by the adjacency list
            while( ( line = flightFile.readLine( ) ) != null ) {
            	StringTokenizer st = new StringTokenizer( line, ":,");
            	int vertex= Integer.parseInt(st.nextToken().trim());
            	while (st.hasMoreTokens()) { 
            	    adjacency.get(vertex).add(Integer.parseInt(st.nextToken().trim()));
            	}
            }
        }
        
		return adjacency;
	}
	
	public int size() { return annGraph.size(); }

    //public static void main(String[] args) throws IOException, Exception {
		
		/*ArrayList<List<Integer>> adjList = GraphA1NN.readAdjacencyFile("knn.txt", 10000);
        ArrayList<LabelledPoint> points = PointSet.read_ANN_SIFT("siftsmall_base.fvecs");
        ArrayList<LabelledPoint> queryPoints = PointSet.read_ANN_SIFT("siftsmall_query.fvecs");
        LabelledPoint query;
        LabelledPoint nearestNeighbor;

        GraphA1NN knnGraph = new GraphA1NN("siftsmall_base.fvecs");
        knnGraph.constructKNNGraph(25);

        System.out.println("Number of vertices: " + knnGraph.size());

        Random random = new Random();
        int randomIndex = random.nextInt(queryPoints.size());
        query = queryPoints.get(randomIndex);

        System.out.println("Query point: " + query);

        long startTime = System.currentTimeMillis();
        nearestNeighbor = knnGraph.find1NN(query);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Nearest neighbor: " + nearestNeighbor);
        System.out.println("Execution time of find1NN: " + executionTime + " milliseconds");
        System.out.println("Query point label: " + query.getLabel());
        System.out.println("Nearest neighbor label: " + nearestNeighbor.getLabel());

    }*/

    public static void main(String[] args) throws IOException, Exception {
		
		ArrayList<List<Integer>> adjacency= GraphA1NN.readAdjacencyFile("knn.txt", 10000);
		ArrayList<LabelledPoint> basePoints = PointSet.read_ANN_SIFT("siftsmall_base.fvecs");
		ArrayList<LabelledPoint> baseQueryPoints = PointSet.read_ANN_SIFT("siftsmall_query.fvecs");
		LabelledPoint q;
		LabelledPoint result ;
		int S= 25;


		// Create an instance of the GRAPHA1NN class
		GraphA1NN graph = new GraphA1NN("siftsmall_base.fvecs");
		// Construct the k-nearest neighbors graph with K
        graph.setS(S);
		graph.constructKNNGraph(25);

		System.out.println("Number of vertices: "+ graph.size());


		for (LabelledPoint qPt: baseQueryPoints ) {

			//System.out.println("Query point: " + qPt.getLabel());

			//Measure the execution time of find1NN
			long startTime = System.currentTimeMillis();
			result = graph.find1NN(qPt);
			long endTime = System.currentTimeMillis();
			long executionTime = endTime - startTime;

			//Print the result and execution time;
			System.out.println( qPt.getLabel()+": " + result.getLabel());
			
			//System.out.println("Query point label : " + qPt.getLabel());
			//System.out.println("Result label : "+ result.getLabel());
            System.out.println("Execution time of find1NN: " + executionTime + " milliseconds");
		}
    }
}

