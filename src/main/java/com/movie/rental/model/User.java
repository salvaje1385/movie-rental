package com.movie.rental.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * User model
 */
@Entity
@Table(name = "users",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
public class User extends AuditModel {

    public User() {
    }

    public User(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * The User Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The username to signin
     */
    @Size(max = 100)
    @Column(nullable = false)
    private String username;

    /**
     * The User's email address
     */
    @Size(max = 100)
    private String email;

    /**
     * The User's password
     */
    @Size(max = 150)
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
     * The roles related to this User
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /**
     * The User's liked movies
     */
    @JsonIgnore
    @ManyToMany(targetEntity=Movie.class, fetch=FetchType.LAZY, mappedBy = "usersWhoLike")
    private Set<Movie> moviesLiked;
}
