package org.example.steps;

import io.restassured.response.Response;
import org.example.entities.User;
import org.example.service.UserService;

import static org.example.service.uritemplate.UserServiceUri.*;

public class UserServiceSteps {
    private static final UserService USER_SERVICE = UserService.getInstance();


    public static Response getUserByUsername(String username) {
        return USER_SERVICE.getRequest(USER_BY_USERNAME, username);
    }

    public static Response createUser(User user) {
        return USER_SERVICE.postRequest(USER, user);
    }

    public static void deleteUserByUsername(String username) {
        USER_SERVICE.deleteRequest(USER_BY_USERNAME, username);
    }
}
