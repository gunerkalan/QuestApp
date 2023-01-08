package com.guner.questapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @SequenceGenerator(name="seq_comment",allocationSize = 1)
    @GeneratedValue(generator = "seq_comment",strategy = GenerationType.SEQUENCE)
    private Long id;

    @JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(optional=true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    @JsonIgnore
    private User user;

    @JoinColumn(name="post_id", nullable = false, referencedColumnName = "id")
    @ManyToOne(optional=true, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    @JsonIgnore
    private Post post;

    @Lob
    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;

}
