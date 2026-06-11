import java.util.ArrayList;
import java.util.List;

/**
 * BinarySearchTree implementation for managing MySQL data.
 * Nodes are ordered by ID (primary key).
 */
public class BinarySearchTree {
    private BSTNode root;

    /**
     * Constructor for an empty BST
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Insert a new node into the BST
     * @param id the node's ID
     * @param title the node's title
     */
    public void insert(int id, String title) {
        root = insertRecursive(root, id, title);
    }

    private BSTNode insertRecursive(BSTNode node, int id, String title) {
        if (node == null) {
            return new BSTNode(id, title);
        }

        if (id < node.getId()) {
            node.setLeft(insertRecursive(node.getLeft(), id, title));
        } else if (id > node.getId()) {
            node.setRight(insertRecursive(node.getRight(), id, title));
        }
        // Duplicate IDs are ignored

        return node;
    }

    /**
     * Search for a node by ID
     * @param id the ID to search for
     * @return the BSTNode if found, null otherwise
     */
    public BSTNode search(int id) {
        return searchRecursive(root, id);
    }

    private BSTNode searchRecursive(BSTNode node, int id) {
        if (node == null) {
            return null;
        }

        if (id == node.getId()) {
            return node;
        } else if (id < node.getId()) {
            return searchRecursive(node.getLeft(), id);
        } else {
            return searchRecursive(node.getRight(), id);
        }
    }

    /**
     * Delete a node by ID
     * @param id the ID to delete
     */
    public void delete(int id) {
        root = deleteRecursive(root, id);
    }

    private BSTNode deleteRecursive(BSTNode node, int id) {
        if (node == null) {
            return null;
        }

        if (id < node.getId()) {
            node.setLeft(deleteRecursive(node.getLeft(), id));
        } else if (id > node.getId()) {
            node.setRight(deleteRecursive(node.getRight(), id));
        } else {
            // Node to delete found

            // Case 1: No children (leaf node)
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }

            // Case 2: One child
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }

            // Case 3: Two children
            // Find the in-order successor (smallest in right subtree)
            BSTNode successor = findMin(node.getRight());
            node.setId(successor.getId());
            node.setTitle(successor.getTitle());
            node.setRight(deleteRecursive(node.getRight(), successor.getId()));
        }

        return node;
    }

    /**
     * Find the node with the minimum ID
     * @param node the starting node
     * @return the node with minimum ID
     */
    private BSTNode findMin(BSTNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * In-order traversal (Left-Root-Right) returns sorted list by ID
     * @return List of nodes in sorted order
     */
    public List<BSTNode> inOrderTraversal() {
        List<BSTNode> result = new ArrayList<>();
        inOrderTraversalRecursive(root, result);
        return result;
    }

    private void inOrderTraversalRecursive(BSTNode node, List<BSTNode> result) {
        if (node != null) {
            inOrderTraversalRecursive(node.getLeft(), result);
            result.add(node);
            inOrderTraversalRecursive(node.getRight(), result);
        }
    }

    /**
     * Pre-order traversal (Root-Left-Right)
     * @return List of nodes in pre-order
     */
    public List<BSTNode> preOrderTraversal() {
        List<BSTNode> result = new ArrayList<>();
        preOrderTraversalRecursive(root, result);
        return result;
    }

    private void preOrderTraversalRecursive(BSTNode node, List<BSTNode> result) {
        if (node != null) {
            result.add(node);
            preOrderTraversalRecursive(node.getLeft(), result);
            preOrderTraversalRecursive(node.getRight(), result);
        }
    }

    /**
     * Check if the tree is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Get the root node
     * @return the root node
     */
    public BSTNode getRoot() {
        return root;
    }

    /**
     * Get the height of the tree
     * @return height of the tree
     */
    public int getHeight() {
        return getHeightRecursive(root);
    }

    private int getHeightRecursive(BSTNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getHeightRecursive(node.getLeft()), getHeightRecursive(node.getRight()));
    }
}
