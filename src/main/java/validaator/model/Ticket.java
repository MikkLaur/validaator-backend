package validaator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;


@Data
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

    /* Constructor */

    public Ticket(User user, Stop stop) {
        this.user = user;
        this.stop = stop;
    }

    Ticket() { // Used by JPA
    }
}
