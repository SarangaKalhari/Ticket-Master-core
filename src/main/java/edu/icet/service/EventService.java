package edu.icet.service;

import edu.icet.model.dto.event.EventRequestDTO;
import edu.icet.model.dto.event.EventResponseDTO;
import edu.icet.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository ;

    public Long addEvent(EventRequestDTO dto) throws Exception {

        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new RuntimeException("Event name cannot be empty");
        }

        if (dto.getBasePrice() == null || dto.getBasePrice().doubleValue() <= 0) {
            throw new RuntimeException("Invalid base price");
        }

        if (dto.getEventDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Event date must be in the future");
        }

        return eventRepository.save(dto);
    }

    public List<EventResponseDTO> getAllEvents() throws Exception {
        return eventRepository.findAll();
    }

    public EventResponseDTO getEventById(Long id) throws Exception {

        EventResponseDTO event = eventRepository.findById(id);

        if (event == null) {
            throw new RuntimeException("Event not found");
        }

        return event;
    }

    public boolean updateEvent(Long id, EventRequestDTO dto) throws Exception {

        if (eventRepository.findById(id) == null) {
            throw new RuntimeException("Event not found");
        }

        return eventRepository.update(id, dto);
    }
}
