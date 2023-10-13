package edu.fudanselab.trainticket.init;

import edu.fudanselab.trainticket.entity.*;
import edu.fudanselab.trainticket.repository.AddMoneyRepository;
import edu.fudanselab.trainticket.repository.PaymentRepository;
import edu.fudanselab.trainticket.service.InsidePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author fdse
 */
@Component
public class InsidePaymentInitData implements CommandLineRunner {
    @Autowired
    InsidePaymentService service;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    AddMoneyRepository addMoneyRepository;

    @Override
    public void run(String... args) throws Exception{
        AccountInfo info1 = new AccountInfo();
        info1.setUserId("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f");
        info1.setMoney("10000");
        service.createAccount(info1,null);

        InsidePayment payment = new InsidePayment();
        payment.setId("5ad7750ba68b49c0a8c035276b321701");
        payment.setOrderId("5ad7750b-a68b-49c0-a8c0-32776b067702");
        payment.setPrice("100.0");
        payment.setUserId("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f");
        payment.setType(PaymentType.P);
        service.initPayment(payment,null);
    }
}

