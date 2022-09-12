package org.example.usertest;

import io.restassured.response.Response;
import org.example.entities.User;
import org.example.steps.UserServiceSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class UserServiceTest {

    @Test
    public void getUserByUsernameTest(){
        User expectedUser = createUser();
        UserServiceSteps.createUser(expectedUser);
        User createdUser = UserServiceSteps.getUserByUsername(expectedUser.getUsername()).as(User.class);
        Assert.assertEquals(createdUser.getId(), expectedUser.getId(),
                "Incorrect user id");
        Assert.assertEquals(createdUser.getUsername(), expectedUser.getUsername(),
                "Incorrect username");
    }

    @Test
    public void createUserTest(){
        User expectedUser = createUser();
        User createdUser = UserServiceSteps.createUser(expectedUser).as(User.class);
        Assert.assertEquals(createdUser.getFirstName(), expectedUser.getFirstName(),
                "Incorrect first name");
    }

    @Test
    public void deleteUserTest(){
        User user = createUser();
        UserServiceSteps.createUser(user);
        UserServiceSteps.deleteUserByUsername(user.getUsername());
        Response deletedUserResponse = UserServiceSteps.getUserByUsername(user.getUsername());
        Assert.assertEquals(deletedUserResponse.getStatusCode(), 404,
                "User not deleted or invalid username");
    }

    @Test
    public void loginTest(){
        User user = createUser();
        Response loginResponse = UserServiceSteps.loginUser(user.getUsername(), user.getPassword());
        Assert.assertEquals(loginResponse.getStatusCode(), 200,
                "Unsuccessful login attempt");
    }




    private User createUser() {
        Random random = new Random();
        return new User()
                .setId(random.nextInt(100))
                .setUsername("user" + random.nextInt(1000))
                .setFirstName("John" + random.nextInt(1000))
                .setLastName("Smith")
                .setEmail("testEmail" + random.nextInt(100) + "@gmail.com")
                .setPassword("Kentucky11")
                .setPhone("8976500" + (random.nextInt(90) + 10))
                .setUserStatus(1);
    }
}
