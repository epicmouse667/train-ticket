package adminorder.controller;

import adminorder.entity.*;
import adminorder.service.AdminOrderService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping("/api/v1/adminorderservice")
public class AdminOrderController {

    @Autowired
    AdminOrderService adminOrderService;

    private static final Logger logger = LoggerFactory.getLogger(AdminOrderController.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [Admin Order Service] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/adminorder")
    @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    public HttpEntity getAllOrders(@RequestHeader HttpHeaders headers) {
        logger.info("Get all order");
        return ok(adminOrderService.getAllOrders(headers));
    }

    @PostMapping(value = "/adminorder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "Order",dataType = "Order", paramType = "body",required = true),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    public HttpEntity addOrder(@RequestBody Order request, @RequestHeader HttpHeaders headers) {
        logger.info("Add new order, AccountID: {}", request.getAccountId());
        return ok(adminOrderService.addOrder(request, headers));
    }

    @PutMapping(value = "/adminorder")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "Order",dataType = "Order", paramType = "body",required = true),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    public HttpEntity updateOrder(@RequestBody Order request, @RequestHeader HttpHeaders headers) {
        logger.info("Update order, AccountID: {}, OrderId: {}", request.getAccountId(), request.getId());
        return ok(adminOrderService.updateOrder(request, headers));
    }

    @DeleteMapping(value = "/adminorder/{orderId}/{trainNumber}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "orderId",dataType = "String", paramType = "path",required = true,defaultValue = "5ad7750b-a68b-49c0-a8c0-32776b067703"),
            @ApiImplicitParam(name = "trainNumber", value = "trainNumber",dataType = "String", paramType = "path",required = true,defaultValue = "G1237"),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    public HttpEntity deleteOrder(@PathVariable String orderId, @PathVariable String trainNumber, @RequestHeader HttpHeaders headers) {
        logger.info("Delete order, OrderId: {}, TrainNumber: {}", orderId, trainNumber);
        return ok(adminOrderService.deleteOrder(orderId, trainNumber, headers));
    }

}
