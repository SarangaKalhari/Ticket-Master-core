package edu.icet.repository;

import edu.icet.db.DBConnection;
import edu.icet.model.entity.Seat;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;

@Repository
public class SeatRepository {

    public void bookSeat(Seat seat) {

        String sql = """
        UPDATE seats
        SET status = ?,
            held_by_user_id = ?,
            hold_expiry = ?
        WHERE id = ?
    """;

        try  {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, seat.getStatus());
            statement.setLong(2, seat.getHeldByUserId());
            statement.setTimestamp(
                    3,
                    Timestamp.valueOf(LocalDateTime.now().plusMinutes(10))
            );
            statement.setLong(4, seat.getId());

            int updated = statement.executeUpdate();

            if (updated == 0) {
                throw new RuntimeException("Seat not found or already booked");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Seat searchSeat(String seatNumber) {

        String sql = """
        SELECT id, event_id, seat_number, status, held_by_user_id, hold_expiry
        FROM seats
        WHERE seat_number = ?
    """;

        try {

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, seatNumber);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Seat seat = new Seat();
                    seat.setId(rs.getLong("id"));
                    seat.setEventId(rs.getLong("event_id"));
                    seat.setSeatNumber(rs.getString("seat_number"));
                    seat.setStatus(rs.getString("status"));
                    seat.setHeldByUserId(rs.getObject("held_by_user_id", Long.class));
                    seat.setHoldExpiry(
                            rs.getTimestamp("hold_expiry") != null
                                    ? rs.getTimestamp("hold_expiry").toLocalDateTime()
                                    : null
                    );
                    return seat;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null; // seat not found
    }

    public void expireUnpaidHolds() {
        String sql = """
        UPDATE seats
        SET status = 'AVAILABLE',
            held_by_user_id = NULL,
            hold_expiry = NULL
        WHERE status = 'HELD'
        AND hold_expiry < NOW()
    """;

        try  {

            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
