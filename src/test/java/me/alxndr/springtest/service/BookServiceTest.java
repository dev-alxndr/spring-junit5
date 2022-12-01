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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    /*
    *
    * */

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

}
