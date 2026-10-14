package com.zee.systems.controller;


import com.zee.systems.datatype.TicketPriority;
import com.zee.systems.datatype.TicketStatus;
import com.zee.systems.entity.Ticket;
import com.zee.systems.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public Ticket createTicket(@RequestParam String title,
                               @RequestParam String description,
                               @RequestParam TicketPriority priority,
                               @RequestParam String assignee) {

        return ticketService.createTicket(title, description, priority, assignee);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }


    @GetMapping(path = "{id}")
    public Ticket getTicket(@PathVariable Long id) {
        return ticketService.getTicket(id);
    }


    @PutMapping(path ="{id}/status")
    public Ticket updateStatus(@PathVariable Long id,
                               @RequestParam TicketStatus status) {
        return ticketService.updateStatus(id, status);
    }


    @PutMapping(path ="{id}/assign")
    public Ticket assignTicket(@PathVariable Long id,
                               @RequestParam String assignee) {
        return ticketService.assignTicket(id, assignee);
    }
}