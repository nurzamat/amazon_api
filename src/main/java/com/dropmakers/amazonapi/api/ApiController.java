package com.dropmakers.amazonapi.api;

import com.dropmakers.amazonapi.SPAPI;
import io.swagger.client.ApiException;
import io.swagger.client.model.Item;
import io.swagger.client.model.MarketplaceParticipation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    private static final String template = "Hello, %s!";


    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }

    @GetMapping("/participations")
    public List<MarketplaceParticipation> participations(@RequestParam(value = "name", defaultValue = "World") String name) {
        SPAPI spapi = new SPAPI();
        // Fetch all the marketplaces that your Seller Central account participates in
        List<MarketplaceParticipation> participations = spapi.getMarketplaceParticipations();
        // And print em all out!
        for (MarketplaceParticipation p : participations) {
            System.out.println(p.getMarketplace());
        }
        return participations;
    }

    @GetMapping("/products")
    public List<Item> searchProduct(@RequestParam(value = "query", defaultValue = "pen") String query) {
        SPAPI spapi = new SPAPI();
        ArrayList<Item> result = new ArrayList<>();
        try{
            result = spapi.searchProduct(query);
        }
        catch (ApiException e) {
            e.printStackTrace();
        }
        return result;
    }
}
