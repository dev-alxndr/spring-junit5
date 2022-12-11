package me.alxndr.springtest.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.alxndr.springtest.service.BookService;
import me.alxndr.springtest.web.dto.BookDto;
import me.alxndr.springtest.web.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    // 책등록하기
    @PostMapping
    public ResponseEntity registerBook(@RequestBody @Valid BookDto.BookSaveRequestDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(Response.error(bindingResult), HttpStatus.BAD_REQUEST);
        }

        final BookDto.BookResponseDto savedBook = bookService.registerBook(dto);

        return new ResponseEntity(Response.OK(savedBook), HttpStatus.CREATED);
    }

    // 책 목록 보기
    @GetMapping
    public ResponseEntity<Response<Object>> findBooks() {
        final List<BookDto.BookResponseDto> books = bookService.findBooks();
        final BookDto.BooksDto booksDto = BookDto.BooksDto.of(books);

        return new ResponseEntity<>(Response.OK(booksDto), HttpStatus.OK);
    }

    // 책 한건 보기
//    @GetMapping("/{id}")
    public void findBook() {

    }


    // 책 수정
//    @PutMapping
    public void modifyBook() {

    }

}
