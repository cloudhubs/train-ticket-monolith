package edu.fudanselab.trainticket.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.UUID;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@GenericGenerator(name = "money-jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
@Entity
public class Money {
    /*@Id
    @Column(name = "id", nullable = false, length = 36)
    @GeneratedValue(generator = "money-jpa-uuid")
    private String id;*/

    @Id
    @GeneratedValue(generator = "money-jpa-uuid")
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(length = 36)
    private String userId;
    private String money; //NOSONAR



}
