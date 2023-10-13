package edu.fudanselab.trainticket.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author fdse
 */
@Data
@Entity
@GenericGenerator(name = "inside-payment-jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
@Table(name="inside_payment")
public class InsidePayment {
    @Id
    @NotNull
    @Column(length = 36)
    @GeneratedValue(generator = "inside-payment-jpa-uuid")
    private String id;

    @NotNull
    @Valid
    @Column(length = 36)
    private String orderId;

    @NotNull
    @Valid
    @Column(length = 36)
    private String userId;

    @NotNull
    @Valid
    private String price;

    @NotNull
    @Valid
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    public InsidePayment(){
        this.orderId = "";
        this.userId = "";
        this.price = "";
    }

}
