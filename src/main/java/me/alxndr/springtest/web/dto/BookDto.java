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

	public static class BookResponseDto {

	}

}
