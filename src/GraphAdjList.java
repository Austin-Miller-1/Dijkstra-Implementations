
public class GraphAdjList
{
    Node[] adjList;
    int vertices;
    
    public GraphAdjList(int vertices){
        adjList = new Node[vertices];
        this.vertices = vertices;
        //Initialize with dummy nodes?
    }
    
    public void addEdge(int startNode, int secondNode, int edgeWeight){
        if(adjList[startNode] == null){
            adjList[startNode] = new Node(secondNode, edgeWeight);
        }
        else{
            Node node = adjList[startNode];
            while(node.getNext() != null){
                node = node.getNext();
            }
            
            node.setNext(new Node(secondNode, edgeWeight));
        }
        
        if(adjList[secondNode] == null){
            adjList[secondNode] = new Node(startNode, edgeWeight);
        }
        else{
            Node node = adjList[secondNode];
            while(node.getNext() != null){
                node = node.getNext();
            }
            
            node.setNext(new Node(startNode, edgeWeight));
        }
        
    }
    
    public Node getNeighbor(int node){
        return adjList[node];
    }
    
    public int numVertices(){
        return vertices;
    }
    
    public void printNameless(){
        Node node;
        for(int a = 0; a < adjList.length; a++){
            node = adjList[a];
            
            if(node == null){
                System.out.printf("Node %d: Empty\n",a);
                continue;
            }
            System.out.printf("Node %d:\t",a);
            
            while(node != null){
                node.printNode();
                node = node.getNext();
            }
            System.out.println("");
        }
    }
}
