package me.alxndr.springtest.web.dto;

import lombok.*;
import me.alxndr.springtest.domain.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class BookDto {

	@Data
	@Builder
	public static class BooksDto {
		private List<BookResponseDto> books;

		public static BooksDto of(final List<BookResponseDto> dtos) {
			return BooksDto.builder()
					.books(dtos)
					.build();
		}
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class BookSaveRequestDto {
		@Size(min = 1, max = 50)
		@NotBlank
		private String title;

		@Size(min = 2, max = 20)
		@NotBlank
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
