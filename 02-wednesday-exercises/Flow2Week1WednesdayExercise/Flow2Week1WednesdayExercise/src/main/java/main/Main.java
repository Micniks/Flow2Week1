/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.Customer;
import entities.ItemType;
import entities.Order;
import entities.OrderLine;
import facade.Facade;
import java.util.List;

/**
 *
 * @author Bruger
 */
public class Main {

    public static void main(String[] args) {

        Facade facade = new Facade();

        // Create a Customer
        Customer c1 = facade.createCustomer("Michael", "FakeEmail@Mail.Fake");
        Customer c2 = facade.createCustomer("Allan", "FakestEmail@Mail.Fake");
        Customer c3 = facade.createCustomer("Tobias", "FakerEmail@Mail.Fake");

        // Find a Customer
        Customer foundC = facade.findCustomer(c1.getId());
        System.out.println("We found = " + foundC.getName() + " from " + foundC.getEmail());
        System.out.println(c1.getId() + " = " + foundC.getId());

        // Get all Customers
        List<Customer> customerList = facade.getAllCustomer();
        System.out.println("We have in total:");
        for (Customer customer : customerList) {
            System.out.println(customer.getId() + ": " + customer.getName() + " from " + customer.getEmail());
        }

        // Create an ItemType
        ItemType it1 = facade.createItemType("Milk", "This is milk", 7.0);
        ItemType it2 = facade.createItemType("Ice", "This is an icecream", 19.99);
        ItemType it3 = facade.createItemType("Sandwich", "Eggs and ham", 15.75);

        // Find an ItemType
        ItemType foundIT = facade.findItemType(it1.getId());
        System.out.println(foundIT.getName() + ": " + foundIT.getDescription() + ". Only " + foundIT.getPrice());
        System.out.println(it1.getId() + " = " + foundIT.getId());

        // Create an Order and Add it to a Customer
        Order order1 = facade.createOrder(c1.getId());
        Order order2 = facade.createOrder(c1.getId());
        Order order3 = facade.createOrder(c2.getId());

        // Create an OrderLine for a specific ItemType, and add it to an Order
        OrderLine orderLine1 = facade.createOrderLine(2, it1, order1);
        OrderLine orderLine2 = facade.createOrderLine(3, it2.getId(), order1.getId());
        OrderLine orderLine3 = facade.createOrderLine(1, it3, order2);
        OrderLine orderLine4 = facade.createOrderLine(4, it1.getId(), order3.getId());

        // Find all Orders, for a specific Customer
        List<Order> foundOrder = facade.getCustomerOrders(c1.getId());
        for (Order order : foundOrder) {
            System.out.println(c1.getName() + " has order number " + order.getId() + " with:");
            for (OrderLine orderLine : order.getOrderLines()) {
                System.out.println(orderLine.getQuantity() + " of " + orderLine.getItemType().getName());
            }
            // Find the total price of an order 
            System.out.println("For a total of: " + order.getTotalPrice());
            System.out.println();
        }

    }

}
