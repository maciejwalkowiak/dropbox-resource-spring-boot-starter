package com.maciejwalkowiak.boot.dropbox;

import java.io.InputStream;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;

import org.springframework.core.io.AbstractResource;

/**
 * Read-only {@link org.springframework.core.io.Resource} that abstract resource stored on Dropbox.
 *
 * @author Maciej Walkowiak
 */
public class DropboxResource extends AbstractResource {
    private final String path;

    private final DbxClientV2 client;

    public DropboxResource(String path, DbxClientV2 client) {
        this.path = "/" + path;
        this.client = client;
    }

    @Override
    public String getDescription() {
        return "Dropbox [path=" + this.path + "]";
    }

    @Override
    public InputStream getInputStream() {
        try {
            return client.files().download(this.path).getInputStream();
        }
        catch (DbxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFilename() {
        try {
            return client.files().getMetadata(this.path).getName();
        }
        catch (DbxException e) {
            throw new RuntimeException(e);
        }
    }
}
