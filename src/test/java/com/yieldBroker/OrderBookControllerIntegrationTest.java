package com.yieldBroker;

import com.yieldBroker.controller.OrderBookController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderBookControllerIntegrationTest {

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    OrderBookController orderBookController;

    @Before
    public void setup() throws Exception {

        // Standalone context
        this.mockMvc = standaloneSetup(this.orderBookController).build();
    }

    @Test
    public void test_Place_Order_Successfully() throws Exception {

        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("{\"clientOrderId\":1233,\"side\":\"Sell\",\"price\":12.21,\"volume\":2000}")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"clientOrderId\":1233,\"side\":\"Sell\",\"price\":12.21,\"volume\":2000,\"operationSuccessful\":true}"));

    }

    @Test
    public void test_Place_Order_With_Bad_Request() throws Exception {

        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("{\"clientOrderId\":,\"side\":\"Sell\",\"price\":12.21,\"volume\":2000}")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("{\"\"}")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void test_Cancel_Order_Successfully() throws Exception {

        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("{\"clientOrderId\":1233,\"side\":\"Sell\",\"price\":12.21,\"volume\":2000}")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"clientOrderId\":1233,\"side\":\"Sell\",\"price\":12.21,\"volume\":2000,\"operationSuccessful\":true}"));


        mockMvc.perform(post("/market/cancelOrder/1233")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_Get_OrderBook_Successfully_And_Verify_Content() throws Exception {

        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("{\"clientOrderId\":1233,\"side\":\"Buy\",\"price\":12.21,\"volume\":2000}")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"clientOrderId\":1233,\"side\":\"Buy\",\"price\":12.21,\"volume\":2000,\"operationSuccessful\":true}"));


        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("{\"clientOrderId\":2233,\"side\":\"Buy\",\"price\":13.21,\"volume\":2000}")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"clientOrderId\":2233,\"side\":\"Buy\",\"price\":13.21,\"volume\":2000,\"operationSuccessful\":true}"));


        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("{\"clientOrderId\":3233,\"side\":\"Buy\",\"price\":99.21,\"volume\":2000}")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"clientOrderId\":3233,\"side\":\"Buy\",\"price\":99.21,\"volume\":2000,\"operationSuccessful\":true}"));


        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("{\"clientOrderId\":4233,\"side\":\"Sell\",\"price\":32.21,\"volume\":2000}")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"clientOrderId\":4233,\"side\":\"Sell\",\"price\":32.21,\"volume\":2000,\"operationSuccessful\":true}"));

        mockMvc.perform(post("/market/placeOrder")
                .contentType(APPLICATION_JSON)
                .content("{\"clientOrderId\":5233,\"side\":\"Sell\",\"price\":12.21,\"volume\":100}")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"clientOrderId\":5233,\"side\":\"Sell\",\"price\":12.21,\"volume\":100,\"operationSuccessful\":true}"));


        mockMvc.perform(get("/market/orderBook").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().json("{\"buyOrders\":[{\"price\":99.21,\"volume\":2000},{\"price\":13.21,\"volume\":2000},{\"price\":12.21,\"volume\":2000}],\"sellOrders\":[{\"price\":12.21,\"volume\":100},{\"price\":32.21,\"volume\":2000}]}\n"));
    }
}