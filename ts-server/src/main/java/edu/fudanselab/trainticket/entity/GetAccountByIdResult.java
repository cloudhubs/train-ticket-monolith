package edu.fudanselab.trainticket.entity;

import edu.fudanselab.trainticket.entity.Account;
import lombok.Data;
import edu.fudanselab.trainticket.entity.Account;

/**
 * @author fdse
 */
@Data
public class GetAccountByIdResult {

    private boolean status;

    private String message;

    private Account account;

    public GetAccountByIdResult() {
        //Default Constructor
    }

}
