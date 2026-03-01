package edu.icet.repository;

import edu.icet.db.DBConnection;
import edu.icet.model.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
public class UserRepository {

    public void createUser(User user) {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (id,name,email,tier) VALUES (?,?,?,?)");

            statement.setObject(1,user.getId());
            statement.setObject(2,user.getName());
            statement.setObject(3,user.getEmail());
            statement.setObject(4,user.getTier());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public long getLastID() {
        long lastID = 0;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) AS last_id FROM users");

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                lastID = rs.getLong(1);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lastID;
    }
}
