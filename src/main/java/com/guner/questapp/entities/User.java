package com.guner.questapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name="seq_user",allocationSize = 1)
    @GeneratedValue(generator = "seq_user",strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "uname")
    private String userName;

    @Column(name = "pwd")
    private String password;

    @Column(name = "avatar")
    private int avatar;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<Post> posts;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<Like> likes;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<Comment> comments;

}
