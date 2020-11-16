package com.movie.rental.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.rental.model.Purchase;
import com.movie.rental.model.User;
import com.movie.rental.repository.PurchaseRepository;
import com.movie.rental.service.PurchaseService;
import com.movie.rental.service.UserDetailsImpl;
import com.movie.rental.service.dto.PurchaseDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * Purchase Controller
 */
@Slf4j // lombok
@RestController
public class PurchaseController extends AbstractController {

    private final PurchaseRepository purchaseRepository;

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(final PurchaseRepository purchaseRepository,
            final PurchaseService purchaseService) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseService = purchaseService;
    }

    /**
     * Get all the purchases
     * @param pageable A {@link Pageable} object
     * @return A {@link Page} of {@link Purchase}
     */
    @GetMapping("/purchases")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Purchase> getPurchases(final Pageable pageable) {
        log.info("Get Purchases: {}", pageable);

        final UserDetailsImpl userDetails = getLoggedInUser();

        // Regular users only get the list of purchases they've made
        if (!userDetails.isAdmin()) {
            final User user = getPurchaseService().checkIfUserExists(userDetails.getId());
            log.info("Getting this User purchases: {} - {}", user.getId(), user.getUsername());

            return purchaseRepository.findByUser(user);
        } else {
            return purchaseRepository.findAll(pageable).getContent();
        }
    }

    /**
     * Delete a {@link Purchase}
     * @param purchaseId The {@link Purchase}'s Id
     * @return A {@link ResponseEntity} object
     */
    @DeleteMapping("/purchases/{purchaseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePurchase(@PathVariable final Long purchaseId) {
        log.info("Delete Purchase: {}", purchaseId);

        getPurchaseService().deletePurchase(purchaseId);

        return ResponseEntity.ok().build();
    }

    /**
     * Create a purchase
     * @param purchase The {@link PurchaseDTO} object
     * @return The created {@link Purchase}
     */
    @PostMapping("/purchases")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Purchase createPurchase(@Valid @RequestBody final PurchaseDTO purchaseDTO) {
        log.info("Create a Purchase: {}", purchaseDTO);
        return getPurchaseService().saveOrUpdatePurchase(purchaseDTO);
    }

    /**
     * Update a purchase
     * @param id The {@link Purchase}'s Id
     * @param purchase The {@link PurchaseDTO} object
     * @return The updated {@link Purchase}
     */
    @PutMapping("purchases/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Purchase updatePurchase(@PathVariable final Long id,
            @Valid @RequestBody final PurchaseDTO purchaseDTO) {

        purchaseDTO.setId(id);
        log.info("Update a Purchase: {}", purchaseDTO);
        return getPurchaseService().saveOrUpdatePurchase(purchaseDTO);
    }

    /**
     * Getter for the {@link PurchaseService}
     * @return The {@link PurchaseService}
     */
    public PurchaseService getPurchaseService() {
        return this.purchaseService;
    }
}
