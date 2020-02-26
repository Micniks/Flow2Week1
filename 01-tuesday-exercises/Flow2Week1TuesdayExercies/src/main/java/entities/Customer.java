/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Bruger
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @ElementCollection
    @CollectionTable(
            name = "HOBBIES",
            joinColumns = @JoinColumn(name = "Customer_ID")
    )
    @Column(name = "HOBBY")
    private List<String> hobbies = new ArrayList();

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyColumn(name = "PHONE")
    @Column(name = "Description")
    private Map<String, String> phones = new HashMap();

//    @OneToOne
//    private Address address;
    
//    @OneToMany
//    @JoinColumn(name = "Customer_ID")
//    private List<Address> addresses = new ArrayList
    
//    @OneToMany(mappedBy = "customer")
//    private List<Address> addresses = new ArrayList();
    
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Address> addresses = new ArrayList();

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer() {
    }

    public void addAddress(Address address) {
        //Extra step for bi-directional
//        address.setCustomer(this);
        
        addresses.add(address);
        address.addCustomer(this);
    }

    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void addHobby(String s) {
        hobbies.add(s);
    }

    public void addPhone(String phoneNo, String description) {
        phones.put(phoneNo, phoneNo);
    }

    public String getPhoneDescription(String phoneNo) {
        return phones.get(phoneNo);
    }

    public String getHobbies() {
        if (hobbies.size() > 0) {
            String result = hobbies.get(0);
            boolean first = true;
            for (String hobby : hobbies) {
                if (first) {
                    first = false;
                } else {
                    result += "," + hobby;
                }
            }
            return result;
        } else {
            return "";
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Customer[ id=" + id + " ]";
    }

}
