package com.movie.rental.model;

import java.util.Date;

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

@Entity
@Table(name = "rental")
final @Data // lombok data annotation
public class Rental extends AuditModel {

    /**
     * The Rental Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The Movie being rented
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;

    /**
     * The User who rented
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    /**
     * The rental's price
     */
    @Column(nullable = false)
    private Double price;

    /**
     * A monetary penalty if there is a delay
     */
    private Double penalty;

    /**
     * The date when the rental expires
     */
    @Column(nullable = false)
    private Date dueDate;

    /**
     * The date when the movie was returned
     */
    private Date returnDate;
}
