
public class GraphAlgorithms
{
    public static int[] dijkstra(Graph g, int source, boolean printPath){//, int target){
        //Create variables
        int vertexCount = g.numVertices(); //Number of vertices
        int verticesDone = 0;              //Number of completed vertices
        int minVertex = -1;                //Vertex with min distance, if ever -1, bug occurred
        int minDistance;
        int edgeWeight;                    //Contains edge weight between min vertex and neighbors
        
        // int comparisons1 =0;
        //int comparisons2 =0;
        //int comparisons3 =0;
        
        //Exchanges and Comparisons
        int exchanges = 0;
        int comparisons = 0;
        int assignments = 0;

        //Create arrays
        boolean[] completedVertices = new boolean[vertexCount];   //Set of which vertices are completed
        int[] distance = new int[vertexCount];                   //Set of all node distances, will represent nodes, source set to 0, rest set to max
        int[] path = new int[vertexCount];                       //Set of predecessor nodes for node numbered by index

        //Initialize arrays
        for(int a = 0; a < vertexCount; a++){ //For all vertices
            completedVertices[a] = false;       //All vertices marked as incomplete
            distance[a] = Integer.MAX_VALUE;    //Set all distances to max (just convenient to include in loop)
        }
        assignments+= vertexCount*2;
        
        
        
        distance[source] = 0; //Source vertex has distance 0
        path[source] = source;

        //Loop will etermine shortest path between source node and every other node, return as array of precedessors
        while(verticesDone != vertexCount){ //Continue until every vertice is has been completed

            
            //Following loop gets the node with shortest distance
            minDistance = Integer.MAX_VALUE; //Set to max value so that it will always be replaced by legitimate distances
            for(int a = 0; a < vertexCount; a++){   //Go through every vertex..
                if(!completedVertices[a] && minDistance > distance[a] ){    //If that vertex is not completed already and it has a shorter distance..
                    minDistance = distance[a];  //Mark it as the shortest vertex
                    minVertex = a;
                    assignments+=2;
                    
                }   
               
                if(completedVertices[a]){
                    comparisons++;
                    //comparisons1++;
                }
            }
            
            //Work on neighbors, changing distances and making path predecessors
            if( completedVertices[minVertex]/*distance[minVertexInd] == Integer.MAX_VALUE*/  ){ //If the min distance found is max, then remaining nodes are unconnected
                for(int b =0; b < vertexCount; b++){
                    if(!completedVertices[b]) {path[b] = -1; 
                        
                    }
                }

                verticesDone = vertexCount;
            }
            else{

                for(int b = 0; b < vertexCount; b++){
                    edgeWeight = g.edgeWeight(minVertex,b);

                    if( edgeWeight > 0 && !completedVertices[b] ){ //If there is an edge, compare distances
                        if( distance[b] > edgeWeight + distance[minVertex]){ //If the current path distance to the neighbor is greater than the new path with the edge, replace values 
                            distance[b] = edgeWeight + distance[minVertex];
                            path[b] = minVertex;
                            assignments+=2;
                        }
                        comparisons++;
                        //comparisons2++;
                    }
                    comparisons++;
                    //comparisons3++;
                }
                //End Iteration by essentially removing 

                /*
                System.out.printf("Distances of step %d\n", verticesDone+1);
                for(int a = 0; a < vertexCount; a++){
                System.out.printf("node %d: %d",a,distance[a]);
                System.out.print(completedVertices[a] ? "completed\n":"\n");
                }
                System.out.println("");
                 */
                completedVertices[minVertex] = true;
                //assignments++;
                verticesDone++;
            }
        }
        
        //System.out.printf("Comparisons in min node finding: %d\n", comparisons1);
        //System.out.printf("Comparisons of distances: %d\n", comparisons2);
        //System.out.printf("Comparisons done to determine neighbors: %d" , comparisons3);
        /*System.out.printf("Path\n", verticesDone+1);
        for(int a = 0; a < vertexCount; a++){
        System.out.printf("predecessor of node %d: %d\n",a,path[a]);

        }*/
        if(printPath){
            return path;
        }
        else{
            return new int[]{comparisons, exchanges,  assignments};
        }
    }

