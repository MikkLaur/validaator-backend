package validaator.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
    Collection<Ticket> findByUserId(Long userId);
}
