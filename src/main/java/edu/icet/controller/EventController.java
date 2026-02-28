package edu.icet.controller;

import edu.icet.model.dto.event.EventRequestDTO;
import edu.icet.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public Long createEvent(@RequestBody EventRequestDTO dto) throws Exception {
        return eventService.addEvent(dto);
    }
}
