package com.maciejwalkowiak.boot.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = { "dropbox.access-token=foo", "dropbox.client-identifier=123" })
class DropboxResourceTest {

    @SpringBootConfiguration
    @Import(DropboxAutoConfiguration.class)
    static class Config {

    }

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private DbxClientV2 dbxClientV2;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void resolvesTheResourceFromDropbox() throws DbxException, IOException {
        when(dbxClientV2.files().download("/file.txt").getInputStream()).thenReturn(new ByteArrayInputStream("hello world".getBytes()));

        Resource resource = resourceLoader.getResource("dropbox://file.txt");

        String content = streamToString(resource.getInputStream());
        assertThat(content).isEqualTo("hello world");
    }

    private static String streamToString(InputStream inputStream) {
        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }

}