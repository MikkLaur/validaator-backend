package validaator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Transaction {

    @ManyToOne
    private User user;

    @ManyToOne
    private Stop stop;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "creation_date", updatable = false)
    @CreationTimestamp
    private Date createdOn;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdatedOn;

    public Transaction(User user) {

    }

    Transaction() { // Used by JPA
    }
}
