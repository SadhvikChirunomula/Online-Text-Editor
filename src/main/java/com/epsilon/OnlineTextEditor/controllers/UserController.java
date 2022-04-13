package com.epsilon.OnlineTextEditor.controllers;

import com.epsilon.OnlineTextEditor.models.request.LoginUserRequest;
import com.epsilon.OnlineTextEditor.models.request.RegisterUserRequest;
import com.epsilon.OnlineTextEditor.models.response.LoginUserResponse;
import com.epsilon.OnlineTextEditor.models.response.RegisterUserResponse;
import com.epsilon.OnlineTextEditor.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(value = "User Controller", description = "User API's related to Text Editor")
public class UserController {
    UserService userService = new UserService();

    @RequestMapping(method = RequestMethod.POST, value = "/user/register")
    public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
        return userService.registerUser(registerUserRequest);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/login")
    public LoginUserResponse loginUser(@RequestBody LoginUserRequest loginUserRequest) throws Exception {
        return userService.loginUser(loginUserRequest);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/users")
    public List<Map<String, Object>> listUsers() throws Exception {
        return userService.listUsers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list/user")
    public List<Map<String, Object>> listUser(@RequestParam int userId) throws Exception {
        return userService.listUser(userId);
    }
}
