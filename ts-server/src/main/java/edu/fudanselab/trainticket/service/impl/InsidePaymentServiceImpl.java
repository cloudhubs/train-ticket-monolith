package edu.fudanselab.trainticket.service.impl;

import edu.fudanselab.trainticket.entity.OrderStatus;
import edu.fudanselab.trainticket.repository.InsidePaymentAddMoneyRepository;
import edu.fudanselab.trainticket.repository.InsidePaymentRepository;
import edu.fudanselab.trainticket.service.InsidePaymentService;
import edu.fudanselab.trainticket.service.ServiceResolver;
import edu.fudanselab.trainticket.util.Response;
import edu.fudanselab.trainticket.entity.PaymentInfo;
import edu.fudanselab.trainticket.entity.InsidePayment;
import edu.fudanselab.trainticket.entity.InsidePaymentMoney;
import edu.fudanselab.trainticket.entity.Payment;
import edu.fudanselab.trainticket.entity.Order;
import edu.fudanselab.trainticket.entity.PaymentType;
import edu.fudanselab.trainticket.entity.AccountInfo;
import edu.fudanselab.trainticket.entity.MoneyType;
import edu.fudanselab.trainticket.entity.Balance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author fdse
 */
@Service
public class InsidePaymentServiceImpl implements InsidePaymentService {

    @Autowired
    public InsidePaymentAddMoneyRepository addMoneyRepository;

    @Autowired
    public InsidePaymentRepository paymentRepository;

    @Autowired
    public RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(InsidePaymentServiceImpl.class);

    @Autowired
    private ServiceResolver serviceResolver;

    @Override
    public Response pay(PaymentInfo info, HttpHeaders headers) {

        String userId = info.getUserId();

        String requestOrderURL = "";
        String order_service_url = serviceResolver.getServiceUrl("ts-order-service");
        String order_other_service_url = serviceResolver.getServiceUrl("ts-order-other-service");
        if (info.getTripId().startsWith("G") || info.getTripId().startsWith("D")) {
            requestOrderURL =  order_service_url + "/api/v1/orderservice/order/" + info.getOrderId();
        } else {
            requestOrderURL = order_other_service_url + "/api/v1/orderOtherService/orderOther/" + info.getOrderId();
        }
        HttpEntity requestGetOrderResults = new HttpEntity(headers);
        ResponseEntity<Response<Order>> reGetOrderResults = restTemplate.exchange(
                requestOrderURL,
                HttpMethod.GET,
                requestGetOrderResults,
                new ParameterizedTypeReference<Response<Order>>() {
                });
        Response<Order> result = reGetOrderResults.getBody();


        if (result.getStatus() == 1) {
            Order order = result.getData();
            if (order.getStatus() != OrderStatus.NOTPAID.getCode()) {
                InsidePaymentServiceImpl.LOGGER.warn("[Inside Payment Service.pay][Order status Not allowed to Pay]");
                return new Response<>(0, "Error. Order status Not allowed to Pay.", null);
            }

            InsidePayment payment = new InsidePayment();
            payment.setOrderId(info.getOrderId());
            payment.setPrice(order.getPrice());
            payment.setUserId(userId);

            //判断一下账户余额够不够，不够要去站外支付
            List<InsidePayment> payments = paymentRepository.findByUserId(userId);
            List<InsidePaymentMoney> addMonies = addMoneyRepository.findByUserId(userId);
            Iterator<InsidePayment> paymentsIterator = payments.iterator();
            Iterator<InsidePaymentMoney> addMoniesIterator = addMonies.iterator();

            BigDecimal totalExpand = new BigDecimal("0");
            while (paymentsIterator.hasNext()) {
                InsidePayment p = paymentsIterator.next();
                totalExpand = totalExpand.add(new BigDecimal(p.getPrice()));
            }
            totalExpand = totalExpand.add(new BigDecimal(order.getPrice()));

            BigDecimal money = new BigDecimal("0");
            while (addMoniesIterator.hasNext()) {
                InsidePaymentMoney addMoney = addMoniesIterator.next();
                money = money.add(new BigDecimal(addMoney.getMoney()));
            }

            if (totalExpand.compareTo(money) > 0) {
                //站外支付
                Payment outsidePaymentInfo = new Payment();
                outsidePaymentInfo.setOrderId(info.getOrderId());
                outsidePaymentInfo.setUserId(userId);
                outsidePaymentInfo.setPrice(order.getPrice());

                /****这里调用第三方支付***/

                HttpEntity requestEntityOutsidePaySuccess = new HttpEntity(outsidePaymentInfo, headers);
                String payment_service_url = serviceResolver.getServiceUrl("ts-payment-service");
                ResponseEntity<Response> reOutsidePaySuccess = restTemplate.exchange(
                        payment_service_url + "/api/v1/paymentservice/payment",
                        HttpMethod.POST,
                        requestEntityOutsidePaySuccess,
                        Response.class);
                Response outsidePaySuccess = reOutsidePaySuccess.getBody();

                InsidePaymentServiceImpl.LOGGER.info("[Inside Payment Service.pay][outside Pay][Out pay result: {}]", outsidePaySuccess.toString());
                if (outsidePaySuccess.getStatus() == 1) {
                    payment.setType(PaymentType.O);
                    paymentRepository.save(payment);
                    setOrderStatus(info.getTripId(), info.getOrderId(), headers);
                    return new Response<>(1, "Payment Success " +    outsidePaySuccess.getMsg(), null);
                } else {
                    LOGGER.error("Payment failed: {}", outsidePaySuccess.getMsg());
                    return new Response<>(0, "Payment Failed:  " +  outsidePaySuccess.getMsg(), null);
                }
            } else {
                setOrderStatus(info.getTripId(), info.getOrderId(), headers);
                payment.setType(PaymentType.P);
                paymentRepository.save(payment);
            }
            LOGGER.info("[Inside Payment Service.pay][Payment success][orderId: {}]", info.getOrderId());
            return new Response<>(1, "Payment Success", null);

        } else {
            LOGGER.error("[Inside Payment Service.pay][Payment failed][Order not exists][orderId: {}]", info.getOrderId());
            return new Response<>(0, "Payment Failed, Order Not Exists", null);
        }
    }

