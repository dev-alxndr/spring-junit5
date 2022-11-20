package me.alxndr.springtest.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@Slf4j
@DataJpaTest // DB와 관련된 컴포넌트만 등록
class BookRepositoryTest {

	@Autowired
	BookRepository bookRepository;

	@Test
	// 1. 책 등록
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

	// 3. 책 단건 검색

	// 4. 책 수정

	// 5. 책 삭제

}