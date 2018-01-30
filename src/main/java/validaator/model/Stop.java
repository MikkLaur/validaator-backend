package validaator.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Stop {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String name;

    @OneToMany
    private Set<Ticket> tickets;

    /* Getter */

    public long getId() { return id; }

    public String getName() { return name; }

    public Set<Ticket> getTickets() { return tickets; }

    /* Constructor */

    public Stop(String name) {
        this.name = name;
    }
}
