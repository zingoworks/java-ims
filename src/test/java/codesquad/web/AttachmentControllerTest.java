package codesquad.web;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;


public class AttachmentControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);

    @Test
    public void download() throws Exception {
        // upload
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("import.sql"))
                .build();
        ResponseEntity<String> result = template.postForEntity("/attachments", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());

        // download
        result = template.getForEntity("/attachments/1", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        log.debug("body : {}", result.getBody());
    }

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        log.info("request info : {}", request.getBody());
        ResponseEntity<String> result = template.postForEntity("/attachments", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

    @Test
    public void uploadAnotherFile() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("import.sql"))
                .build();
        ResponseEntity<String> result = template.postForEntity("/attachments", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }
}