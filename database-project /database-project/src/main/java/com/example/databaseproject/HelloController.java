import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {
    @FXML
    private TextField nameField, emailField;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn, emailColumn;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadUsers();
        userTable.setItems(userList);
    }

    public void addUser() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !email.isEmpty()) {
            try (Connection conn = DatabaseConnection.connect()) {
                String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.executeUpdate();
                userList.add(new User(getLastInsertedId(conn), name, email));
                nameField.clear();
                emailField.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadUsers() {
        userList.clear();
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                userList.add(new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getLastInsertedId(Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT currval(pg_get_serial_sequence('users', 'user_id')) AS id")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }
}
