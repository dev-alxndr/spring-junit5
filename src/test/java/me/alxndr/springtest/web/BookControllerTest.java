package me.alxndr.springtest.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import me.alxndr.springtest.web.dto.BookDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

// 통합 테스트
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private TestRestTemplate rt;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerBook_test() throws JsonProcessingException {
        // given

        final String requestTitle = "hello";
        final String requestAuthor = "seungwon";
        BookDto.BookSaveRequestDto saveRequestDto = BookDto.BookSaveRequestDto
                .builder()
                .title(requestTitle)
                .author(requestAuthor)
                .build();
        final String json = objectMapper.writeValueAsString(saveRequestDto);

        // when
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);
        final ResponseEntity<String> exchange = rt.exchange("/api/v1/books", HttpMethod.POST, httpEntity, String.class);

        mockMvc.perform()

        // then
        final DocumentContext dc = JsonPath.parse(exchange.getBody());
        final String title = dc.read("$.body.title");
        final String author = dc.read("$.body.author");

        Assertions.assertThat(title).isEqualTo(requestTitle);
        Assertions.assertThat(author).isEqualTo(requestAuthor);

    }
}
