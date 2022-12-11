package me.alxndr.springtest.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.alxndr.springtest.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class BookDto {

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
