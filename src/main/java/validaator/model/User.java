package validaator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @OneToMany(mappedBy = "user")
    private Set<Transaction> transactions = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    private String password;
    private String username;

    @Column(updatable = false, unique = true)
    private String personalCode;

    @Column(updatable = false)
    private Date dateOfBirth;

    @Column(unique = true)
    private String email;

    private String firstName;
    private String lastName;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdOn;


    @UpdateTimestamp
    private Date lastUpdatedOn;


    /* Getter */

    public Set<Transaction> getTransactions() { return transactions; }

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getPersonalCode() { return personalCode; }

    public String getEmail() { return email; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public Date getCreatedOn() { return createdOn; }

    public Date getLastUpdatedOn() { return lastUpdatedOn; }

    /* Constructor */

    public User(String username, String password, String personalCode, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.personalCode = personalCode;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    User() { // Used by JPA
    }
}
