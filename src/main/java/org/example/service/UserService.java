package org.example.service;

import io.restassured.response.Response;
import org.example.service.uritemplate.UriTemplate;

public class UserService extends CommonService {

    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public Response getRequest(UriTemplate uri, String username) {
        return super.getRequest(uri.getUri(username));
    }

    public Response getRequest(UriTemplate uri, String username, String password) {
        return super.getRequest(uri.getUri(username, password));
    }

    public Response postRequest(UriTemplate uri, Object body) {
        return super.postRequest(uri.getUri(), body);
    }

    public void deleteRequest(UriTemplate uri, String username) {
        super.deleteRequest(uri.getUri(username));
    }
}
