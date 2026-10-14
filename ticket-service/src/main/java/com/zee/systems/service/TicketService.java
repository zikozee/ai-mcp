package com.zee.systems.service;


import com.zee.systems.datatype.TicketPriority;
import com.zee.systems.datatype.TicketStatus;
import com.zee.systems.entity.Ticket;
import com.zee.systems.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public Ticket createTicket(String title,
                               String description,
                               TicketPriority priority,
                               String assignee) {

        Ticket ticket = Ticket.builder()
                .title(title)
                .description(description)
                .status(TicketStatus.OPEN)
                .priority(priority)
                .assignee(assignee)
                .createdAt(LocalDateTime.now(ZoneId.systemDefault()))
                .updatedAt(LocalDateTime.now(ZoneId.systemDefault()))
                .build();

        return ticketRepository.save(ticket);
    }


    public Ticket getTicket(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    @Transactional(readOnly = true)
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }


    @Transactional
    public Ticket updateStatus(Long id, TicketStatus status) {
        Ticket ticket = getTicket(id);
        ticket.setStatus(status);
        ticket.setUpdatedAt(LocalDateTime.now(ZoneId.systemDefault()));
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket assignTicket(Long id, String assignee) {
        Ticket ticket = getTicket(id);
        ticket.setAssignee(assignee);
        ticket.setUpdatedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }
}