package adminroute.controller;

import adminroute.entity.Route;
import adminroute.entity.RouteInfo;
import adminroute.service.AdminRouteService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/adminrouteservice")
public class AdminRouteController {

    @Autowired
    AdminRouteService adminRouteService;

    public static final Logger logger = LoggerFactory.getLogger(AdminRouteController.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminRoute Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/adminroute")
    @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    @ApiResponses({
            @ApiResponse(code = 1, message = "Success",response = Route.class,responseContainer = "ArrayList"),
            @ApiResponse(code = 0, message = "No Content")
    })
    public HttpEntity getAllRoutes(@RequestHeader HttpHeaders headers) {
        logger.info("Get all routes request");
        return ok(adminRouteService.getAllRoutes(headers));
    }

    @PostMapping(value = "/adminroute")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "RouteInfo",dataType = "RouteInfo", paramType = "body",required = true),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "Station Number Not Equal To Distance Number"),
            @ApiResponse(code = 1, message = "Save Success", response = Route.class),
            @ApiResponse(code = 1, message = "Modify success", response = Route.class)
    })
    public HttpEntity addRoute(@RequestBody RouteInfo request, @RequestHeader HttpHeaders headers) {
        logger.info("Create and modify route, route id: {}, from station {} to station {}",
                request.getId(), request.getStartStation(), request.getEndStation());
        return ok(adminRouteService.createAndModifyRoute(request, headers));
    }

    @DeleteMapping(value = "/adminroute/{routeId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "routeId", value = "routeId",dataType = "String", paramType = "path",required = true,defaultValue = "0b23bd3e-876a-4af3-b920-c50a90c90b04"),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 1, message = "Delete Success", response = String.class),
            @ApiResponse(code = 0, message = "Delete failed, Reason unKnown with this routeId", response = String.class)
    })
    public HttpEntity deleteRoute(@PathVariable String routeId, @RequestHeader HttpHeaders headers) {
        logger.info("Delete route, route id: {}", routeId);
        return ok(adminRouteService.deleteRoute(routeId, headers));
    }


}
