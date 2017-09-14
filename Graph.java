
public class Graph
{
    String[] nodes;
    int[][] adjacencyMatrix;
    
    
    int vertexCount;
    int edgeCount;
    
    public Graph(int[][] aMatrix){
        adjacencyMatrix = aMatrix;
    }
    
    public Graph(int nodeCount){
        adjacencyMatrix = new int[nodeCount][nodeCount];
        
         for(int a = 0; a < vertexCount; a++){
            for(int b = 0; b <vertexCount; b++){
                adjacencyMatrix[a][b] = 0;
            }
        }
        
        
        nodes = new String[nodeCount];
        vertexCount = nodeCount;
    }
    
    public void addEdge(int v1, int v2){
        adjacencyMatrix[v1][v2] = 1;
        adjacencyMatrix[v2][v1] = 1;
        edgeCount++;
    }
    
    public void addWeightedEdge(int v1, int v2, int weight){
        adjacencyMatrix[v1][v2] = weight;
        adjacencyMatrix[v2][v1] = weight;
        edgeCount++;
    }
    
    public boolean edge(int v1, int v2){
   
        if( adjacencyMatrix[v1][v2] > 0){
            return true;
        }
        else{
            return false;
        }
        
    }
    
    public int edgeWeight(int v1, int v2){
   
        return adjacencyMatrix[v1][v2];
        
    }
    
    //publc Enumeration 
    
    //publc Enumeration 
    
    public int numVertices(){
        return vertexCount;
    }
    
    public int numEdges(){
        return 0;
    }
    
    public void printNameless(){
        System.out.print("vertices:\t");
        for(int c = 0; c < vertexCount; c++){
            System.out.printf("%3d ", c);
        }
        System.out.println("");
        
        for(int a = 0; a < vertexCount; a++){
            
            
            System.out.printf("vertex %2d:\t", a);
            
            for(int b = 0; b <vertexCount; b++){
                System.out.printf("%3d ", adjacencyMatrix[a][b]);
            }
            
            System.out.println("");
        }
    }
    
    public void printNameed(String[] nodeNames){
        System.out.print("vertices:\t");
        for(int c = 0; c < vertexCount; c++){
            System.out.printf("%d ", c);
        }
        System.out.println("\n");
        
        for(int a = 0; a < vertexCount; a++){
            
            
            System.out.printf("vertex %2d:\t", a);
            
            for(int b = 0; b <vertexCount; b++){
                System.out.printf("%d ", adjacencyMatrix[a][b]);
            }
            
            System.out.println("");
        }
    }
    
}
