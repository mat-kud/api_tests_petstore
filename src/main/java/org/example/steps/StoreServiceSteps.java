package org.example.steps;

import io.restassured.response.Response;
import org.example.entities.Order;
import org.example.service.StoreService;

import static org.example.service.uritemplate.StoreServiceUri.ORDER;
import static org.example.service.uritemplate.StoreServiceUri.ORDER_BY_ID;

public class StoreServiceSteps {
    private static final StoreService STORE_SERVICE = StoreService.getInstance();

    public static Response getOrderById(long orderId) {
        return STORE_SERVICE.getRequest(ORDER_BY_ID, orderId);
    }

    public static Response createOrder(Order order) {
        return STORE_SERVICE.postRequest(ORDER, order);
    }

    public static void deleteOrderById(long orderId) {
        STORE_SERVICE.deleteRequest(ORDER_BY_ID, orderId);
    }
}
