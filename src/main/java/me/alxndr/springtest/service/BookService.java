package me.alxndr.springtest.service;

import lombok.RequiredArgsConstructor;
import me.alxndr.springtest.domain.Book;
import me.alxndr.springtest.domain.BookRepository;
import me.alxndr.springtest.web.dto.BookDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 책 등록
    @Transactional
    public BookDto.BookResponseDto registerBook(final BookDto.BookSaveRequestDto dto) {
        final Book book = dto.toEntity();
        bookRepository.save(book);
        return BookDto.BookResponseDto.of(book);
    }

    // 책 목록보기
    public List<BookDto.BookResponseDto> findBooks() {
        return bookRepository.findAll().stream()
                .map(BookDto.BookResponseDto::of)
                .collect(Collectors.toList());
    }
    // 책 한건 보기

    // 책 삭제

    // 책 수정

}
