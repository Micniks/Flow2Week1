/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Bruger
 */
@Entity
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double totalPrice = 0;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<OrderLine> orderLines = new ArrayList();

    public Order(Customer customer) {
        this.customer = customer;
    }

    public Order(Customer customer, double totalPrice) {
        this.customer = customer;
        this.totalPrice = totalPrice;
    }

    public Order() {
    }

    public double getTotalPrice() {
        calculateTotalPrice();
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void addOrderLine(OrderLine orderLine) {
        if (!this.orderLines.contains(orderLine)) {
            this.orderLines.add(orderLine);
        }
        calculateTotalPrice();
    }

    public Double calculateTotalPrice() {
        double tPrice = 0;
        for (OrderLine orderLine : this.orderLines) {
            tPrice += orderLine.getItemType().getPrice() * orderLine.getQuantity();
        }
        return tPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
