import java.util.Scanner;

public class Project
{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        Graph graph;
        GraphAdjList graph2;

        String[] nodeList; //If user named nodes
        String input1 = "", input2;

        int nodeNum;
        int inputNode, node1, node2, weight;
        boolean resultMode,printAll = false;

        System.out.println("Do you want RESULTS or emprical DATA?");   
        if(scan.next().equals("RESULTS")){
            resultMode = true;
            System.out.println("Do you want ALL paths printed?"); 
            if(scan.next().equals("ALL")){
                printAll = true; 
            }
        }
        else{
            resultMode = false;
        }

        //Node num section
        System.out.println("Enter number of nodes:");   //Enter node num
        nodeNum = scan.nextInt();                       
        graph = new Graph(nodeNum);                     //Instantiate graph with node num
        graph2 = new GraphAdjList(nodeNum);
        nodeList = new String[nodeNum];

        
        //Edge creation section
        System.out.print("Enter edges");

        //Procedure for nameless nodes
        System.out.println(" (enter -1 to end):");
        node1= scan.nextInt();
        while(node1>=0)
        {
            node2 = scan.nextInt();
            weight = scan.nextInt();

            graph.addWeightedEdge(node1, node2, weight);
            graph2.addEdge(node1, node2, weight);
            node1 = scan.nextInt();
        }

        System.out.println("\n\nHere is the adjacency matrix:");
        graph.printNameless();

        System.out.println("\nHere is the adjacency list:");
        graph2.printNameless();

        System.out.println("Are we testing all shortest paths? (1/0)(no leads to testing of all implementations)"); 
        if(scan.nextInt()==1){  
            int[] allPaths1 = GraphAlgorithms.allShortestPaths(graph);
            int[] allPaths2 = GraphAlgorithms.allShortestPathsAL(graph2);

            HeapDijkstra graphHeapAM = new HeapDijkstra(graph);
            HeapDijkstra graphHeapAL = new HeapDijkstra(graph2);

            int[] allPaths3 = graphHeapAM.allShortestPaths();
            int[] allPaths4 = graphHeapAL.allShortestPaths();

            System.out.printf("ASP ADJ MATRIX + ARRAY \t Comparisons: %d \t Exchanges: %d \t Assignments: %d\n",allPaths1[0],allPaths1[1],allPaths1[2]);
            System.out.printf("ASP ADJ LIST + ARRAY \t Comparisons: %d \t Exchanges: %d \tAssignments: %d\n",allPaths2[0],allPaths2[1],allPaths2[2]);
            System.out.printf("ASP ADJ MATRIX + HEAP \t Comparisons: %d \t Exchanges: %d \t Assignments: %d\n",allPaths3[0],allPaths3[1],allPaths3[2]);
            System.out.printf("ASP ADJ LIST + HEAP \t Comparisons: %d \t Exchanges: %d \t Assignments: %d\n",allPaths4[0],allPaths4[1],allPaths4[2]);

        }
        else {
            if(!printAll){
                System.out.println("\n\nEnter source and target nodes:");   //Enter nodes
                
                    node1 = scan.nextInt();
                    node2 = scan.nextInt();
                

                int[] path1 = GraphAlgorithms.dijkstra(graph, node1,resultMode);
                int[] path2 = GraphAlgorithms.dijkstraAL(graph2, node1,resultMode);

                HeapDijkstra graphHeapAM = new HeapDijkstra(graph);
                HeapDijkstra graphHeapAL = new HeapDijkstra(graph2);

                int[] path3 = graphHeapAM.dijkstraAM(node1,resultMode);
                int[] path4 = graphHeapAL.dijkstraAL(node1,resultMode);

                if(resultMode){
                    System.out.printf("ADJ MATRIX + ARRAY:");
                    GraphAlgorithms.printPath(path1,node1, node2);
                    System.out.printf("ADJ LIST + ARRAY:");
                    GraphAlgorithms.printPath(path2,node1, node2);
                    System.out.printf("ADJ MATRIX + HEAP:");
                    GraphAlgorithms.printPath(path3,node1, node2);
                    System.out.printf("ADJ LIST + HEAP:");
                    GraphAlgorithms.printPath(path4,node1, node2);
                }
                else{
                    System.out.printf("ADJ MATRIX + ARRAY \t Comparisons: %d \t Exchanges: %d \t Assignments: %d\n",path1[0],path1[1],path1[2]);
                    System.out.printf("ADJ LIST + ARRAY \t Comparisons: %d \t Exchanges: %d \t Assignments: %d\n",path2[0],path2[1],path2[2]);
                    System.out.printf("ADJ MATRIX + HEAP \t Comparisons: %d \t Exchanges: %d \t Assignments: %d\n",path3[0],path3[1],path3[2]);
                    System.out.printf("ADJ LIST + HEAP \t Comparisons: %d \t Exchanges: %d \t Assignments: %d\n",path4[0],path4[1],path4[2]);
                }
            }
            else{
                HeapDijkstra graphHeapAM = new HeapDijkstra(graph);
                HeapDijkstra graphHeapAL = new HeapDijkstra(graph2);

                for(int a = 0; a < nodeNum; a++){
                    int[] path1 = GraphAlgorithms.dijkstra(graph, a,resultMode);
                    int[] path2 = GraphAlgorithms.dijkstraAL(graph2, a,resultMode);
                    int[] path3 = graphHeapAM.dijkstraAM(a,resultMode);
                    int[] path4 = graphHeapAL.dijkstraAL(a,resultMode);

                    for(int b = 0; b < nodeNum; b++){
                        GraphAlgorithms.printPath(path1,a, b);
                        GraphAlgorithms.printPath(path2,a, b);
                        GraphAlgorithms.printPath(path3,a, b);
                        GraphAlgorithms.printPath(path4,a, b);

                    }

                }

            }
        }

    }

    public static int getIndex(String[] list, String node){
        int a;
        for(a = 0; a < list.length; a++){
            if(node.equals(list[a])){
                return a;
            }
        }
        return a;

    }
}
