/**
 * BSTNode represents a node in a Binary Search Tree.
 * Each node contains data from MySQL (id and title).
 */
public class BSTNode {
    private int id;
    private String title;
    private BSTNode left;
    private BSTNode right;

    /**
     * Constructor for BSTNode
     * @param id the primary key from MySQL
     * @param title the title from MySQL
     */
    public BSTNode(int id, String title) {
        this.id = id;
        this.title = title;
        this.left = null;
        this.right = null;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BSTNode getLeft() {
        return left;
    }

    public void setLeft(BSTNode left) {
        this.left = left;
    }

    public BSTNode getRight() {
        return right;
    }

    public void setRight(BSTNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "BSTNode{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
