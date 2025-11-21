package org.example;

public class Client {
    private String name;
    private String address;
    private String phone;
    private String contactPerson;
    private String inn;
    private String ogrn;

    Client(String name, String address, String phone, String contactPerson, String inn, String ogrn) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.contactPerson = contactPerson;
        this.inn = inn;
        this.ogrn = ogrn;
    }

    public String getName(){return this.name;}

    public void setName(String name){this.name = name;}

    public String getAddress(){return this.address;}

    public void setAddress(String address){this.address = address;}

    public String getPhone(){return this.phone;}

    public void setPhone(String phone){this.phone = phone;}

    public String getContactPerson(){return this.contactPerson;}

    public void setContactPerson(String contactPerson){this.contactPerson = contactPerson;}

    public String getInn(){return this.inn;}

    public void setInn(String inn){this.inn = inn;}

    public String getOgrn(){return this.ogrn;}

    public void setOgrn(String ogrn){this.ogrn = ogrn;}
}
