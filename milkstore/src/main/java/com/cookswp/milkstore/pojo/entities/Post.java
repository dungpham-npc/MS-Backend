package com.cookswp.milkstore.pojo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Post(){}

    public Post(int userID, String title, String content, String userComment) {
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.dateCreated = new Date();
        this.userComment = userComment;
    }

}
