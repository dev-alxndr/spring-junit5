package me.alxndr.springtest.service;

import lombok.RequiredArgsConstructor;
import me.alxndr.springtest.domain.Book;
import me.alxndr.springtest.domain.BookRepository;
import me.alxndr.springtest.web.dto.BookDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 책 등록
    @Transactional
    public BookDto.BookResponseDto registerBook(final BookDto.BookSaveRequestDto dto) {
        final Book book = bookRepository.save(dto.toEntity());
        mailSender.send();
        return BookDto.BookResponseDto.of(book);
    }

    // 책 목록보기
    public List<BookDto.BookResponseDto> findBooks() {
        return bookRepository.findAll().stream()
                .map(BookDto.BookResponseDto::of)
                .collect(Collectors.toList());
    }

    // 책 한건 보기
    public BookDto.BookResponseDto findBookById(final Long id) {
        final Book book = bookRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return BookDto.BookResponseDto.of(book);
    }

    // 책 삭제
    @Transactional
    public void deleteBookById(final Long id) {
        Objects.requireNonNull(id);
        bookRepository.deleteById(id);
    }

    // 책 수정
    @Transactional
    public BookDto.BookResponseDto modifyBookById(Long id, BookDto.BookSaveRequestDto dto) {
        final Book book = bookRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        book.update(dto.getTitle(), dto.getAuthor());

        return BookDto.BookResponseDto.of(book);
    }

}
