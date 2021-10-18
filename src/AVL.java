import java.util.ArrayList;
import java.util.List;

/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 2
 * <p>
 * TODO: Complete implementation of AVL.Java
 *
 * @author Shivam Bairoloya, Andrew Thomae TODO: add your name here
 * @username sbairoli, akthomae TODO: add your username here
 * @sources Brightspace, Campuswire, Princeton algs4cs TODO: add your sources here
 */
public class AVL {

    /**
     * Insert a tuple in the tree
     *
     * @param node The root of the tree
     * @param tuple The data to be inserted
     * @return root of the tree
     */
    public Node insert(Node node, Tuple tuple) {
        //If the tree is empty
        if (node == null) {
            node = new Node(tuple, null, null, 0);
            return node;
        }

        //Traverse the tree
        if (tuple.compareTo(node.data) < 0) {
            node.left = insert(node.left, tuple);
        } else if(tuple.compareTo(node.data) > 0) {
            node.right = insert(node.right, tuple);
        } else {
            return node;
        }

        //Set the height
        if (height(node.left) > height(node.right)) {
            node.height = 1 + height(node.left);
        } else {
            node.height = 1 + height(node.right);
        }

        //Balance and return
        node = balance(node);
        return node;
    }

    private Node balance(Node node) {
        //System.out.println("Balance factor: " + balanceFactor(node));
        //If the tree is unbalanced to the right
        if (balanceFactor(node) < -1) {
            if (balanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        }
        //If the tree is unbalanced to the left
        else if(balanceFactor(node) > 1) {
            if (balanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        }

        return node;
    }


    //Rotation helper methods
    private Node rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        //Fix the height of the nodes
        if (height(node.right) > height(node.left)) {
            node.height = 1 + height(node.right);
        } else {
            node.height = 1 + height(node.left);
        }
        if (height(right.right) > height(right.left)) {
            right.height = 1 + height(right.right);
        } else {
            right.height = 1 + height(right.left);
        }
        return right;
    }
    private Node rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        //Fix the height of the nodes
        if (height(node.left) > height(node.right)) {
            node.height = 1 + height(node.left);
        } else {
            node.height = 1 + height(node.right);
        }
        if (height(left.left) > height(left.right)) {
            left.height = 1 + height(left.left);
        } else {
            left.height = 1 + height(left.right);
        }
        return left;
    }

    /**
     * Returns the height of a particular node
     * Returns -1 if the node is null
     *
     * @param node the node
     * @return the height
     */
    public int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.height;
    }

    /**
     * Returns the balance factor of a particular node
     *
     * @param node
     * @return the balance factor
     */
    public int balanceFactor(Node node) {
        //assume node is not null
        return (height(node.left) - height(node.right));
    }

    /**
     * Returns the preorder traversal
     *
     * @param node
     * @return
     */
    public List<Node> preorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node);
        if (node.left != null) {
            nodes.addAll(preorder(node.left));
        }
        if (node.right != null) {
            nodes.addAll(preorder(node.right));
        }
        return nodes;
    }


    /**
     * Returns the inorder traversal
     *
     * @param node
     * @return
     */
    public List<Node> inorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        if (node.left != null) {
            nodes.addAll(inorder(node.left));
        }
        nodes.add(node);
        if (node.right != null) {
            nodes.addAll(inorder(node.right));
        }
        return nodes;
    }

    /**
     * Returns the level order traversal
     *
     * @param node
     * @return
     */
    public String levelOrder(Node node) {
        int height = height(node);
        List<List<Node>> levels = new ArrayList<>();
        for (int i = 1; i <= height + 1; i++) {
            levels.add(levelOrderHelper(node, i));
        }
        StringBuilder out = new StringBuilder();
        int i = 0;
        for (List<Node> level : levels) {
            out.append("Level ").append(i).append(": ");
            for (Node x : level) {
                out.append("|").append(x).append("|");
            }
            out.append("\n");
            i++;
        }
        return out.toString();
    }

    /**
     * Recursive function to aid level order traversal
     *
     * @param curr
     * @param level
     * @return
     */
    private List<Node> levelOrderHelper(Node curr, int level) {
        List<Node> currLevel = new ArrayList<>();
        if (curr == null) {
            currLevel.add(null);
            return currLevel;
        }
        if (level == 1) {
            currLevel.add(curr);
            return currLevel;
        }

        currLevel.addAll(levelOrderHelper(curr.left, level - 1));
        currLevel.addAll(levelOrderHelper(curr.right, level - 1));
        return currLevel;
    }

    /**
     * For manual testing
     * @param args
     */
    public static void main(String[] args) {

    }
}