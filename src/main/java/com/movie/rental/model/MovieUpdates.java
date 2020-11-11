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

@Entity
@Table(name = "movie_updates")
final @Data // lombok data annotation
public class MovieUpdates extends AuditModel {

    /**
     * The User Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The Movie being updated
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "MOVIE_ID", nullable = false)
    private Movie movie;

    /**
     * The type of the update being made
     */
    @Column(name = "update_type", nullable = false)
    private UpdateType updateType;

    /**
     * The updated field's old value
     */
    @Column(name = "old_value")
    private String oldValue;

    /**
     * The updated field's new value
     */
    @Column(name = "new_value", nullable = false)
    private String newValue;

    /**
     * An enum representing the type of the update being made
     */
    public enum UpdateType {
        /** 0 = If a title update was done */
        TITLE_UPDATE,
        /** 1 = If a rental price update was done */
        RENTAL_PRICE_UPDATE,
        /** 2 = If a sale price update was done */
        SALE_PRICE_UPDATE;
    }
}
