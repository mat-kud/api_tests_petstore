package org.example.usertest;

import io.restassured.response.Response;
import org.example.entities.Category;
import org.example.entities.Pet;
import org.example.entities.Tag;
import org.example.entities.enums.PetStatus;
import org.example.steps.PetServiceSteps;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class PetServiceTest extends BaseTest {

    @Test
    public void getPetByPetIdTest() {
        Pet expectedPet = createPet();
        PetServiceSteps.createPet(expectedPet);
        Pet createdPet = PetServiceSteps.getPetById(expectedPet.getId()).as(Pet.class);
        softAssert.assertEquals(createdPet.getId(), expectedPet.getId(),
                "Incorrect pet id");
        softAssert.assertEquals(createdPet.getName(), expectedPet.getName(),
                "Incorrect pet name");
        softAssert.assertAll();
    }

    @DataProvider(name = "petStatus")
    public Object[][] petStatus() {
        return new Object[][]{
                {PetStatus.AVAILABLE},
                {PetStatus.PENDING},
                {PetStatus.SOLD}
        };
    }

    @Test(dataProvider = "petStatus")
    public void getPetsByPetStatusTest(PetStatus petStatus){
        List<Pet> pets = PetServiceSteps.getPetsByPetStatus(petStatus)
                .jsonPath()
                .getList("", Pet.class);

        boolean isEveryPetStatusTheSame = verifyEveryPetStatus(pets, petStatus);
        Assert.assertTrue(isEveryPetStatusTheSame,
                "Not every pet status is " + petStatus.nameLowerCase());
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

    private boolean verifyEveryPetStatus(List<Pet> pets, PetStatus petStatus){
        return pets.stream().allMatch(pet -> pet.getStatus().equals(petStatus.nameLowerCase()));
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
                .setStatus(PetStatus.AVAILABLE
                        .nameLowerCase());
    }
}
