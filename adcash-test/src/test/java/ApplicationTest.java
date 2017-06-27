import com.adcash.trading.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
 * @since 20/06/2017
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getCountryCode() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/").param("countryCode", "IN").param("category", "IT").param("baseBid", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("C2"));
    }
}
