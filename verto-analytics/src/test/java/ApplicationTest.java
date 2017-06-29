
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verto.analytics.Application;
import com.verto.analytics.dto.StringSetRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author abhishekrai
 * @since 26/06/2017
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void uploadTestStringSet() throws Exception {

        StringSetRequest stringSetRequest = new StringSetRequest();
        stringSetRequest.setStringSet("ABH HTP PTA LTH");

        String json;

            ObjectMapper mapper = new ObjectMapper();
            json = mapper.writeValueAsString(stringSetRequest);

        mvc.perform(MockMvcRequestBuilders.post("/upload").content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(0))
                .andExpect(jsonPath("$.message").value("Successfully uploaded string set to server"));

    }

    @Test
    public void createIntersection() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/create_intersection")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(-1))
                .andExpect(jsonPath("$.message").value("No String set in storage"));
    }

    @Test
    public void getStringExactlyIn() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/exactly_in").param("specificNumberOfSet", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(-1))
                .andExpect(jsonPath("$.message").value("No record found with specific number"));
    }

    @Test
    public void getLongestString() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/longest")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(-1))
                .andExpect(jsonPath("$.message").value("No record Found"));
    }
}
