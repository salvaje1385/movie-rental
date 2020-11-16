package com.movie.rental.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Purchase model
 */
@Entity
@Table(name = "purchase")
final @Data // lombok data annotation
public class Purchase extends AuditModel{

    /**
     * The purchase Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The Movie being purchased
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;

    /**
     * The User who purchased
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /**
     * The purchase's price
     */
    @Column(nullable = false)
    private Double price;
}
