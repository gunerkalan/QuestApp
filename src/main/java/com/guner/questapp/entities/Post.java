package com.guner.questapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private Long id;

    @JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(optional=true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private User user;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private Set<Like> likes;

    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private Set<Comment> comments;
}
