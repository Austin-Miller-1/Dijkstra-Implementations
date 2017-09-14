import java.lang.Math;

public class HeapDijkstra
{   
    Graph g1;
    GraphAdjList g2;
    int upheapOc=0;
    int downheapOc=0;
    boolean graphImp;

    int[] heap;
    int[] keys;
    int[] heapPos;
    boolean[] completedVertices;

    int vertices;
    int heapEnd;

    int exchanges = 0;
    int comparisons = 0;
    int assignments = 0;
    
    boolean debug = false;
    boolean debug2 = false;

    int [] onePath;
    int[][] allPaths;

    public HeapDijkstra(Graph g){
        g1 = g;
        vertices = g.numVertices();

        graphImp = false;

        heap = new int[vertices+1];

        heapPos = new int[vertices];
        keys = new int[vertices];

        completedVertices = new boolean[vertices];
    }

    public HeapDijkstra(GraphAdjList g){
        g2 = g;
        vertices = g.numVertices();

        graphImp = true;

        heap = new int[vertices+1];

        heapPos = new int[vertices];
        keys = new int[vertices];

        completedVertices = new boolean[vertices];
    }

    public void upheap(int elementHeapIndex){ /*element's index on heap*/
        boolean foundSpot = false;
        
        int elementNumber = heap[elementHeapIndex]; //NODE NUMBER
        int elementData = keys[elementNumber];      //DISTANCE FOR NODE
        int parentHeapInd = elementHeapIndex/2;     //NODE'S PARENT IN HEAP
        //assignments+=3;
        
        
        while(parentHeapInd > 0 && !foundSpot){
            
            if( keys[heap[parentHeapInd]] > elementData ){
                /*System.out.printf("\nBefore upheap:");
                for(int a = 0; a < vertices; a++){System.out.printf("\nHeap position of node %d = %d", a, heapPos[a]);}
                 */
                /*Swap heap positions*/
                heapPos[heap[elementHeapIndex]] = parentHeapInd;
                heapPos[heap[parentHeapInd]] = elementHeapIndex;
                
                /*Swap nodes on heap*/
                heap[elementHeapIndex] = heap[parentHeapInd];
                elementHeapIndex = parentHeapInd;
                parentHeapInd /=2;

                exchanges+=2;
                upheapOc++;
                /*System.out.printf("\nAfter upheap:");
                for(int a = 0; a < vertices; a++){System.out.printf("\nHeap position of node %d = %d", a, heapPos[a]);}*/
            }
            else{
                foundSpot = true;

            }
            comparisons+=2;
            heap[elementHeapIndex] = elementNumber;
            //assignments++;

        }

    }

    public void downheap(int elementIndex){ /*element's index on heap*/
        boolean foundSpot = false;

        int elementNumber = heap[elementIndex];
        int elementData = keys[elementNumber];
        int childInd = elementIndex*2; //left child
        int endOfHeap = heapEnd - 1;
        

        while(childInd <= endOfHeap && !foundSpot){
            
            if(childInd < endOfHeap && (keys[heap[childInd+1]] < keys[heap[childInd]]) ){
                childInd++; 

                //if there is a right child (heap ends after right child's index) 
                //and the right child has a smaller distance than the left child
                //then look at right child. We need to replace node with the new, smallest child at the level

                //else, look at left child
            }
            
            if(childInd < endOfHeap){comparisons+=2;}
            else{comparisons++;}

            if(( keys[heap[childInd]]<elementData)){ //if child is less than looked at element, swap with child
                /*Swap heap positions*/
                heapPos[heap[elementIndex]] = childInd;
                heapPos[heap[childInd]] = elementIndex;
                
                exchanges+=2;
                downheapOc++;
                //keys[elementIndex] = keys[childInd];
                //keys[childInd] = elementData;

                heap[elementIndex] = heap[childInd];
                elementIndex = childInd;
                childInd *= 2;

            }
            else{
                foundSpot = true;

            }
            
            comparisons++;
            heap[elementIndex] = elementNumber;
            
        }

    }

    public int[] getPath(){
        return onePath;

    }

    public int[] getPath(int source){
        return allPaths[source];
    }

    public void setDefault(){
        heapEnd = vertices + 1;
        for(int a = 0; a < vertices; a++){ //For all vertices
            heap[a+1] = a;        //All vertices added to unsorted heap
            heapPos[a] = a+1;
            keys[a] = Integer.MAX_VALUE;    //Set all distances to max (just convenient to include in loop)
            completedVertices[a] = false;
        }
        //assignments += 4 * vertices;
    }

    public void setCEdefault(){
        
            exchanges = 0;
            comparisons = 0;
            assignments = 0;
    
        
    }
    

