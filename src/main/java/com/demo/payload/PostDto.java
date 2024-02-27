package com.demo.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min = 3, message = "title should be at least 3 character")
    private String title;

    @NotEmpty(message = "description is empty")
    @Size(min = 5, message = "description should not less than 5 character")
    private String description;

    private String content;
}
