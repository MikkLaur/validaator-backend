package validaator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @JsonIgnore
    @OneToMany
    private Set<Ticket> tickets = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    private String password;
    @NotNull
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(updatable = false, unique = true)
    @NotNull
    private String personalCode;

    @Column(updatable = false)
    @NotNull
    private Date dateOfBirth;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date lastUpdatedOn;


    /* Getter */

    public Set<Ticket> getTickets() { return tickets; }

    public Long getId() { return id; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getEmail() { return email; }

    public String getPersonalCode() { return personalCode; }

    public Date getDateOfBirth() { return dateOfBirth; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public Date getCreatedOn() { return createdOn; }

    public Date getLastUpdatedOn() { return lastUpdatedOn; }

    /* Constructor */

    public User(String username, String password, String email, String personalCode, Date dateOfBirth, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.personalCode = personalCode;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() { // Used by JPA
    }
}
