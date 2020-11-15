package com.movie.rental.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movie")
@Getter
@Setter
public class Movie extends AuditModel {
    @Id
    @GeneratedValue(generator = "movie_generator")
    @SequenceGenerator(
            name = "movie_generator",
            sequenceName = "movie_sequence",
            initialValue = 1000
    )

    /**
     * The Movie Id
     */
    private Long id;

    /**
     * The Movie Title
     */
    @NotBlank
    @Size(max = 500)
    @Column(nullable = false)
    private String title;

    /**
     * The Movie's description
     */
    @Size(max = 2000)
    private String description;

    /**
     * The Movie's image
     */
    @Size(max = 2000)
    private String image;

    /**
     * Movie's stock
     */
    @NotNull
    @Column(nullable = false)
    private Integer stock;

    /**
     * Movie's rental price
     */
    @NotNull
    @Column(name = "rental_price", nullable = false)
    private Double rentalPrice;

    /**
     * Movie's sale price
     */
    @NotNull
    @Column(name = "sale_price", nullable = false)
    private Double salePrice;

    /**
     * If the movie is available or not
     */
    @NotNull
    @Column(columnDefinition = "boolean default 'true'", nullable = false)
    private Boolean available;

    @Column(columnDefinition = "integer default '0'")
    private Integer likes = 0;

    @JsonIgnore
    @ManyToMany(targetEntity=User.class, fetch = FetchType.LAZY)
    @JoinTable(name = "likes",
    joinColumns =  @JoinColumn(name = "movie_id"),
    inverseJoinColumns =  @JoinColumn(name = "user_id"))
    private Set<User> usersWhoLike;

}
