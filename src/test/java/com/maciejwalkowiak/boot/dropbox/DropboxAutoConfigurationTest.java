package com.maciejwalkowiak.boot.dropbox;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link DropboxAutoConfiguration}.
 *
 * @author Maciej Walkowiak
 */
class DropboxAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(DropboxAutoConfiguration.class));

    @Test
    void createsProtocolResolverWhenPropertiesAreSet() {
        contextRunner
                .withPropertyValues("dropbox.access-token=xyz", "dropbox.client-identifier=abc")
                .run(context -> assertThat(context.getBean(DropboxProtocolResolver.class)).isNotNull());
    }

    @Test
    void doesNotCreateProtocolResolverWhenAccessTokenMissing() {
        contextRunner
                .withPropertyValues("dropbox.client-identifier=abc")
                .run(context -> assertThatThrownBy(() -> context.getBean(DropboxProtocolResolver.class))
                        .isInstanceOf(NoSuchBeanDefinitionException.class));
    }

    @Test
    void doesNotCreateProtocolResolverWhenClientIdentifierMissing() {
        contextRunner
                .withPropertyValues("dropbox.access-token=xyz")
                .run(context -> assertThatThrownBy(() -> context.getBean(DropboxProtocolResolver.class))
                        .isInstanceOf(NoSuchBeanDefinitionException.class));
    }

}