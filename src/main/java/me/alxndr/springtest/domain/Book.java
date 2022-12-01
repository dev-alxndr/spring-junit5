package me.alxndr.springtest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	private String title;

	@Column(length = 20, nullable = false)
	private String author;

	public void changeTitle(final String to) {
		this.title = to;
	}

	public void changeAuthor(final String to) {
		this.author = to;
	}

	public void update(final String title, final String author) {
		this.title = title;
		this.author = author;
	}
}
