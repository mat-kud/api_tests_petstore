package org.example.usertest;

import io.restassured.response.Response;
import org.example.entities.Category;
import org.example.entities.Pet;
import org.example.entities.Tag;
import org.example.entities.enums.PetStatus;
import org.example.steps.PetServiceSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class PetServiceTest {

    @Test
    public void getPetByPetIdTest() {
        Pet expectedPet = createPet();
        PetServiceSteps.createPet(expectedPet);
        Pet createdPet = PetServiceSteps.getPetById(expectedPet.getId()).as(Pet.class);
        Assert.assertEquals(createdPet.getId(), expectedPet.getId(),
                "Incorrect pet id");
        Assert.assertEquals(createdPet.getName(), expectedPet.getName(),
                "Incorrect pet name");
    }

    @Test
    public void createPetTest() {
        Pet expectedPet = createPet();
        Pet createdPet = PetServiceSteps.createPet(expectedPet).as(Pet.class);
        Assert.assertEquals(createdPet.getId(), expectedPet.getId(),
                "Incorrect pet id");
    }

    @Test
    public void deletePetByPetIdTest() {
        Pet expectedPet = createPet();
        PetServiceSteps.createPet(expectedPet);
        PetServiceSteps.deletePetById(expectedPet.getId());
        Response deletedPetResponse = PetServiceSteps.getPetById(expectedPet.getId());
        Assert.assertEquals(deletedPetResponse.getStatusCode(), 404,
                "Pet not deleted");
    }


    private Pet createPet() {
        Random random = new Random();
        return new Pet()
                .setId(random.nextInt(100))
                .setName("Frank" + random.nextInt(100))
                .setCategory(new Category()
                        .setId(1)
                        .setName("pug"))
                .setPhotoUrls(new String[]{"url1", "url2"})
                .setTags(new Tag[]{new Tag()
                        .setId(1)
                        .setName("hungry")})
                .setStatus(PetStatus.AVAILABLE);
    }
}
