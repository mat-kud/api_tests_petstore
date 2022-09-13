package org.example.steps;

import io.restassured.response.Response;
import org.example.entities.Pet;
import org.example.service.StoreService;

import static org.example.service.uritemplate.PetServiceUri.PET;
import static org.example.service.uritemplate.PetServiceUri.PET_BY_ID;


public class PetServiceSteps {
    private static final StoreService PET_SERVICE = StoreService.getInstance();

    public static Response getPetById(int petId) {
        return PET_SERVICE.getRequest(PET_BY_ID, petId);
    }

    public static Response createPet(Pet pet) {
        return PET_SERVICE.postRequest(PET, pet);
    }

    public static void deletePetById(int petId) {
        PET_SERVICE.deleteRequest(PET_BY_ID, petId);
    }


}
