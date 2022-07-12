package adminuser.controller;

import adminuser.dto.UserDto;
import adminuser.entity.User;
import adminuser.service.AdminUserService;
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
@RequestMapping("/api/v1/adminuserservice/users")
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;
    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminUser Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    @ApiResponses({
            @ApiResponse(code = 0, message = "get all users error"),
            @ApiResponse(code = 1, message = "Success",response = User.class,responseContainer = "List"),
            @ApiResponse(code = 0, message = "NO User")
    })
    public HttpEntity getAllUsers(@RequestHeader HttpHeaders headers) {
        logger.info("Get all user");
        return ok(adminUserService.getAllUsers(headers));
    }

    @PutMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto", value = "UserDto",dataType = "UserDto", paramType = "body",required = true),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "Update user error"),
            @ApiResponse(code = 1, message = "SAVE USER SUCCESS",response = User.class),
            @ApiResponse(code = 0, message = "USER NOT EXISTS")
    })
    public HttpEntity updateUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        logger.info("Update User, userName: {}", userDto.getUserName());
        return ok(adminUserService.updateUser(userDto, headers));
    }


    @PostMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto", value = "UserDto",dataType = "UserDto", paramType = "body",required = true),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "Add user error"),
            @ApiResponse(code = 1, message = "REGISTER USER SUCCESS",response = User.class),
            @ApiResponse(code = 0, message = "USER HAS ALREADY EXISTS")
    })
    public HttpEntity addUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        logger.info("Add user, userName: {}", userDto.getUserName());
        return ok(adminUserService.addUser(userDto, headers));
    }

    @DeleteMapping(value = "/{userId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "userId",dataType = "String", paramType = "path",required = true,defaultValue = "471e1746-66e1-4c46-9e99-eccff12bd3b4"),
            @ApiImplicitParam(name = "headers",  paramType = "header",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "delete user error"),
            @ApiResponse(code = 1, message = "DELETE SUCCESS"),
            @ApiResponse(code = 0, message = "USER NOT EXISTS")
    })
    public HttpEntity deleteUser(@PathVariable String userId, @RequestHeader HttpHeaders headers) {
        logger.info("Delete user, userId: {}", userId);
        return ok(adminUserService.deleteUser(userId, headers));
    }

}
