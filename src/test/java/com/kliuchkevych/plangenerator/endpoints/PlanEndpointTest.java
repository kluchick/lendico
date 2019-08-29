package com.kliuchkevych.plangenerator.endpoints;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kliuchkevych.plangenerator.model.api.LoanApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


/**
 * This test is used to test enpoint of the application.
 * It is more integration test than unit test because it starts application context and run real call.
 * Test profile can help to change some dependencies which we don't want to use during testing.
 *
 * Added only one test because of the lack of time.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PlanEndpointTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void generatePlan() throws Exception {

        // GIVEN
        final LoanApi loanApi = new LoanApi();
        loanApi.setDuration("24");
        loanApi.setLoanAmount("5000");
        loanApi.setNominalRate("5.0");
        loanApi.setStartDate("2018-01-01T00:00:01Z");

        // WHEN-THEN
        mvc.perform(MockMvcRequestBuilders
                            .post("/repayment-plan")
                            .content(asJsonString(loanApi))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}