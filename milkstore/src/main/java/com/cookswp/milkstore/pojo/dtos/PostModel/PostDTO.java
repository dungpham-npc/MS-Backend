package com.cookswp.milkstore.pojo.dtos.PostModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @NotNull(message = "TITLE_NULL")
    @NotBlank(message = "TITLE_BLANK")
    @NotEmpty(message = "TITLE_EMPTY")
    private String title;

    @NotNull(message = "CONTENT_NULL")
    @NotBlank(message = "CONTENT_BLANK")
    @NotEmpty(message = "CONTENT_EMPTY")
    private String content;


}
