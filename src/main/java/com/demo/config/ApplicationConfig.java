package com.demo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfig {

    @Autowired
    private ConfigurableEnvironment environment;

    @Value("${aws.ssm.env.hierarchy}")
    private String ssmHierarchy;

    @Value("${aws.access-key}")
    private String awsAccessKey;

    @Value("${aws.secret-key}")
    private String awsSecretKey;

    private static final String LOCAL = "local";
    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    private static final String DB_URL = "dbUrl";
    private static final String DB_USER = "dbUsername";
    private static final String DB_PASSWORD = "dbPassword";

    @Bean
    @Primary
    public DataSource dataSource() {
        mapEnvironmentProperties();
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));

        return dataSource;
    }

    private AWSSimpleSystemsManagement awsClient() {
        AWSSimpleSystemsManagement awsClient = null;

        if (environment.getProperty(SPRING_PROFILES_ACTIVE).equals(LOCAL)) {
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
            awsClient = AWSSimpleSystemsManagementClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_WEST_2)
                    .build();

        } else {
            awsClient = AWSSimpleSystemsManagementClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
        }

        return awsClient;
    }

    private void mapEnvironmentProperties() {

        AWSSimpleSystemsManagement awsClient = awsClient();

        Map<String, Object> props = new HashMap<>();

        GetParametersRequest paramRequest = new GetParametersRequest()
                .withNames(getParamKey(DB_URL), getParamKey(DB_USER), getParamKey(DB_PASSWORD))
                .withWithDecryption(true);

        GetParametersResult parameters = awsClient.getParameters(paramRequest);
        parameters.getParameters().forEach(parameter ->
                props.put(parameter.getName(), parameter.getValue())
        );

        MapPropertySource mapSource = new MapPropertySource("aws-ssm", props);
        environment.getPropertySources().addFirst(mapSource);
    }

    private String getParamKey(String param){
        return String.format(ssmHierarchy, param);
    }
}
