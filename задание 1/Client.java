package org.example;

public class Client {
    private String name;
    private String address;
    private String phone;
    private String contactPerson;
    private String inn;
    private String ogrn;

    public Client(String name, String address, String phone, String contactPerson, String inn, String ogrn) {
        this.name = validateName(name);
        this.address = validateAddress(address);
        this.phone = validatePhone(phone);
        this.contactPerson = validateContactPerson(contactPerson);
        this.inn = validateINN(inn);
        this.ogrn = validateOGRN(ogrn);
    }

    public static String validateName(String name) {
        if (name == null || name.trim().length() < 2 || name.trim().length() > 100)
            throw new IllegalArgumentException("Некорректное название компании");
        return name;
    }

    public static String validateAddress(String address) {
        if (address == null || address.trim().length() < 5 || address.trim().length() > 200)
            throw new IllegalArgumentException("Некорректный адрес");
        return address;
    }

    public static String validatePhone(String phone) {
        if (phone == null || !phone.matches("^(\\+7|7|8)\\d{9,10}$"))
            throw new IllegalArgumentException("Некорректный телефон");
        return phone;
    }

    public static String validateContactPerson(String contactPerson) {
        if (contactPerson == null) throw new IllegalArgumentException("Контактное лицо не может быть пустым");
        String[] words = contactPerson.trim().split("\\s+");
        if (words.length < 2 || words.length > 3)
            throw new IllegalArgumentException("Контактное лицо должно состоять из 2-3 слов");
        for (String word : words) {
            if (!word.matches("[A-Za-zА-Яа-яЁё]{2,50}"))
                throw new IllegalArgumentException("Каждое слово контактного лица должно содержать только буквы (2-50 символов)");
        }
        return contactPerson;
    }

    public static String validateINN(String inn) {
        if (inn == null || !inn.matches("^\\d{10}$"))
            throw new IllegalArgumentException("Некорректный ИНН");
        return inn;
    }

    public static String validateOGRN(String ogrn) {
        if (ogrn == null || !ogrn.matches("^\\d{13}$"))
            throw new IllegalArgumentException("Некорректный ОГРН");
        return ogrn;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = validateName(name); }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = validateAddress(address); }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = validatePhone(phone); }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = validateContactPerson(contactPerson); }

    public String getInn() { return inn; }
    public void setInn(String inn) { this.inn = validateINN(inn); }

    public String getOgrn() { return ogrn; }
    public void setOgrn(String ogrn) { this.ogrn = validateOGRN(ogrn); }

}
