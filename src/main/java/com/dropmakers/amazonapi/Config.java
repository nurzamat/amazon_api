package com.dropmakers.amazonapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Config {
    private Properties props;

    public Config() {
        Properties props = new Properties();
        InputStream in = Config.class
                .getClassLoader()
                .getResourceAsStream("application-eu.properties");
        try {
            props.load(in);
            in.close();
            this.props = props;
        } catch (IOException e) {
            System.err.println("Error while reading in amazon.properties: ");
            e.printStackTrace();
        }
    }

    public Properties getProps() {
        return props;
    }

    private String getProp(String propName) {
        return props.getProperty(propName);
    }

    public String getAwsAccessKeyId() {
        return getProp("aws.accessKeyId");
    }
    public String getAwsSecretKey() {
        return getProp("aws.secretKey");
    }
    public String getAwsRegion() {
        return getProp("aws.region");
    }
    public String getAwsRoleArn() {
        return getProp("aws.role.arn");
    }
    public String getLwaAuthEndpoint() {
        return getProp("lwa.authEndpoint");
    }
    public String getLwaClientId() {
        return getProp("lwa.clientId");
    }
    public String getLwaClientSecret() {
        return getProp("lwa.clientSecret");
    }
    public String getLwaRefreshToken() {
        return getProp("lwa.refreshToken");
    }
    public String getSPAPIEndpoint() {
        return getProp("spapi.endpoint");
    }
    public List<String> getSPAPIMarketplaceIds() {
        String rawProp = getProp("spapi.marketplaceIds");
        return new ArrayList<String>(Arrays.asList(rawProp));
    }
}