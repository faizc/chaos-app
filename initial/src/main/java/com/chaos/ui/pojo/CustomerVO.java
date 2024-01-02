package com.chaos.ui.pojo;

public class CustomerVO {
    private int customerId;
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String phone;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" CustomerId ").append(customerId)
                .append(" FirstName ").append(firstName)
                .append(" LastName ").append(lastName)
                .append(" CompanyName ").append(companyName)
                .append(" Email ").append(email)
                .append(" Phone ").append(phone).append("\n");
        return buffer.toString();
    }
}
