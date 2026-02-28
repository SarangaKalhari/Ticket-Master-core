package edu.icet.repository;

import edu.icet.db.DBConnection;
import edu.icet.model.dto.event.EventRequestDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class EventRepository {

    public Long save(EventRequestDTO dto) throws Exception {

        String sql = "INSERT INTO events (name, base_price, is_high_demand, event_date) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, dto.getName());
            ps.setBigDecimal(2, dto.getBasePrice());
            ps.setBoolean(3, dto.isHighDemand());
            ps.setTimestamp(4, Timestamp.valueOf(dto.getEventDate()));

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return null;
    }

}
