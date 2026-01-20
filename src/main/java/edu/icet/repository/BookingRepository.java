package edu.icet.repository;

import edu.icet.db.DBConnection;
import edu.icet.model.dto.booking.BookingRequestDTO;
import edu.icet.model.entity.Seat;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    public boolean markHeldSeatAsSold(long seatId, long userId) {

        String sql = """
        UPDATE seats
        SET status = 'SOLD',
            held_by_user_id = ?,
            hold_expiry = NULL
        WHERE id = ?
          AND held_by_user_id = ?
          AND status = 'HELD'
          AND hold_expiry > NOW()
    """;

        try {

            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setLong(1,userId);
            stmt.setLong(2, seatId);
            stmt.setLong(3, userId);

            int updatedRows = stmt.executeUpdate();
            return updatedRows > 0; // true if SOLD

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addBooking(Long seatId, Long userId, double amount) {

        String sql = """
        INSERT INTO bookings (user_id, seat_id, amount_paid, status, booked_at)
        VALUES (?, ?, ?, 'CONFIRMED', NOW())
    """;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setLong(1, userId);
            stmt.setLong(2, seatId);
            stmt.setBigDecimal(3, BigDecimal.valueOf(amount));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
