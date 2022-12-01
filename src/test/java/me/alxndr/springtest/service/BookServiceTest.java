package me.alxndr.springtest.service;


import me.alxndr.springtest.domain.Book;
import me.alxndr.springtest.domain.BookRepository;
import me.alxndr.springtest.web.dto.BookDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    public void registerBookTest() {
        // given
        final BookDto.BookSaveRequestDto dto = BookDto.BookSaveRequestDto.builder()
                .title("코스모스")
                .author("칼 세이건")
                .build();

        // stub 행동 정의
        Book book = new Book(1L, "코스모스", "칼 세이건");
        when(bookRepository.save(any())).thenReturn(book);
        when(mailSender.send()).thenReturn(true);

        // when
        final BookDto.BookResponseDto bookResponseDto = bookService.registerBook(dto);

        // then
        assertThat(bookResponseDto.getId()).isNotNull();
        assertThat(bookResponseDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookResponseDto.getAuthor()).isEqualTo(dto.getAuthor());
    }


    @Test
    public void findAllTest() {
        // given

        // stub
        final List<Book> books = Arrays.asList(
                new Book(1L, "junit", "choi"),
                new Book(2L, "test", "seungwon")
        );
        when(bookRepository.findAll()).thenReturn(books);
        // when

        final List<BookDto.BookResponseDto> bookDtos = bookService.findBooks();
        // then
        assertThat(bookDtos.size()).isEqualTo(2);

        assertThat(bookDtos.get(0).getTitle()).isEqualTo("junit");
        assertThat(bookDtos.get(1).getTitle()).isEqualTo("test");

        assertThat(bookDtos.get(0).getAuthor()).isEqualTo("choi");
        assertThat(bookDtos.get(1).getAuthor()).isEqualTo("seungwon");
    }

    @Test
    public void findByIdTest() {
        // given
        Long id = 1L;

        //stub
        final Book book = new Book(1L, "junit", "choi");
        final Optional<Book> optionalBook = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(optionalBook);

        // when
        final BookDto.BookResponseDto bookById = bookService.findBookById(id);

        // then
        assertThat(bookById.getId()).isEqualTo(id);
        assertThat(bookById.getTitle()).isEqualTo(book.getTitle());
    }

    // 책 삭제는 안한다?

    @Test
    public void modifyBookTest() {
        // given
        Long id = 1L;
        final BookDto.BookSaveRequestDto bookSaveRequestDto =
                BookDto.BookSaveRequestDto.builder()
                        .title("spring")
                        .author("summer")
                        .build();
        // stub
        final Book book = new Book(1L, "junit", "choi");
        final Optional<Book> optBook = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(optBook);

        // when
        final BookDto.BookResponseDto bookResponseDto = bookService.modifyBookById(id, bookSaveRequestDto);

        // then
        assertThat(bookResponseDto.getId()).isEqualTo(1L);
        assertThat(bookResponseDto.getTitle()).isEqualTo("spring");
        assertThat(bookResponseDto.getAuthor()).isEqualTo("summer");

    }

}
