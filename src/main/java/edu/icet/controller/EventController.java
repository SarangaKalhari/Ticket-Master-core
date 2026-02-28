package edu.icet.controller;

import edu.icet.model.dto.event.EventRequestDTO;
import edu.icet.model.dto.event.EventResponseDTO;
import edu.icet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public Long createEvent(@RequestBody EventRequestDTO dto) throws Exception {
        return eventService.addEvent(dto);
    }

    @GetMapping
    public List<EventResponseDTO> getAllEvents() throws Exception {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEvent(@PathVariable Long id) throws Exception {
        return eventService.getEventById(id);
    }

    }
