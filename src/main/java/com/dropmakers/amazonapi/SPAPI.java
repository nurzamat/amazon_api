package com.dropmakers.amazonapi;
import java.util.ArrayList;
import java.util.List;

import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentials;
import com.amazon.SellingPartnerAPIAA.AWSAuthenticationCredentialsProvider;
import com.amazon.SellingPartnerAPIAA.LWAAuthorizationCredentials;
import io.swagger.client.ApiException;
import io.swagger.client.api.SellersApi;
import io.swagger.client.model.GetMarketplaceParticipationsResponse;
import io.swagger.client.model.MarketplaceParticipation;


public class SPAPI {
    protected Config config;
    protected SellersApi api;
    protected AWSAuthenticationCredentials awsAuthCreds;
    protected AWSAuthenticationCredentialsProvider awsAuthCredsProvider;
    protected LWAAuthorizationCredentials lwaAuthCreds;

    public SPAPI() {
        // Load the config information
        this.config = new Config();
        // Authenticate with Amazon
        this._authenticate();

        // Each application role corresponds to one or more ___Api classes. In this case, we're
        // using the SellersApi class, which maps to the Selling Partner Insights role.
        api = new SellersApi.Builder()
                .awsAuthenticationCredentials(this.awsAuthCreds)
                .lwaAuthorizationCredentials(this.lwaAuthCreds)
                .awsAuthenticationCredentialsProvider(this.awsAuthCredsProvider)
                .endpoint(this.config.getSPAPIEndpoint())
                .build();
    }

    public List<MarketplaceParticipation> getMarketplaceParticipations() {
        GetMarketplaceParticipationsResponse res = null;
        // Many SP API operations can throw ApiExceptions, so you'll often need to handle them
        try {
            // You can find all the API endpoints in the docs (linked below). The operation name
            // for an endpoint is generally the same name as the name of the corresponding Java
            // method. Here are the docs for this call:
            // https://github.com/amzn/selling-partner-api-docs/blob/main/references/sellers-api/sellers.md#getmarketplaceparticipations
            res = api.getMarketplaceParticipations();
        } catch (ApiException e) {
            System.err.println("Error while trying to fetch marketplace participations: ");
            e.printStackTrace();
        }

        List<MarketplaceParticipation> data = new ArrayList<MarketplaceParticipation>();
        if (res != null && res.getErrors() == null) {
            data = res.getPayload();
        }

        return data;
    }

    private void _authenticate() {
        // Configure AWS credentials
        awsAuthCreds = AWSAuthenticationCredentials.builder()
                .accessKeyId(this.config.getAwsAccessKeyId())
                .secretKey(this.config.getAwsSecretKey())
                .region(this.config.getAwsRegion())
                .build();

        // Configure LWA credentials
        lwaAuthCreds = LWAAuthorizationCredentials.builder()
                .clientId(this.config.getLwaClientId())
                .clientSecret(this.config.getLwaClientSecret())
                .refreshToken(this.config.getLwaRefreshToken())
                .endpoint(this.config.getLwaAuthEndpoint())
                .build();

        //role
        awsAuthCredsProvider = AWSAuthenticationCredentialsProvider.builder()
                .roleArn(this.config.getAwsRoleArn())
                .roleSessionName("sp-api")
                .build();
    }
}
