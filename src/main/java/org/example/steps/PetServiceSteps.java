package org.example.steps;

import io.restassured.response.Response;
import org.example.entities.Pet;
import org.example.entities.enums.PetStatus;
import org.example.service.PetService;


import static org.example.service.uritemplate.PetServiceUri.*;


public class PetServiceSteps {
    private static final PetService PET_SERVICE = PetService.getInstance();

    public static Response getPetById(long petId) {
        return PET_SERVICE.getRequest(PET_BY_ID, petId);
    }

    public static Response getPetsByPetStatus(PetStatus petStatus){
        return PET_SERVICE.getRequest(PETS_BY_STATUS, petStatus);
    }

    public static Response createPet(Pet pet) {
        return PET_SERVICE.postRequest(PET, pet);
    }

    public static void deletePetById(long petId) {
        PET_SERVICE.deleteRequest(PET_BY_ID, petId);
    }


}
