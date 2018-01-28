package validaator.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Stop {

    @Id
    @GeneratedValue
    private long id;

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
