
public class Node
{
    Node next;
    int index, weight, heapPos;
    
    public Node(int index, int weight){
        this.index = index;
        this.weight = weight;
    }
    
    public void setNext(Node node){
        next = node;
    }
    
    public Node getNext(){
        return next;
    }
    
    public int getWeight(){
        return weight;
    }
    
    public int getIndex(){
        return index;
    }

    
    public void printNode(){
        System.out.printf("Node %d, weight %d -- ",index, weight);
    }
}
