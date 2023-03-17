package ai;
/**
* Creates a new Node object with the given col and row values.
* @param col The column value of the node.
* @param row The row value of the node.
*/
public class Node {
    Node parent;
    public int row;
    public int col;
    int gCost;
    int hCost;
    int fCost;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row){

        this.col = col;
        this.row = row;
    }
}
