package validaator.controller;

import org.junit.Before;
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
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TicketRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private List<User> userList;
    private List<Ticket> ticketList;
    private List<Stop> stopList;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.userRepository.deleteAllInBatch();
        this.stopRepository.deleteAllInBatch();
        this.ticketRepository.deleteAllInBatch();


        userList = Arrays.asList(
                userRepository.save(
                        new User("miggest", "password",
                                "miggest@email.com", "39505220000",
                                Date.valueOf("1995-05-22"),"Mikk",
                                "Laur")),
                userRepository.save(
                        new User("miggest2", "password",
                        "miggest2@email.com", "39505220001",
                        Date.valueOf("1995-05-22"),"Mikk",
                        "Laur"))
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

    @Test
    public void readTickets() throws Exception {
        User user = userList.get(0);
        mockMvc.perform(get("/users/" + user.getId() + "/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(user.getTickets().size())));
    }

    @Test
    public void readTicket() throws Exception {
        for (User user : userList) {
            mockMvc.perform(get("/users/" + user.getId() + "/transactions/" + "/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType));
            mockMvc.perform(get("/users/" + user.getId() + "/transactions/" + "/2"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType));
        }
    }

    @Test
    public void createTicket() throws Exception {
        User user = userList.get(0);
        String ticketJson = json(new Ticket(
                user, stopList.get(0)
        ));

        this.mockMvc.perform(post("/users/" + user.getId() + "/transactions")
                .contentType(contentType)
                .content(ticketJson))
                .andExpect(status().isCreated());
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
