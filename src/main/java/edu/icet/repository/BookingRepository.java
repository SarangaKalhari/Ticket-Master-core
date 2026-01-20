package edu.icet.repository;

import edu.icet.db.DBConnection;
import edu.icet.model.dto.booking.BookingRequestDTO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class BookingRepository {

    public String searchOnHold(BookingRequestDTO requestDTO) {
        String status = null;

        String sql = "SELECT status FROM seats WHERE id = ? AND held_by_user_id = ?";

        try {

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setLong(1, requestDTO.getSeatId());
            stmt.setLong(2, requestDTO.getUserId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status"); // AVAILABLE / HELD / SOLD
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return status; // null if not held
    }

}
