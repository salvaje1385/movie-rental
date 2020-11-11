package com.movie.rental.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "movie")
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
    private Integer stock;

    /**
     * Movie's rental price
     */
    @Column(name = "rental_price")
    private Double rentalPrice;

    /**
     * Movie's sale price
     */
    @Column(name = "sale_price")
    private Double salePrice;

    /**
     * If the movie is available or not
     */
    private Boolean available;

    /**
     * Getter for the movie's id
     * @return The movie's id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the movie's id
     * @param The movie's id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Getter for the movie's title
     * @return The movie's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for the movie's the movie's title
     * @param The movie's title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Getter for the movie's description
     * @return The movie's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the movie's description
     * @param The movie's description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter for the movie's image
     * @return The movie's image
     */
    public String getImage() {
        return image;
    }

    /**
     * Setter for the movie's image
     * @param image The movie's image
     */
    public void setImage(final String image) {
        this.image = image;
    }

    /**
     * Getter for the movie's stock
     * @return The movie's stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * Setter for the movie's stock
     * @param The movie's stock
     */
    public void setStock(final Integer stock) {
        this.stock = stock;
    }

    /**
     * Getter for the movie's rental price
     * @return The movie's rental price
     */
    public Double getRentalPrice() {
        return rentalPrice;
    }

    /**
     * Setter for the movie's rental price
     * @param The movie's rental price
     */
    public void setRentalPrice(final Double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    /**
     * Getter for the movie's rental price
     * @return The movie's rental price
     */
    public Double getSalePrice() {
        return salePrice;
    }

    /**
     * Setter for the movie's sale price
     * @param The movie's sale price
     */
    public void setSalePrice(final Double salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * Getter for the movie's availability
     * @return The movie's availability
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * Setter for the movie's availability
     * @param The movie's availability
     */
    public void setAvailable(final Boolean available) {
        this.available = available;
    }

    /**
     * The toString method for this class
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Movie [");
        sb.append("id = ");
        sb.append(this.id);
        sb.append(", title = ");
        sb.append(this.title);
        sb.append(", description = ");
        sb.append(this.description);
        sb.append(", image = ");
        sb.append(this.image);
        sb.append(", stock = ");
        sb.append(this.stock);
        sb.append(", rentalPrice = ");
        sb.append(this.rentalPrice);
        sb.append(", salePrice = ");
        sb.append(this.salePrice);
        sb.append(", available = ");
        sb.append(this.available);
        sb.append("]");
        return sb.toString();
    }
}
