package com.cascade.journal;

import com.cascade.journal.api.dto.Customer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JournalApplication.class)
@WebAppConfiguration
public class CustomerControllerTests {

    private static Logger log = LoggerFactory.getLogger(CustomerControllerTests.class);

    private MockMvc mockMvc;
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private Gson gson = new Gson();


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void customerNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/666"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.post("/customer/666/update")
                .content(gson.toJson(new Customer()))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        mockMvc.perform(MockMvcRequestBuilders.post("/customer/666/remove")
                .content(gson.toJson(new Customer()))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void fullFlowTest() throws Exception {
        // Add
        ArrayList<Customer> customers1 = new ArrayList<>();
        for (int i = 0; i < 16; ++i) {
            Customer toCreate = createCustomer(i);

            String addResult = mockMvc
                    .perform(MockMvcRequestBuilders.post("/customer/add")
                            .content(gson.toJson(toCreate))
                            .contentType(contentType))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Customer created = gson.fromJson(addResult, Customer.class);
            Assert.assertNotEquals(toCreate.id, created.id);
            Assert.assertEquals(toCreate.firstName, created.firstName);

            customers1.add(created);
        }


        // List default
        String listAllString = mockMvc
                .perform(MockMvcRequestBuilders.get("/customer/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Customer> listAll = gson.fromJson(listAllString,
                new TypeToken<ArrayList<Customer>>(){}.getType());
        Assert.assertEquals(10, listAll.size());
        Assert.assertEquals(customers1.get(5).id, listAll.get(5).id);


        // Update
        Customer toUpdate = listAll.get(5);
        toUpdate.firstName = "Anton";
        toUpdate.lastName = "Romankov";
        String updateString = mockMvc.perform(
                MockMvcRequestBuilders.post("/customer/" + toUpdate.id + "/update")
                .content(gson.toJson(toUpdate))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Customer updated = gson.fromJson(updateString, Customer.class);
        Assert.assertEquals(toUpdate.id, updated.id);
        Assert.assertEquals(toUpdate.firstName, updated.firstName);
        Assert.assertEquals(toUpdate.lastName, updated.lastName);


        // List using offset and max
        listAllString = mockMvc
                .perform(MockMvcRequestBuilders.get("/customer/list?offset=5&max=7"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        listAll = gson.fromJson(listAllString,
                new TypeToken<ArrayList<Customer>>(){}.getType());
        Assert.assertEquals(7, listAll.size());
        Assert.assertEquals(customers1.get(5).id, listAll.get(0).id);
        Assert.assertEquals(updated.firstName, listAll.get(0).firstName);
        Assert.assertEquals(updated.lastName, listAll.get(0).lastName);


        // Remove all
        for (Customer customer : customers1) {
            mockMvc.perform(MockMvcRequestBuilders.post("/customer/" + customer.id + "/remove"))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            mockMvc.perform(MockMvcRequestBuilders.get("/customer/" + customer.id))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }


        // List empty
        String emptyListString = mockMvc
                .perform(MockMvcRequestBuilders.get("/customer/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Customer> emptyList = gson.fromJson(emptyListString,
                new TypeToken<ArrayList<Customer>>(){}.getType());
        Assert.assertTrue(emptyList.isEmpty());
    }

    Customer createCustomer(int postFix) {
        Customer res = new Customer();
        res.firstName = "Anton" + postFix;
        res.lastName = "Romankov" + postFix;
        res.mobile = "+7923229" + (1516 + postFix);
        res.email = "kalmarster" + postFix + "@gmail.com";
        res.bdYear = 1986 - postFix;
        res.bdMonth = 10;
        res.bdDay = 18;
        return res;
    }

}
