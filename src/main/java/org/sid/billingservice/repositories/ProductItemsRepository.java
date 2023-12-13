package org.sid.billingservice.repositories;

import org.sid.billingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductItemsRepository extends JpaRepository<ProductItem, Long> {
}