    public  int[] dijkstraAL(int source, boolean printPath){
        int verticesDone = 0;              //Number of completed vertices
        int minVertex = -1;                //Vertex with min distance
        int edgeWeight;                    //Contains edge weight between min vertex and neighbors

        //int distanceOfCurrent;
        //Create arrays
        int[] path = new int[vertices];                       //Set of predecessor nodes for node numbered by index

        //Init arrays
        setDefault();
        assignments += 4 * vertices;
        //Since we must create the heap from the array and every node has distance infinity besides source, just replace first and source nodes
        //for super-convenience

        heap[1] = source; //node 0 replaced with source
        heap[source+1] = 0; //node source replaced with node 0
        
        heapPos[source] = 1;
        heapPos[0] = source+1;

        //exchanges+=2;
        
        keys[source] = 0; //Source vertex has distance 0
        path[source] = source;
        

        //Determine shortest between source node and every other node.
        while(verticesDone != vertices){ //While all nodes are not completed

            if(debug){
                System.out.println("Current heap:");
                for(int a = 1; a < heapEnd; a++){
                    System.out.printf("Node %d, distance %d\n", heap[a], keys[heap[a]]);
                }
                System.out.println("\n");
            }

            //Get vertex with shortest distance
            minVertex = heap[1]; //Min vertex is always root
            //System.out.printf("\nMin node is %d with distance %d\n\n", incompletedVertices[1], distance[incompletedVertices[1]]);
            //Remove min vertex from heap (place at end, shorten heap)

            heapEnd--;                                             //Heap shortened

            heap[1] = heap[heapEnd]; //New root is last node
            heap[heapEnd] = minVertex;              //Removed vertex is put at end
            exchanges++;
            
            heapPos[minVertex] = heapEnd;
            heapPos[heap[1]] = 1;
            exchanges++;
           

            if(debug){
                System.out.println("Heap after removal:");
                for(int a = 1; a < heapEnd; a++){
                    System.out.printf("Node %d, distance %d\n", heap[a], keys[heap[a]]);
                }
                System.out.println("\n");}

            downheap(1); //Maintain ordering property
            if(debug){
                System.out.println("Heap after fix:");
                for(int a = 1; a < heapEnd; a++){
                    System.out.printf("Node %d, distance %d, Position in heap %d\n", heap[a], keys[heap[a]], heapPos[heap[a]]);
                }
                System.out.println("\n");
            }

            //Work on neighbors, changing distances and making path predecessors
            if( completedVertices[minVertex]  ){ //If the min distance found is max, then remaining nodes are unconnected
                for(int b =0; b < vertices; b++){
                    if(!completedVertices[b]) {path[b] = -1; 
                    }
                }

                verticesDone = vertices;
            }
            else{

                Node neighbor = g2.getNeighbor(minVertex);
                int nodeInd, posInHeap;

                while (neighbor != null){
                    nodeInd = neighbor.getIndex();

                    edgeWeight = neighbor.getWeight();
                    
                    if(!completedVertices[nodeInd] ){ //If neighbor is not complete

                        if( keys[nodeInd] > edgeWeight + keys[minVertex]){ //If the current path distance to the neighbor is greater than the new path with the edge, replace values 
                            keys[nodeInd] = edgeWeight + keys[minVertex];
                            path[nodeInd] = minVertex;
                            assignments+=2;
                            posInHeap = heapPos[nodeInd];
                            upheap(posInHeap);

                            if(debug)System.out.printf("\nNode number %d with pos in heap %d was upheaped. It had distance %d\n", nodeInd, posInHeap, keys[nodeInd]);

                        }
                        comparisons++;
                    }

                    neighbor = neighbor.getNext();
                }

                if(debug){
                    System.out.printf("Distances of step %d\n", verticesDone+1);
                    for(int a = 0; a < vertices; a++){
                        System.out.printf("node %d: %d",a,keys[a]);
                        System.out.print(completedVertices[a] ? "completed\n":"\n");
                    }
                    System.out.println("");
                }

                completedVertices[minVertex] = true;
                verticesDone++;
            }
        }

        /*
        System.out.printf("Path\n", verticesDone+1);
        for(int a = 0; a < vertexCount; a++){
        System.out.printf("predecessor of node %d: %d\n",a,path[a]);

        }
         */
        //System.out.printf("Downheaps: %d\n Upheaps: %d\n", upheapOc, downheapOc);
        upheapOc = 0;
        downheapOc = 0;

        if(printPath){
            return path;
        }
        else{
            return new int[]{comparisons, exchanges,  assignments};
        }
    }

