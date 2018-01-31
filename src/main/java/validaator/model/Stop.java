package validaator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Stop {

    @JsonIgnore
    @OneToMany
    private Set<Ticket> tickets;

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date lastUpdatedOn;

    /* Getter */

    public Long getId() { return id; }

    public String getName() { return name; }

    public Set<Ticket> getTickets() { return tickets; }

    /* Constructor */

    public Stop(String name) {
        this.name = name;
    }

    public Stop() { // Used by JPA
    }
}
