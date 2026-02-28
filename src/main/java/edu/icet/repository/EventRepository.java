package edu.icet.repository;

import edu.icet.db.DBConnection;
import edu.icet.model.dto.event.EventRequestDTO;
import edu.icet.model.dto.event.EventResponseDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<EventResponseDTO> findAll() throws Exception {

        String sql = "SELECT * FROM events";

        List<EventResponseDTO> eventList = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                eventList.add(mapToDTO(rs));
            }
        }
        return eventList;
    }

    private EventResponseDTO mapToDTO(ResultSet rs) throws Exception {

        return new EventResponseDTO(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getBigDecimal("base_price"),
                rs.getBoolean("is_high_demand"),
                rs.getTimestamp("event_date").toLocalDateTime()
        );
    }

    public EventResponseDTO findById(Long id) throws Exception {

        String sql = "SELECT * FROM events WHERE id = ?";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToDTO(rs);
            }
        }
        return null;
    }
}
