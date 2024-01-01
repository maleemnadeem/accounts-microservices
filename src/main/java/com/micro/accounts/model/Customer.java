package com.micro.accounts.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name="native")
    @Column(name="customer_id" )
    private Long customerId;
    private String name;
    private String email;
    @Column(name="mobile_number")
    private String mobileNumber;
}
