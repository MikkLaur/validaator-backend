package validaator.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import validaator.Application;
import validaator.model.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class UserRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private List<User> userList;
    private List<Ticket> ticketList;
    private List<Stop> stopList;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(httpmc -> httpmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

        this.ticketRepository.deleteAllInBatch();
        this.userRepository.deleteAllInBatch();
        this.stopRepository.deleteAllInBatch();


        userList = Arrays.asList(
                userRepository.save(
                        new User("testuser1", "password",
                                "testuser1@email.com", "39505220000",
                                Date.valueOf("1990-01-22"),"tester",
                                "man")),
                userRepository.save(
                        new User("testuser2", "password",
                                "testuser2@email.com", "39505220001",
                                Date.valueOf("1992-04-21"),"tester",
                                "man"))
        );

        stopList = Arrays.asList(
                stopRepository.save(new Stop("Vikerkaare")),
                stopRepository.save(new Stop("Rõngu"))
        );

        ticketList = Arrays.asList(
                ticketRepository.save(new Ticket(userList.get(0), stopList.get(0))),
                ticketRepository.save(new Ticket(userList.get(0), stopList.get(1))),

                ticketRepository.save(new Ticket(userList.get(1), stopList.get(0))),
                ticketRepository.save(new Ticket(userList.get(1), stopList.get(1)))
        );
    }

    @Test //TODO Not what is is supposed to be
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/users/" + userList.size()+1)
                .content(json(new User()))
                .contentType(contentType))
                .andExpect(status().isMethodNotAllowed());  // Throws error 405 at the moment
    }

    @Test
    public void readUser() throws Exception {
        for (User user : userList) {
            mockMvc.perform(get("/users/" + user.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                    .andExpect(jsonPath("$.username", is(user.getUsername())))
                    .andExpect(jsonPath("$.email", is(user.getEmail())))
                    .andExpect(jsonPath("$.personalCode", is(user.getPersonalCode())))
                    .andExpect(jsonPath("$.dateOfBirth", is(user.getDateOfBirth().toString())))
                    .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                    .andExpect(jsonPath("$.lastName", is(user.getLastName())));
        }
    }

    @Test
    public void readUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(this.userList.size())))
                .andExpect(jsonPath("$[0].id", is(this.userList.get(0).getId().intValue())));
    }

    @Test
    public void createUser() throws Exception {
        String userJson = json(new User(
                "addme!",
                "password",
                "addme@email.com",
                "231591351",
                Date.valueOf("1950-12-11"),
                "ultimate",
                "testerman"));
        mockMvc.perform(post("/users")
                .content(userJson)
                .contentType(contentType))
                .andExpect(status().isCreated());
    }

    @Test
    public void createUserDuplicate() throws Exception {
        String userJson = json(new User(
                "addme!",
                "password",
                "addme@email.com",
                "231591351",
                Date.valueOf("1950-12-11"),
                "ultimate",
                "testerman"));
        mockMvc.perform(post("/users")
                .content(userJson)
                .contentType(contentType))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/users")
                .content(userJson)
                .contentType(contentType))
                .andExpect(status().is4xxClientError());
    }

    @Test @Ignore
    public void createUserInvalid() throws Exception {

    }

    @Test @Ignore
    public void updateUser() throws Exception {

    }

    @Test @Ignore
    public void deleteUser() throws Exception {

    }

    /* TICKETS */

    @Test
    public void readTicket() throws Exception {
        for(User user : userRepository.findAll()) {
            for (Ticket ticket : ticketRepository.findByUserId(user.getId())) {
                mockMvc.perform(get("/users/" + user.getId() + "/tickets/" + ticket.getId()))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(contentType))
                        .andExpect(jsonPath("$.id", is(ticket.getId().intValue())));
            }
        }
    }

    @Test
    public void readTicketNotBelongingToUser() throws Exception {
        User user1 = userList.get(0);
        assertFalse(ticketRepository
                .findByUserId(user1.getId())
                .isEmpty());
        Ticket ticket1 = ticketRepository.findByUserId(user1.getId()).iterator().next();

        User user2 = userList.get(1);
        assertFalse(ticketRepository
                .findByUserId(user2.getId())
                .isEmpty());
        Ticket ticket2 = ticketRepository.findByUserId(user2.getId()).iterator().next();

        mockMvc.perform(get("/users/" + user1.getId() + "/tickets/" + ticket2.getId() ))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(get("/users/" + user2.getId() + "/tickets/" + ticket1.getId() ))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void readTickets() throws Exception {
        User user = userList.get(0);
        mockMvc.perform(get("/users/" + user.getId() + "/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(ticketRepository.findByUserId(user.getId()).size())));
    }

    @Test
    public void createTicket() throws Exception {
        User user = userList.get(0);
        String ticketJson = json(new Ticket(
                user, stopList.get(0)
        ));
        int totalTickets = ticketRepository.findByUserId(user.getId()).size();
        /* Post ticket */
        this.mockMvc.perform(post("/users/" + user.getId() + "/tickets")
                .contentType(contentType)
                .content(ticketJson))
                .andExpect(status().isCreated());

        /* Check whether the user has one more ticket now */
        this.mockMvc.perform(get("/users/" + user.getId() + "/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(totalTickets + 1)));
    }

    @Test
    public void createTicketWrongUserIdInPathVariable() throws Exception {
        User user = userList.get(0);
        String ticketJson = json(new Ticket(
                userList.get(1), stopList.get(0)
        ));

        this.mockMvc.perform(post("/users/" + user.getId() + "tickets")
                .contentType(contentType)
                .content(ticketJson))
                .andExpect(status().is4xxClientError()); // Server responds 405. Should respond 403/401.
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
