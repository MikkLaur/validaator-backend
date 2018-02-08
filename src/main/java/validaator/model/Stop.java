package validaator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
public class Stop {

    @JsonIgnore
    @OneToMany
    private Set<Ticket> tickets = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date lastUpdatedOn;

    /* Constructor */

    public Stop(String name) {
        this.name = name;
    }

    public Stop() { // Used by JPA
    }
}
