package com.cookswp.milkstore.pojo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "post")
public class Post implements Serializable {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false)
    private int userID;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date_created")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateCreated;

    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "visibility_status")
    private boolean visibility = true;

    public Post(){}


}
