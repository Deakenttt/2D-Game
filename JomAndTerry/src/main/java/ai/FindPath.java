package ai;

import entity.Entity;
import main.GamePanel;

import java.util.ArrayList;

public class FindPath {
    GamePanel gp;
    Node[][] node;  // store all the node calculate base on the current start pos and goal pos
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached;
    int step = 0;

    public FindPath(GamePanel gp) {
        this.gp = gp;
        instantiateNode();
    }

    public void instantiateNode() {  // define all the nodes
        node = new Node[gp.maxScreenCol][gp.maxScreenRow];
        for (int i = 0; i < gp.maxScreenCol; i++) {
            for (int j = 0; j < gp.maxScreenRow; j++) {
                node[i][j] = new Node(i, j);
            }
        }
    }

    // reset all the node after start node and goal are modified
    public void resetNode() {
        for (int i = 0; i < gp.maxScreenCol; i++) {
            int j = 0;
            for (j = 0; j < gp.maxScreenRow; j++) {
                node[i][j].open = false;
                node[i][j].checked = false;
                node[i][j].solid = false;
            }
        }
        // reset other setting
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNode(int startCol, int startRow, int goalCol, int goalRow, Entity entity) {
        resetNode();

        // Set Start and Goal node
        try{startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        for (int i = 0; i < gp.maxScreenCol; i++) {
            for (int j = 0; j < gp.maxScreenRow; j++) {
                int tileNum = gp.tileManager.mapTileNum[i][j];
                if (gp.tileManager.tile[tileNum].collision)
                    node[i][j].solid = true;
                getCost(node[i][j]);   // defined the g cost, h cost, AND final cost for each node
            }
        }
    } catch(Exception e){
        System.out.println(e);
    }
    }

    public void getCost(Node node) {
        // Greedy cost
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;
        // Hus cost
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        // Final cost
        node.fCost = node.gCost + node.hCost;
    }

    public boolean aStarSearch() {
        int col, row, bestNodeIndex, bestNodefCost;
        while (!goalReached && step < 500) {
            col = currentNode.col;
            row = currentNode.row;
            bestNodeIndex = 0;
            bestNodefCost = 999;
            
            // check current node and then go to the next node
            currentNode.checked = true;
            openList.remove(currentNode);

            // check the upward node
            if (row - 1 >= 0)
                openNode(node[col][row - 1]);
            // check the leftward node
            if (col - 1 >= 0)
                openNode(node[col - 1][row]);
            // check the downward node
            if (row + 1 < gp.maxScreenRow)
                openNode(node[col][row + 1]);
            // check the rightward node
            if (col + 1 < gp.maxScreenCol)
                openNode(node[col + 1][row]);
            //find Best node

            for (int i = 0; i < openList.size(); i++) {
                // compare the final cost between two nodes
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                // if the final cost is equal between two nodes. THEN check Greedy cost
                else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            // if there is no node in open list THEN end the loop
            if (openList.size() == 0)
                break;
            // After the loop, openlist[bestNodeIndex] is the next step  <- currentNode
            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode) {
                goalReached = true;
                trackPath();
            }
            step++;
        }
        return goalReached;
    }

    // parent -> current -> next
    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    public void trackPath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);  // always adding to the first slot so the last added node is in pathList[0]

            current = current.parent;  //  last Node <-- parent Node <- current Node
        }
    }


}
