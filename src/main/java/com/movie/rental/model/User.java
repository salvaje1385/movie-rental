package com.movie.rental.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends AuditModel {

    /**
     * The User Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The User's name
     */
    @Size(max = 100)
    @Column(nullable = false)
    private String name;

    /**
     * The User's email address
     */
    @Size(max = 100)
    private String email;

    /**
     * The User's password
     */
    @Size(max = 2000)
    private String password;

    /**
     * The User's phone
     */
    @Size(max = 80)
    private String phone;

    /**
     * The User's address
     */
    @Size(max = 2000)
    private String address;

    /**
     * The User's liked movies
     */
    @JsonIgnore
    @ManyToMany(targetEntity=Movie.class, fetch=FetchType.LAZY, mappedBy = "usersWhoLike")
    private Set<Movie> moviesLiked;
}