    public static int[] dijkstraAL(GraphAdjList g, int source, boolean printPath){//, int target){
        //Create variables
        int vertexCount = g.numVertices(); //Number of vertices
        int verticesDone = 0;              //Number of completed vertices
        int minVertex = -1;                //Vertex with min distance, if ever -1, bug occurred
        int minDistance;
        int edgeWeight;                    //Contains edge weight between min vertex and neighbors

        int exchanges = 0;
        int comparisons = 0;
        int assignments = 0;

        //Create arrays
        boolean[] completedVertices = new boolean[vertexCount];   //Set of which vertices are completed
        int[] distance = new int[vertexCount];                   //Set of all node distances, source set to 0, rest set to max
        int[] path = new int[vertexCount];                       //Set of predecessor nodes for node numbered by index

        //Initialize arrays
        for(int a = 0; a < vertexCount; a++){ //For all vertices
            completedVertices[a] = false;       //All vertices marked as incomplete
            distance[a] = Integer.MAX_VALUE;    //Set all distances to max (just convenient to include in loop)
        }
        assignments+= vertexCount*2;

        distance[source] = 0; //Source vertex has distance 0
        path[source] = source;

        //Loop will etermine shortest path between source node and every other node, return as array of precedessors
        while(verticesDone != vertexCount){ //Continue until every vertice is has been completed

            //Following loop gets the node with shortest distance
            minDistance = Integer.MAX_VALUE; //Set to max value so that it will always be replaced by legitimate distances
            for(int a = 0; a < vertexCount; a++){   //Go through every vertex..
                if(!completedVertices[a] && minDistance > distance[a] ){    //If that vertex is not completed already and it has a shorter distance..
                    minDistance = distance[a];  //Mark it as the shortest vertex
                    minVertex = a;
                    assignments+= 2;
                }   
                
                if(completedVertices[a])comparisons++;
            }

            //Work on neighbors, changing distances and making path predecessors
            if( completedVertices[minVertex]/*distance[minVertexInd] == Integer.MAX_VALUE*/  ){ //If the min distance found is max, then remaining nodes are unconnected
                for(int b =0; b < vertexCount; b++){
                    if(!completedVertices[b]) {path[b] = -1; 
                        
                    }
                }

                verticesDone = vertexCount;
            }
            else{
                Node neighbor = g.getNeighbor(minVertex);
                int nodeInd;
                while (neighbor != null){
                    nodeInd = neighbor.getIndex();
                    edgeWeight = neighbor.getWeight();

                    if(!completedVertices[nodeInd] ){ //If there is an edge, compare distances
                        if( distance[nodeInd] > edgeWeight + distance[minVertex]){ //If the current path distance to the neighbor is greater than the new path with the edge, replace values 
                            distance[nodeInd] = edgeWeight + distance[minVertex];
                            path[nodeInd] = minVertex;
                            assignments+=2;
                        }
                        comparisons++;
                    }

                    neighbor = neighbor.getNext();
                }

                completedVertices[minVertex] = true;

                verticesDone++;

            }
        }
        if(printPath){
            return path;
        }
        else{
            return new int[]{comparisons, exchanges,  assignments};
        }
    }

    public static void printPath(int[] path, int source, int target){
        String pathString = "";
        int pathInd = target;
        if(path[target] < 0){
            System.out.println("Target node has no paths from source node");
        }
        else{
            while(pathInd!=source){
                pathString = "-->" + pathInd + pathString;
                pathInd = path[pathInd];
            }
            pathString = source + pathString;
        }

        System.out.println(pathString);
    }

    public static int[] allShortestPaths(Graph g){
        int vertices = g.numVertices();
        int[] allPaths = new int[3];
        int[] onePath;
        for(int a = 0; a < vertices; a++){
            onePath = dijkstra(g, a, false);
            allPaths[0] += onePath[0];
            allPaths[1] += onePath[1];
            allPaths[2] += onePath[2];
        }
        return allPaths;

    }

    public static int[] allShortestPathsAL(GraphAdjList g){
        int vertices = g.numVertices();
        int[] allPaths = new int[3];
        int[] onePath;
        for(int a = 0; a < vertices; a++){
            onePath = dijkstraAL(g, a, false);
            allPaths[0] += onePath[0];
            allPaths[1] += onePath[1];
            allPaths[2] += onePath[2];
        }
        return allPaths;

    }
}
