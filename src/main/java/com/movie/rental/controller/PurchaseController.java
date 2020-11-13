package com.movie.rental.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.rental.exception.ResourceNotFoundException;
import com.movie.rental.model.Purchase;
import com.movie.rental.repository.PurchaseRepository;
import com.movie.rental.service.PurchaseService;
import com.movie.rental.service.dto.PurchaseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j // lombok
@RestController
public class PurchaseController {

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
    public Page<Purchase> getPurchases(final Pageable pageable) {
        log.info("Get Purchases: {}", pageable);
        return purchaseRepository.findAll(pageable);
    }

    /**
     * Delete a {@link Purchase}
     * @param purchaseId The {@link Purchase}'s Id
     * @return A {@link ResponseEntity} object
     */
    @DeleteMapping("/purchases/{purchaseId}")
    public ResponseEntity<?> deletePurchase(@PathVariable final Long purchaseId) {
        log.info("Delete Purchase: {}", purchaseId);
        return purchaseRepository.findById(purchaseId)
                .map(purchase -> {
                    purchaseRepository.delete(purchase);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id " + purchaseId));
    }

    /**
     * Create a purchase
     * @param purchase The {@link PurchaseDTO} object
     * @return The created {@link Purchase}
     */
    @PostMapping("/purchases")
    public Purchase createPurchase(@Valid @RequestBody final PurchaseDTO purchaseDTO) {
        log.info("Create Purchase: {}", purchaseDTO);
        return getPurchaseService().createPurchase(purchaseDTO);
    }

    /**
     * Getter for the {@link PurchaseService}
     * @return The {@link PurchaseService}
     */
    public PurchaseService getPurchaseService() {
        return this.purchaseService;
    }
}
