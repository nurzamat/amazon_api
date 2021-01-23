package com.dropmakers.amazonapi;

import io.swagger.client.model.MarketplaceParticipation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AmazonapiApplicationTests {

    @Test
    void contextLoads() {
        SPAPI spapi = new SPAPI();
        // Fetch all the marketplaces that your Seller Central account participates in
        List<MarketplaceParticipation> participations = spapi.getMarketplaceParticipations();
        // And print em all out!
        for (MarketplaceParticipation p : participations) {
            System.out.println(p.getMarketplace());
        }
    }
}
