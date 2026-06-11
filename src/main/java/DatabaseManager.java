import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * DatabaseManager handles MySQL connectivity and data loading into BST.
 * Loads records from MySQL table and populates a BinarySearchTree.
 */
public class DatabaseManager {
    private String url;
    private String user;
    private String password;
    private Connection connection;

    /**
     * Constructor for DatabaseManager
     * @param url JDBC connection URL
     * @param user MySQL username
     * @param password MySQL password
     */
    public DatabaseManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Establish connection to MySQL database
     * @return true if connection successful
     */
    public boolean connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL database successfully.");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Failed to connect to MySQL: " + e.getMessage());
            return false;
        }
    }

    /**
     * Close database connection
     */
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from MySQL database.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Load data from MySQL table into a BinarySearchTree
     * @param tableName the name of the table to load from
     * @return BinarySearchTree populated with data
     * @throws SQLException if database query fails
     */
    public BinarySearchTree loadDataIntoBST(String tableName) throws SQLException {
        BinarySearchTree bst = new BinarySearchTree();

        if (connection == null || connection.isClosed()) {
            throw new SQLException("Database connection is not established.");
        }

        String query = "SELECT id, title FROM " + tableName;
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                bst.insert(id, title);
                System.out.println("Inserted: ID=" + id + ", Title='" + title + "'");
            }

            System.out.println("Successfully loaded " + getRowCount(tableName) + " records into BST.");
        } catch (SQLException e) {
            System.err.println("Error loading data into BST: " + e.getMessage());
            throw e;
        }

        return bst;
    }

    /**
     * Get the total number of rows in a table
     * @param tableName the table name
     * @return row count
     * @throws SQLException if query fails
     */
    private int getRowCount(String tableName) throws SQLException {
        String query = "SELECT COUNT(*) as count FROM " + tableName;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        }
        return 0;
    }

    /**
     * Insert a new record into MySQL table and BST
     * @param tableName the table name
     * @param id the record ID
     * @param title the record title
     * @return true if insertion successful
     */
    public boolean insertRecord(String tableName, int id, String title) {
        String query = "INSERT INTO " + tableName + " (id, title) VALUES (" + id + ", '" + title + "')";
        
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Record inserted into MySQL: ID=" + id + ", Title='" + title + "'");
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting record: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a record from MySQL by ID
     * @param tableName the table name
     * @param id the record ID to delete
     * @return true if deletion successful
     */
    public boolean deleteRecord(String tableName, int id) {
        String query = "DELETE FROM " + tableName + " WHERE id = " + id;
        
        try (Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                System.out.println("Record deleted from MySQL: ID=" + id);
                return true;
            } else {
                System.out.println("No record found with ID=" + id);
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting record: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if connection is active
     * @return true if connected
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
