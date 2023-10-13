package edu.fudanselab.trainticket.entity;

import lombok.Data;

/**
 * @author fdse
 */
@Data
public class AccountInfo {

    private String userId;

    private String money;

    public AccountInfo(){
        //Default Constructor
    }

}
