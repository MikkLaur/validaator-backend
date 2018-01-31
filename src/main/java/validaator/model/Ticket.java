package validaator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Ticket {

    @ManyToOne
    private User user;

    @ManyToOne
    private Stop stop;

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date lastUpdatedOn;

    /* Getter */

    public User getUser() { return user; }

    public Stop getStop() { return stop; }

    public Long getId() { return id; }

    public Date getCreatedOn() { return createdOn; }

    public Date getLastUpdatedOn() { return lastUpdatedOn; }

    /* Constructor */

    public Ticket(User user, Stop stop) {

    }

    Ticket() { // Used by JPA
    }
}