    @Override
    public Response createAccount(AccountInfo info, HttpHeaders headers) {
        List<InsidePaymentMoney> list = addMoneyRepository.findByUserId(info.getUserId());
        if (list.isEmpty()) {
            InsidePaymentMoney addMoney = new InsidePaymentMoney();
            addMoney.setMoney(info.getMoney());
            addMoney.setUserId(info.getUserId());
            addMoney.setType(MoneyType.A);
            addMoneyRepository.save(addMoney);
            return new Response<>(1, "Create Account Success", null);
        } else {
            LOGGER.error("[createAccount][Create Account Failed][Account already Exists][userId: {}]", info.getUserId());
            return new Response<>(0, "Create Account Failed, Account already Exists", null);
        }
    }

    @Override
    public Response addMoney(String userId, String money, HttpHeaders headers) {
        if (addMoneyRepository.findByUserId(userId) != null) {
            InsidePaymentMoney addMoney = new InsidePaymentMoney();
            addMoney.setUserId(userId);
            addMoney.setMoney(money);
            addMoney.setType(MoneyType.A);
            addMoneyRepository.save(addMoney);
            return new Response<>(1, "Add Money Success", null);
        } else {
            LOGGER.error("Add Money Failed, userId: {}", userId);
            return new Response<>(0, "Add Money Failed", null);
        }
    }

