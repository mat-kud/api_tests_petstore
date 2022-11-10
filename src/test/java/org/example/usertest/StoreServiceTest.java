package org.example.usertest;

import io.restassured.response.Response;
import org.example.entities.Order;
import org.example.entities.enums.OrderStatus;
import org.example.steps.StoreServiceSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class StoreServiceTest extends BaseTest {

    @Test
    public void getOrderByOrderIdTest() {
        Order expectedOrder = createOrder();
        StoreServiceSteps.createOrder(expectedOrder);
        Order createdOrder = StoreServiceSteps.getOrderById(expectedOrder.getId()).as(Order.class);
        softAssert.assertEquals(createdOrder.getId(), expectedOrder.getId(),
                "Incorrect order id");
        softAssert.assertEquals(createdOrder.getPetId(), expectedOrder.getPetId(),
                "Incorrect pet id");
        softAssert.assertAll();
    }

    @Test
    public void createOrderTest() {
        Order expectedOrder = createOrder();
        Order createdOrder = StoreServiceSteps.createOrder(expectedOrder).as(Order.class);
        Assert.assertEquals(createdOrder.getPetId(), expectedOrder.getPetId(),
                "Incorrect pet id");
    }

    @Test
    public void deleteOrderByOrderIdTest() {
        Order order = createOrder();
        StoreServiceSteps.createOrder(order);
        StoreServiceSteps.deleteOrderById(order.getId());
        Response deletedOrderResponse = StoreServiceSteps.getOrderById(order.getId());
        Assert.assertEquals(deletedOrderResponse.getStatusCode(), 404,
                "Order not deleted");
    }

    private Order createOrder() {
        Random random = new Random();
        return new Order()
                .setId(random.nextInt(100))
                .setPetId(random.nextInt(100))
                .setQuantity(1)
                .setShipDate("2022-09-13T10:01:59.303Z")
                .setStatus(OrderStatus.PLACED.nameLowerCase())
                .setComplete(false);
    }
}
