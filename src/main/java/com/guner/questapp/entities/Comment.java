package com.guner.questapp.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    private Long id;

    @JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(optional=true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private User user;

    @JoinColumn(name="post_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(optional=true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Post post;

    @Lob
    @Column(name = "text", columnDefinition = "text")
    private String text;

}