    @Override
    public Response queryAccount(HttpHeaders headers) {
        List<Balance> result = new ArrayList<>();
        List<InsidePaymentMoney> list = addMoneyRepository.findAll();
        Iterator<InsidePaymentMoney> ite = list.iterator();
        HashMap<String, String> map = new HashMap<>();
        while (ite.hasNext()) {
            InsidePaymentMoney addMoney = ite.next();
            if (map.containsKey(addMoney.getUserId())) {
                BigDecimal money = new BigDecimal(map.get(addMoney.getUserId()));
                map.put(addMoney.getUserId(), money.add(new BigDecimal(addMoney.getMoney())).toString());
            } else {
                map.put(addMoney.getUserId(), addMoney.getMoney());
            }
        }

        Iterator ite1 = map.entrySet().iterator();
        while (ite1.hasNext()) {
            Map.Entry entry = (Map.Entry) ite1.next();
            String userId = (String) entry.getKey();
            String money = (String) entry.getValue();

            List<InsidePayment> payments = paymentRepository.findByUserId(userId);
            Iterator<InsidePayment> iterator = payments.iterator();
            String totalExpand = "0";
            while (iterator.hasNext()) {
                InsidePayment p = iterator.next();
                BigDecimal expand = new BigDecimal(totalExpand);
                totalExpand = expand.add(new BigDecimal(p.getPrice())).toString();
            }
            String balanceMoney = new BigDecimal(money).subtract(new BigDecimal(totalExpand)).toString();
            Balance balance = new Balance();
            balance.setUserId(userId);
            balance.setBalance(balanceMoney);
            result.add(balance);
        }

        return new Response<>(1, "Success", result);
    }

    public String queryAccount(String userId, HttpHeaders headers) {
        List<InsidePayment> payments = paymentRepository.findByUserId(userId);
        List<InsidePaymentMoney> addMonies = addMoneyRepository.findByUserId(userId);
        Iterator<InsidePayment> paymentsIterator = payments.iterator();
        Iterator<InsidePaymentMoney> addMoniesIterator = addMonies.iterator();

        BigDecimal totalExpand = new BigDecimal("0");
        while (paymentsIterator.hasNext()) {
            InsidePayment p = paymentsIterator.next();
            totalExpand.add(new BigDecimal(p.getPrice()));
        }

        BigDecimal money = new BigDecimal("0");
        while (addMoniesIterator.hasNext()) {
            InsidePaymentMoney addMoney = addMoniesIterator.next();
            money.add(new BigDecimal(addMoney.getMoney()));
        }

        return money.subtract(totalExpand).toString();
    }

    @Override
    public Response queryPayment(HttpHeaders headers) {
        List<InsidePayment> payments = paymentRepository.findAll();
        if (payments != null && !payments.isEmpty()) {
            return new Response<>(1, "Query Payment Success", payments);
        }else {
            LOGGER.error("[queryPayment][Query payment failed][payment is null]");
            return new Response<>(0, "Query Payment Failed", null);
        }
    }

    @Override
    public Response drawBack(String userId, String money, HttpHeaders headers) {
        if (addMoneyRepository.findByUserId(userId) != null) {
            InsidePaymentMoney addMoney = new InsidePaymentMoney();
            addMoney.setUserId(userId);
            addMoney.setMoney(money);
            addMoney.setType(MoneyType.D);
            addMoneyRepository.save(addMoney);
            return new Response<>(1, "Draw Back Money Success", null);
        } else {
            LOGGER.error("[drawBack][Draw Back Money Failed][addMoneyRepository.findByUserId null][userId: {}]", userId);
            return new Response<>(0, "Draw Back Money Failed", null);
        }
    }

