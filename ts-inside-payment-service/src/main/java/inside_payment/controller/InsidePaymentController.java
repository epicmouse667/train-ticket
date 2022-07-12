package inside_payment.controller;

import inside_payment.entity.*;
import inside_payment.service.InsidePaymentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/inside_pay_service")
public class InsidePaymentController {

    @Autowired
    public InsidePaymentService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(InsidePaymentController.class);

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ InsidePayment Service ] !";
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "info", value = "PaymentInfo",dataType = "PaymentInfo", paramType = "body",required = true),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "Error. Order status Not allowed to Pay."),
            @ApiResponse(code = 1, message = "Payment Success")
    })
    @PostMapping(value = "/inside_payment")
    public HttpEntity pay(@RequestBody PaymentInfo info, @RequestHeader HttpHeaders headers) {
        InsidePaymentController.LOGGER.info("[Inside Payment Service][Pay] Pay for: {}", info.getOrderId());
        return ok(service.pay(info, headers));
    }

//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "info", value = "AccountInfo",dataType = "AccountInfo", paramType = "body",required = true),
//            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
//    })
    @PostMapping(value = "/inside_payment/account")
    public HttpEntity createAccount(@RequestBody AccountInfo info, @RequestHeader HttpHeaders headers) {
        LOGGER.info("Create account, accountInfo: {}", info);
        return ok(service.createAccount(info, headers));
    }

    @GetMapping(value = "/inside_payment/{userId}/{money}")
    public HttpEntity addMoney(@PathVariable String userId, @PathVariable
            String money, @RequestHeader HttpHeaders headers) {
        LOGGER.info("add money, userId: {}, money: {}", userId, money);
        return ok(service.addMoney(userId, money, headers));
    }

    @GetMapping(value = "/inside_payment/payment")
    public HttpEntity queryPayment(@RequestHeader HttpHeaders headers) {
        LOGGER.info("query payment");
        return ok(service.queryPayment(headers));
    }

    @GetMapping(value = "/inside_payment/account")
    public HttpEntity queryAccount(@RequestHeader HttpHeaders headers) {
        LOGGER.info("query account");
        return ok(service.queryAccount(headers));
    }

    @GetMapping(value = "/inside_payment/drawback/{userId}/{money}")
    public HttpEntity drawBack(@PathVariable String userId, @PathVariable String money, @RequestHeader HttpHeaders headers) {
        LOGGER.info("draw back payment, userId: {}, money: {}", userId, money);
        return ok(service.drawBack(userId, money, headers));
    }

    @PostMapping(value = "/inside_payment/difference")
    public HttpEntity payDifference(@RequestBody PaymentInfo info, @RequestHeader HttpHeaders headers) {
        LOGGER.info("pay difference");
        return ok(service.payDifference(info, headers));
    }

    @GetMapping(value = "/inside_payment/money")
    public HttpEntity queryAddMoney(@RequestHeader HttpHeaders headers) {
        LOGGER.info("query add money");
        return ok(service.queryAddMoney(headers));
    }

}
