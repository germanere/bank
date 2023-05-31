package models;

import java.io.Serializable;

public class User implements Serializable {
    public String name;
    public String customerId;

    public User() {
        super();
    }

    public User(String name, String customerId) {
        super();
        this.name = name;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
