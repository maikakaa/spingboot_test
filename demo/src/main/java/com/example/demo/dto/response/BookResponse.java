package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookResponse {

    private Long id;

    private String title;

    private String author;
}
