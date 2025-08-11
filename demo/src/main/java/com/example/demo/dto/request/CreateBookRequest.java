package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Getter
@Setter
@ToString
public class CreateBookRequest {

    private String title;

    private String author;

    public boolean isValid() {
        return StringUtils.hasText(this.title) && StringUtils.hasText(this.author);
    }
}
