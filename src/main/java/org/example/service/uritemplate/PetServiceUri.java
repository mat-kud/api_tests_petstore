package org.example.service.uritemplate;

public class PetServiceUri {
    public static final UriTemplate PET = new UriTemplate("pet");
    public static final UriTemplate PET_BY_ID = new UriTemplate("pet/%s");
    public static final UriTemplate PETS_BY_STATUS = new UriTemplate("pet/findByStatus?status=%s");
}
