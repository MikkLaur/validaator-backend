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
import validaator.model.Stop;
import validaator.model.StopRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class StopRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private List<Stop> stopList;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StopRepository stopRepository;

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

        this.stopRepository.deleteAllInBatch();

        stopList = Arrays.asList(
                stopRepository.save(new Stop("Vikerkaare")),
                stopRepository.save(new Stop("Rõngu"))
        );
    }

    @Test
    public void readStops() throws Exception {
        Stop stop = stopList.get(0);
        mockMvc.perform(get("/stops"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(stopList.size())))
                .andExpect(jsonPath("$[0].id", is(stop.getId().intValue())));
    }

    @Test
    public void readStop() throws Exception {
        for (Stop stop: stopList) {
            mockMvc.perform(get("/stops/" + stop.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.id", is(stop.getId().intValue())))
                    .andExpect(jsonPath("$.name", is(stop.getName())));
        }
    }

    @Test
    public void readStopNotFound() throws Exception {
        int notFoundId = stopRepository.findAll().size() + 99;
        mockMvc.perform(get("/stops/" + notFoundId ))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createStop() throws Exception {
        String stopJson = json(new Stop("Bussipeatus"));
        mockMvc.perform(post("/stops")
                .contentType(contentType)
                .content(stopJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void createStopDuplicate() throws Exception {
        String stopJson = json(new Stop("Bussipeatus"));
        mockMvc.perform(post("/stops")
                .contentType(contentType)
                .content(stopJson))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/stops")
                .contentType(contentType)
                .content(stopJson))
                .andExpect(status().isConflict());
    }

    @Test
    public void updateStop() throws Exception {
        for (Stop stop : stopRepository.findAll()) {
            stop.setName("New " + stop.getName());
            String stopJson = json(stop);

            mockMvc.perform(put("/stops/" + stop.getId() + "/edit")
                    .contentType(contentType)
                    .content(stopJson))
                    .andExpect(status().isOk());

            assertEquals(stop.getName(), stopRepository.findOne(stop.getId()).getName());
        }
    }

    @Test
    public void updateStopPathIdNotEqualToJSONs() throws Exception {
        int falseStopId = stopRepository.findAll().size() + 1;

        for (Stop stop : stopRepository.findAll()) {
            stop.setName("New " + stop.getName());
            String stopJson = json(stop);

            mockMvc.perform(put("/stops/" + falseStopId + "/edit")
                    .contentType(contentType)
                    .content(stopJson))
                    .andExpect(status().isOk());

            assertNotEquals(stop.getName(), stopRepository.findOne(stop.getId()).getName());
        }
    }


    @Test
    public void deleteStop() throws Exception {
        Stop stop = stopRepository.findAll().iterator().next();

        mockMvc.perform(delete("/stops/" + stop.getId() + "/delete"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/stops/" + stop.getId()))
                .andExpect(status().isNotFound());
    }



    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
