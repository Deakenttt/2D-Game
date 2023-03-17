package ai;
/**
* The ai package contains classes related to AI pathfinding algorithms for game entities.
*/

import entity.Entity;
import main.GamePanel;

import java.util.ArrayList;

/**
*    The FindPath class is responsible for calculating a path between two nodes using the A* pathfinding algorithm.
*    It stores all the nodes calculated based on the current start position and goal position and contains a list of open and closed nodes.
*    A list of nodes representing the path from start to goal is also stored in this class.
*/
public class FindPath {
    GamePanel gp;
    Node[][] node;  // Stores all of the node calculations based on the current start pos and goal pos
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached;
    int step = 0;

    /**
    * Constructs a new FindPath object with the specified GamePanel object.
    * @param gp the GamePanel object
    */
    public FindPath(GamePanel gp) {
        this.gp = gp;
        instantiateNode();
    }

    /**
    * Defines all the nodes for the current GamePanel object.
    */
    public void instantiateNode() {  // define all the nodes
        node = new Node[gp.maxScreenCol][gp.maxScreenRow];
        for (int i = 0; i < gp.maxScreenCol; i++) {
            for (int j = 0; j < gp.maxScreenRow; j++) {
                node[i][j] = new Node(i, j);
            }
        }
    }

    /**
    * Resets all the node after start node and goal are modified.
    */    
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
    /**
    * Sets the start and goal node for the pathfinding algorithm.
    * @param startCol the column index of the start node
    * @param startRow the row index of the start node
    * @param goalCol the column index of the goal node
    * @param goalRow the row index of the goal node
    * @param entity the entity that is moving
    */
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
    /**
    * Calculates the g cost, h cost, and final cost for a given node.
    * @param node the node for which to calculate the costs.
    */
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

    /**
    * Performs an A* search algorithm to find the shortest path between the start node and goal node
    * @return true if the goal node is reached, false otherwise
    */
    public boolean aStarSearch() {
        System.out.println("A START SEARCH STARTING");

        int col, row, bestNodeIndex, bestNodefCost;
        while (!goalReached && step < 500) {
            col = currentNode.col;
            row = currentNode.row;

            bestNodeIndex = 0;
            bestNodefCost = 999;
            
            // Mark current node and remove from open list
            currentNode.checked = true;
            openList.remove(currentNode);

            // Check the upward node
            if (row - 1 >= 0)
                openNode(node[col][row - 1]);

            // Check the leftward node
            if (col - 1 >= 0)
                openNode(node[col - 1][row]);

            // Check the downward node
            if (row + 1 < gp.maxScreenRow)
                openNode(node[col][row + 1]);

            // Check the rightward node
            if (col + 1 < gp.maxScreenCol)
                openNode(node[col + 1][row]);

            // Find best node based on f cost
            for (int i = 0; i < openList.size(); i++) {
                // Compare final cost between two nodes
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                // If the final cost is equal between two nodes, then check greedy cost                
                else if (openList.get(i).fCost == bestNodefCost && openList.get(i).gCost < openList.get(bestNodeIndex).gCost)
                    bestNodeIndex = i;
            }
            // If there are no nodes in the open list, end the loop
            if (openList.size() == 0)
                break;
            
            // If the current node is the goal node, set goalReached to true and track the path            
            currentNode = openList.get(bestNodeIndex);
            if (currentNode == goalNode) {
                goalReached = true; 
                System.out.println("col: %d row: %d".formatted(col, row));

                trackPath();
            }
            step++;
        }
        // Return true if the goal node is reached, false otherwise
        return goalReached;
    }

    /**
    * Opens the given node for search by setting its open status to true, setting its parent node to the current node,
    * and adding it to the open list. The node is only opened if it has not been previously opened, checked, or marked as solid.
    @param node the node to open
    */  
    public void openNode(Node node) {
        if (!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    /**
    * Tracks the path from the goal node back to the start node by following the parent links from each node in the path.
    * The path is stored in the pathList field, which contains the nodes in reverse order starting with the goal node and
    * ending with the start node.
    */
    public void trackPath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0, current);  // Always adding to the first slot so the last added node is in pathList[0]
            current = current.parent;  //  last Node <-- parent Node <- current Node
        }
    }


}
