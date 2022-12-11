package me.alxndr.springtest.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import me.alxndr.springtest.domain.Book;
import me.alxndr.springtest.domain.BookRepository;
import me.alxndr.springtest.web.dto.BookDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

// 통합 테스트
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    private TestRestTemplate rt;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    public void setUp() {
        String title = "book title";
        String author = "seungwon";

        final Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        bookRepository.save(book);
    }

    @AfterEach
    public void tearDown() {
        transactionTemplate.executeWithoutResult((status) -> {
            bookRepository.deleteAll();
            em.createNativeQuery("ALTER TABLE book ALTER COLUMN `id` RESTART").executeUpdate();
        });
    }

    @Test
    public void findBooks_test() {

        // when
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        final ResponseEntity<String> exchange = rt.exchange("/api/v1/books", HttpMethod.GET, httpEntity, String.class);

        // then
        final DocumentContext dc = JsonPath.parse(exchange.getBody());
        final Integer code = dc.read("$.code");
        final String title = dc.read("$.body.books[0].title");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("book title");

    }

    @Test
    public void findBook_test() {
        // given
        Long id = 1L;

        // when
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        final ResponseEntity<String> exchange = rt.exchange("/api/v1/books/" + id, HttpMethod.GET, httpEntity, String.class);

        // then
        final DocumentContext dc = JsonPath.parse(exchange.getBody());
        final Integer code = dc.read("$.code");
        final String title = dc.read("$.body.title");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("book title");

    }

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

        // then
        final DocumentContext dc = JsonPath.parse(exchange.getBody());
        final String title = dc.read("$.body.title");
        final String author = dc.read("$.body.author");

        assertThat(title).isEqualTo(requestTitle);
        assertThat(author).isEqualTo(requestAuthor);

    }


    @Test
    public void deleteBook_test() {
        // given
        Long id = 1L;

        // when
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        final ResponseEntity<String> exchange = rt.exchange("/api/v1/books/" + id, HttpMethod.DELETE, httpEntity, String.class);

        // then
        final String body = exchange.getBody();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void modifyBook_Test() throws JsonProcessingException {
        // given
        Long id = 1L;

        final String requestTitle = "hello";
        final String requestAuthor = "seungwon";
        BookDto.BookSaveRequestDto saveRequestDto = BookDto.BookSaveRequestDto
                .builder()
                .title(requestTitle)
                .author(requestAuthor)
                .build();

        final String modifyRequest = objectMapper.writeValueAsString(saveRequestDto);

        // when
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> httpEntity = new HttpEntity<>(modifyRequest, headers);
        final ResponseEntity<String> exchange = rt.exchange("/api/v1/books/" + id, HttpMethod.PUT, httpEntity, String.class);

        // then
        final DocumentContext dc = JsonPath.parse(exchange.getBody());
        final String title = dc.read("$.body.title");
        final String author = dc.read("$.body.author");

        assertThat(title).isEqualTo(requestTitle);
        assertThat(author).isEqualTo(requestAuthor);

    }
}
