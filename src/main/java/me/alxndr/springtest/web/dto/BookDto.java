package me.alxndr.springtest.web.dto;

import lombok.Builder;
import lombok.Data;
import me.alxndr.springtest.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;

public class BookDto {

	@Data
	@Builder
	public static class BookSaveRequestDto {
		private String title;
		private String author;

		public Book toEntity() {
			return Book.builder()
					.title(title)
					.author(author)
					.build();
		}
	}

	@Data
	@Builder
	public static class BookResponseDto {
		private Long id;

		private String title;

		private String author;

		public static BookResponseDto of(final Book book) {
			return BookResponseDto.builder()
					.id(book.getId())
					.title(book.getTitle())
					.author(book.getAuthor())
					.build();
		}
	}

}
