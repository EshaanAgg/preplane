package com.preplane.dev.controllers.backend;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.razorpay.*;

@RestController
public class PaymentController {

    @Value("${razorpay.keyId}")
    private String apiKey;

    @Value("${razorpay.keySecret}")
    private String apiSecret;

    @GetMapping("/payment/createOrderId/{amount}")
    @ResponseBody
    public String createPaymentOrderId(@PathVariable String amount, Model model) {
        String orderId=null;
        try {
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "orderReceiptID");

            Order order = razorpay.orders.create(orderRequest);
            orderId = order.get("id");
        } catch (RazorpayException e) {
            System.out.println(e.getMessage());
            handleRazorpayException(e, model);
            return "error/paymentError";
        }
        return orderId;
    }

    @ExceptionHandler(RazorpayException.class)
    public String handleRazorpayException(RazorpayException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/paymentError"; // Redirect to error page
    }

}