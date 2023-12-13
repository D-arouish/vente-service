package org.sid.billingservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.sid.billingservice.model.Customer;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;


@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billDate;
    private Long customerId;
    @OneToMany(mappedBy = "bill")
    private List<ProductItem> productItems;

    @Transient
    private Customer customer;

}