    public  int[] dijkstraAM(int source, boolean printPath){
        int verticesDone = 0;              //Number of completed vertices
        int minVertex = -1;                //Vertex with min distance
        int edgeWeight;                    //Contains edge weight between min vertex and neighbors

        //int distanceOfCurrent;
        //Create arrays
        int[] path = new int[vertices];                       //Set of predecessor nodes for node numbered by index
        assignments += 4 * vertices;
        //Init arrays
        setDefault();

        //Since we must create the heap from the array and every node has distance infinity besides sort, just replace first and source nodes
        //for super-convenience

        heap[1] = source; //node 0 replaced with source
        heap[source+1] = 0; //node source replaced with node 0

        heapPos[source] = 1;
        heapPos[0] = source+1;

        keys[source] = 0; //Source vertex has distance 0
        path[source] = source;

        //Determine shortest between source node and every other node.
        while(verticesDone != vertices){ //While all nodes are not completed

            if(debug2){
                System.out.println("Current heap:");
                for(int a = 1; a < heapEnd-1; a++){
                    System.out.printf("Node %d, distance %d, heap position %d\n", heap[a], keys[heap[a]], heapPos[heap[a]]);
                }
                System.out.println("\n");
            }

            //Get vertex with shortest distance
            minVertex = heap[1]; //Min vertex is always root
            //System.out.printf("\nMin node is %d with distance %d\n\n", incompletedVertices[1], distance[incompletedVertices[1]]);
            //Remove min vertex from heap (place at end, shorten heap)

            heapEnd--;                                             //Heap shortened

            heap[1] = heap[heapEnd]; //New root is last node
            heap[heapEnd] = minVertex;              //Removed vertex is put at end
            exchanges+=2;
            
            heapPos[minVertex] = heapEnd;
            heapPos[heap[1]] = 1;
            //assignments+=3;
            
            if(debug2){
                System.out.println("Heap after removal:");
                for(int a = 1; a < heapEnd; a++){
                    System.out.printf("Node %d, distance %d, heap position %d\n", heap[a], keys[heap[a]], heapPos[heap[a]]);
                }
                System.out.println("\n");
            }

            downheap(1); //Maintain ordering property

            if(debug2){System.out.println("Heap after fix:");
                for(int a = 1; a < heapEnd; a++){
                    System.out.printf("Node %d, distance %d, heap position %d\n", heap[a], keys[heap[a]], heapPos[heap[a]]);
                }
                System.out.println("\n");
            }
            
            if(debug2){System.out.println("Nodes completed at this point");
                for(int a = 1; a < vertices; a++){
                    System.out.println("Node " + a + ": " + (completedVertices[a]?"yes":"no"));
                }
                System.out.println("\n");
            }

            //Work on neighbors, changing distances and making path predecessors
            if( completedVertices[minVertex]  ){ //If the min distance found is max, then remaining nodes are unconnected
                for(int b =0; b < vertices; b++){
                    if(!completedVertices[b]) {path[b] = -1;
                   }
                }

                verticesDone = vertices;
            }
            else{


                for(int b = 0; b < vertices; b++){
                    edgeWeight = g1.edgeWeight(minVertex,b);
                    //assignments++;
                    if( edgeWeight > 0 && !completedVertices[b] ){ //If there is an edge, compare distances
                        if( keys[b] > edgeWeight + keys[minVertex]){ //If the current path distance to the neighbor is greater than the new path with the edge, replace values 
                            if(debug2)System.out.printf("\nNeighbor node %d was visited!", b);
                            
                            keys[b] = edgeWeight + keys[minVertex];
                            path[b] = minVertex;
                            assignments+=2;
                            if(debug2)System.out.printf("\nNode number %d with pos in heap %d was upheaped. It had distance %d\n", b, heapPos[b], keys[b]);
                            upheap(heapPos[b]);
                             
                            if(debug2){
                                System.out.printf("Heap after distance change of node %d:\n", b);
                                for(int a = 1; a < heapEnd; a++){
                                    System.out.printf("Node %d, distance %d\n", heap[a], keys[heap[a]]);
                                }
                                System.out.println("\n");
                            }
                        }
                        comparisons++;
                    }
                    comparisons++;
                }

                if(debug2){
                    System.out.printf("Distances of step %d\n", verticesDone+1);
                    for(int a = 0; a < vertices; a++){
                        System.out.printf("node %d: %d",a,keys[a]);
                        System.out.print(completedVertices[a] ? "completed\n":"\n");
                    }
                    System.out.println("");
                }
                if(debug2)System.out.printf("\nNode %d was completed! %d nodes out of %d are done!", minVertex, verticesDone+1, vertices );
                completedVertices[minVertex] = true;
                
                verticesDone++;
            }
        }

        /*
        System.out.printf("Path\n", verticesDone+1);
        for(int a = 0; a < vertexCount; a++){
        System.out.printf("predecessor of node %d: %d\n",a,path[a]);

        }
         */

        if(printPath){
            return path;
        }
        else{
            return new int[]{comparisons, exchanges,  assignments};
        }
    }
    
    
    public int[] allShortestPaths(){
        int[] allPaths = new int[3];
        int[] onePath;
        
        if(!graphImp){
            for(int a = 0; a < vertices; a++){
                setCEdefault();
                onePath = dijkstraAM(a, false);
                allPaths[0] += onePath[0];
                allPaths[1] += onePath[1];
                allPaths[2] += onePath[2];
            }
        }
        else{
            for(int a = 0; a < vertices; a++){
                setCEdefault();
                onePath = dijkstraAL(a,false);
                allPaths[0] += onePath[0];
                allPaths[1] += onePath[1];
                allPaths[2] += onePath[2];
            }
        }
        return allPaths;

    }
    
}
