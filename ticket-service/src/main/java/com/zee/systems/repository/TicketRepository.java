package com.zee.systems.repository;

import com.zee.systems.entity.Ticket;
import org.springframework.data.repository.ListCrudRepository;

public interface TicketRepository extends ListCrudRepository<Ticket, Long> {
}
