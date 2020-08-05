package com.maciejwalkowiak.boot.dropbox;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Auto-configuration for Dropbox integration.
 */
@Configuration
public class DropboxAutoConfiguration {

    @Bean
    public DbxRequestConfig dropboxRequestConfig(Environment environment) {
        return DbxRequestConfig
                .newBuilder(environment.getProperty("dropbox.client-identifier"))
                .build();
    }

    @Bean
    public DbxClientV2 dropboxClient(DbxRequestConfig dbRequestConfig, Environment environment) {
        return new DbxClientV2(dbRequestConfig, environment.getProperty("dropbox.access-token"));
    }

    @Bean
    public static DropboxProtocolResolver googleDriveProtocolResolver(DbxClientV2 dbxClientV2) {
        return new DropboxProtocolResolver(dbxClientV2);
    }
}
