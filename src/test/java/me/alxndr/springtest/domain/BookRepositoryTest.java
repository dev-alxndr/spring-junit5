package me.alxndr.springtest.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.event.annotation.AfterTestMethod;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Slf4j
@DataJpaTest // DB와 관련된 컴포넌트만 등록
class BookRepositoryTest {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	EntityManager em;

	// @BeforeAll - 테스트 시작 전 한번만 실행
	@BeforeEach	// 각 테스트 시작 전 한번씩 실행
	public void prepareBook() {
		String title = "junit";
		String author = "Alexander";

		final Book newBook = Book.builder()
				.title(title)
				.author(author)
				.build();

		// when
		final Book savedBook = bookRepository.save(newBook);
	}

	@AfterEach
	public void tearDown() {
		bookRepository.deleteAll();
		em.createNativeQuery("ALTER TABLE book ALTER COLUMN `id` RESTART").executeUpdate();
	}

	// 1. 책 등록
	@Test
	void saveBookTest() {
		log.debug("책 등록 테스트 실행");

		// given
		String title = "junit5";
		String author = "Alexander";

		final Book newBook = Book.builder()
				.title(title)
				.author(author)
				.build();

		// when
		final Book savedBook = bookRepository.save(newBook);

		// then
		assertThat(title).isEqualTo(savedBook.getTitle());
		assertThat(author).isEqualTo(savedBook.getAuthor());
	}

	// 2. 책 다건 검색
	@Test
	public void findBooksTest() {

		// given
		String title  = "junit";
		String author = "Alexander";
		// when

		// when
		final List<Book> books = bookRepository.findAll();

		// then
		assertThat(books.size()).isEqualTo(1);


		assertThat(title).isEqualTo(books.get(0).getTitle());
		assertThat(author).isEqualTo(books.get(0).getAuthor());
	}

	// 3. 책 단건 검색
	@Test
	public void findBookTest() {
		// given
		String title = "junit";
		String author = "Alexander";
		// when
		final Book book = bookRepository.findById(1L).get();

		// then
		assertThat(title).isEqualTo(book.getTitle());
		assertThat(author).isEqualTo(book.getAuthor());
	}


	// 4. 책 수정




	// 5. 책 삭제
	@Test
	public void deleteBookTest() {
		// given
		Long id = 1L;
		// when
		bookRepository.deleteById(id);
		// then
		final Optional<Book> optBook = bookRepository.findById(id);

		assertThat(optBook.isPresent()).isFalse();
	}
}
