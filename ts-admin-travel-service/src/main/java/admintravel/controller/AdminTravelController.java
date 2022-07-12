package admintravel.controller;

import admintravel.entity.AdminTrip;
import admintravel.entity.TravelInfo;
import admintravel.service.AdminTravelService;
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

import java.util.ArrayList;

import static org.springframework.http.ResponseEntity.*;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/admintravelservice")
public class AdminTravelController {
    @Autowired
    AdminTravelService adminTravelService;

    private static final Logger logger = LoggerFactory.getLogger(AdminTravelController.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminTravel Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/admintravel")
    @ApiResponses({
            @ApiResponse(code = 1, message = "success",response = AdminTrip.class,responseContainer = "ArrayList"),
            @ApiResponse(code = 0, message = "No Content")
    })
    public HttpEntity getAllTravels(@RequestHeader HttpHeaders headers) {
        logger.info("Get all travels");
        return ok(adminTravelService.getAllTravels(headers));
    }

    @PostMapping(value = "/admintravel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "TravelInfo",dataType = "TravelInfo", paramType = "body",required = true),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 1, message = "[Admin add new travel]"),
            @ApiResponse(code = 0, message = "Admin add new travel failed")
    })
    public HttpEntity addTravel(@RequestBody TravelInfo request, @RequestHeader HttpHeaders headers) {
        logger.info("Add travel, trip id: {}, train type id: {}, form station {} to station {}, login id: {}",
                request.getTripId(), request.getTrainTypeId(), request.getStartingStationId(), request.getStationsId(), request.getLoginId());
        return ok(adminTravelService.addTravel(request, headers));
    }

    @PutMapping(value = "/admintravel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "TravelInfo",dataType = "TravelInfo", paramType = "body",required = true),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "Admin update travel failed"),
            @ApiResponse(code = 1, message = "Create trip."),
            @ApiResponse(code = 1, message = "Trip already exists")
    })
    public HttpEntity updateTravel(@RequestBody TravelInfo request, @RequestHeader HttpHeaders headers) {
        logger.info("Update travel, trip id: {}, train type id: {}, form station {} to station {}, login id: {}",
                request.getTripId(), request.getTrainTypeId(), request.getStartingStationId(), request.getStationsId(), request.getLoginId());
        return ok(adminTravelService.updateTravel(request, headers));
    }

    @DeleteMapping(value = "/admintravel/{tripId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tripId", value = "tripId",dataType = "String", paramType = "path",required = true,defaultValue = "G1234"),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "Admin delete travel failed"),
            @ApiResponse(code = 1, message = "Delete trip.",response = String.class),
            @ApiResponse(code = 0, message = "Trip doesn't exist.",response = String.class)
    })
    public HttpEntity deleteTravel(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
        logger.info("Delete travel: trip id: {}", tripId);
        return ok(adminTravelService.deleteTravel(tripId, headers));
    }

}
