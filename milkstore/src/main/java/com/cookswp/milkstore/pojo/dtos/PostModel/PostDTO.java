package com.cookswp.milkstore.pojo.dtos.PostModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @NotNull(message = "User ID can not be null")
    private int userID;

    @NotNull(message = "Title can not be null")
    private String title;

    @NotNull(message = "Content can not be null")
    private String content;

    @NotNull(message = "Date created can not be null")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateCreated;

    private String userComment;
}
