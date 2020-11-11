package com.movie.rental.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "users")
@Data // lombok data annotation
public class User extends AuditModel {

    /**
     * The User Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String email;

    @Size(max = 2000)
    private String password;

    @Size(max = 80)
    private String phone;

    @Size(max = 2000)
    private String address;

    @ManyToMany(targetEntity=Movie.class, fetch=FetchType.LAZY, mappedBy = "usersWhoLike")
    private Set<Movie> moviesLiked;
}
