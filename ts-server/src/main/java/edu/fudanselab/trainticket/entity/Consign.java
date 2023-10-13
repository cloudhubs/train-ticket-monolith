package edu.fudanselab.trainticket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@GenericGenerator(name="consign-jpa-uuid",strategy ="org.hibernate.id.UUIDGenerator")
public class Consign {
    @Id
    @GeneratedValue(generator = "consign-jpa-uuid")
    @Column(length = 36)
    private String id;        //id主键改成String类型的 自定义生成策略
    private String orderId;   //这次托运关联订单
    private String accountId;  //这次托运关联的账户

    private String handleDate;
    private String targetDate;
    private String from;
    private String to;
    private String consignee;
    private String phone;
    private double weight;
    private boolean isWithin;


}
