package edu.fudanselab.trainticket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author fdse
 */
@Data
@AllArgsConstructor
public class CommonOrderAlterInfo {
    private String accountId;

    private String previousOrderId;

    private String loginToken;

    private CommonOrder newOrderInfo;

    public CommonOrderAlterInfo(){
        newOrderInfo = new CommonOrder();
    }
}