    @Override
    public Response payDifference(PaymentInfo info, HttpHeaders headers) {

        String userId = info.getUserId();

        InsidePayment payment = new InsidePayment();
        payment.setOrderId(info.getOrderId());
        payment.setPrice(info.getPrice());
        payment.setUserId(info.getUserId());


        List<InsidePayment> payments = paymentRepository.findByUserId(userId);
        List<InsidePaymentMoney> addMonies = addMoneyRepository.findByUserId(userId);
        Iterator<InsidePayment> paymentsIterator = payments.iterator();
        Iterator<InsidePaymentMoney> addMoniesIterator = addMonies.iterator();

        BigDecimal totalExpand = new BigDecimal("0");
        while (paymentsIterator.hasNext()) {
            InsidePayment p = paymentsIterator.next();
            totalExpand.add(new BigDecimal(p.getPrice()));
        }
        totalExpand.add(new BigDecimal(info.getPrice()));

        BigDecimal money = new BigDecimal("0");
        while (addMoniesIterator.hasNext()) {
            InsidePaymentMoney addMoney = addMoniesIterator.next();
            money.add(new BigDecimal(addMoney.getMoney()));
        }

        if (totalExpand.compareTo(money) > 0) {
            //站外支付
            Payment outsidePaymentInfo = new Payment();
            outsidePaymentInfo.setOrderId(info.getOrderId());
            outsidePaymentInfo.setUserId(userId);
            outsidePaymentInfo.setPrice(info.getPrice());

            HttpEntity requestEntityOutsidePaySuccess = new HttpEntity(outsidePaymentInfo, headers);
            String payment_service_url = serviceResolver.getServiceUrl("ts-payment-service");
            ResponseEntity<Response> reOutsidePaySuccess = restTemplate.exchange(
                    payment_service_url + "/api/v1/paymentservice/payment",
                    HttpMethod.POST,
                    requestEntityOutsidePaySuccess,
                    Response.class);
            Response outsidePaySuccess = reOutsidePaySuccess.getBody();

            if (outsidePaySuccess.getStatus() == 1) {
                payment.setType(PaymentType.E);
                paymentRepository.save(payment);
                return new Response<>(1, "Pay Difference Success", null);
            } else {
                LOGGER.error("[payDifference][Pay Difference Failed][outsidePaySuccess status not 1][orderId: {}]", info.getOrderId());
                return new Response<>(0, "Pay Difference Failed", null);
            }
        } else {
            payment.setType(PaymentType.E);
            paymentRepository.save(payment);
        }
        return new Response<>(1, "Pay Difference Success", null);
    }

    @Override
    public Response queryAddMoney(HttpHeaders headers) {
        List<InsidePaymentMoney> monies = addMoneyRepository.findAll();
        if (monies != null && !monies.isEmpty()) {
            return new Response<>(1, "Query Money Success", null);
        } else {
            LOGGER.error("[queryAddMoney][Query money failed][addMoneyRepository.findAll null]");
            return new Response<>(0, "Query money failed", null);
        }
    }

    private Response setOrderStatus(String tripId, String orderId, HttpHeaders headers) {

        //order paid and not collected
        int orderStatus = 1;
        Response result;
        if (tripId.startsWith("G") || tripId.startsWith("D")) {

            HttpEntity requestEntityModifyOrderStatusResult = new HttpEntity(headers);
            String order_service_url = serviceResolver.getServiceUrl("ts-order-service");
            ResponseEntity<Response> reModifyOrderStatusResult = restTemplate.exchange(
                    order_service_url + "/api/v1/orderservice/order/status/" + orderId + "/" + orderStatus,
                    HttpMethod.GET,
                    requestEntityModifyOrderStatusResult,
                    Response.class);
            result = reModifyOrderStatusResult.getBody();

        } else {
            HttpEntity requestEntityModifyOrderStatusResult = new HttpEntity(headers);
            String order_other_service_url = serviceResolver.getServiceUrl("ts-order-other-service");
            ResponseEntity<Response> reModifyOrderStatusResult = restTemplate.exchange(
                    order_other_service_url + "/api/v1/orderOtherService/orderOther/status/" + orderId + "/" + orderStatus,
                    HttpMethod.GET,
                    requestEntityModifyOrderStatusResult,
                    Response.class);
            result = reModifyOrderStatusResult.getBody();

        }
        return result;
    }

    @Override
    public void initPayment(InsidePayment payment, HttpHeaders headers) {
        Optional<InsidePayment> paymentTemp = paymentRepository.findById(payment.getId());
        if (paymentTemp == null) {
            paymentRepository.save(payment);
        } else {
            InsidePaymentServiceImpl.LOGGER.error("[initPayment][paymentTemp Already Exists][paymentId: {}, orderId: {}]", payment.getId(), payment.getOrderId());
        }
    }

}
