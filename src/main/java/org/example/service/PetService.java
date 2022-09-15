package org.example.service;

import io.restassured.response.Response;
import org.example.entities.enums.PetStatus;
import org.example.service.uritemplate.UriTemplate;

public class PetService extends CommonService {
    private static PetService instance;

    public static PetService getInstance() {
        if (instance == null) {
            instance = new PetService();
        }
        return instance;
    }

    public Response getRequest(UriTemplate uri, long petId) {
        return super.getRequest(uri.getUri(petId));
    }
    public Response getRequest(UriTemplate uri, PetStatus petStatus) {
        return super.getRequest(uri.getUri(petStatus.nameLowerCase()));
    }

    public Response postRequest(UriTemplate uri, Object body) {
        return super.postRequest(uri.getUri(), body);
    }

    public void deleteRequest(UriTemplate uri, long petId) {
        super.deleteRequest(uri.getUri(petId));
    }
}
