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
    private Set<Transaction> transactions;

    /* Getter */

    public long getId() { return id; }

    public String getName() { return name; }

    public Set<Transaction> getTransactions() { return transactions; }

    /* Constructor */

    public Stop(String name) {
        this.name = name;
    }
}
