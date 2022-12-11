package me.alxndr.springtest.web;

import lombok.RequiredArgsConstructor;
import me.alxndr.springtest.service.BookService;
import me.alxndr.springtest.web.dto.BookDto;
import me.alxndr.springtest.web.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    // 책등록하기
    @PostMapping
    public ResponseEntity registerBook(@RequestBody BookDto.BookSaveRequestDto dto) {
        final BookDto.BookResponseDto savedBook = bookService.registerBook(dto);

        final Response response = Response.builder()
                .code(Response.Code.SUCCESS)
                .message(Response.Message.success)
                .body(savedBook)
                .build();

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    // 책 목록 보기
//    @GetMapping
    public void findBooks() {

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
