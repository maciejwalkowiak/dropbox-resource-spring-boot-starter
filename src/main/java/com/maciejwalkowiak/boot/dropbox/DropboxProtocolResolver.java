package com.maciejwalkowiak.boot.dropbox;

import com.dropbox.core.v2.DbxClientV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Resolves resources with "dropbox://" prefix.
 *
 * @author Maciej Walkowiak
 */
public class DropboxProtocolResolver implements BeanFactoryPostProcessor, ProtocolResolver, ResourceLoaderAware {
    private static final Logger logger = LoggerFactory.getLogger(DropboxProtocolResolver.class);

    public static final String PREFIX = "dropbox://";

    private final DbxClientV2 client;
    private ResourceLoader resourceLoader;

    public DropboxProtocolResolver(DbxClientV2 client) {
        this.client = client;
    }

    @Override
    public Resource resolve(String s, ResourceLoader resourceLoader) {
        if (s.startsWith(PREFIX)) {
            return new DropboxResource(s.substring(PREFIX.length()), client);
        }
        return null;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        if (DefaultResourceLoader.class.isAssignableFrom(resourceLoader.getClass())) {
            ((DefaultResourceLoader) resourceLoader).addProtocolResolver(this);
        }
        else {
            logger.warn("The provided delegate resource loader is not an implementation "
                    + "of DefaultResourceLoader. Custom Protocol using dropbox:// prefix will not be enabled.");
        }
    }
}
