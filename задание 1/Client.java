package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.IOException;

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

    public Client(String data){
        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка с данными пуста");
        }
        String[] dataArray = data.split(";");
        if (dataArray.length != 6) {
            throw new IllegalArgumentException(
                    "Строка должна содержать 6 параметров(имя организации, адрес," +
                            " телефон, контактное лицо, ИНН, ОГРН)"
            );
        }
        else {
            this.name = validateName(dataArray[0]);
            this.address = validateAddress(dataArray[1]);
            this.phone = validatePhone(dataArray[2]);
            this.contactPerson = validateContactPerson(dataArray[3]);
            this.inn = validateINN(dataArray[4]);
            this.ogrn = validateOGRN(dataArray[5]);
        }
    }

    public Client(String jsonFilePath, boolean isJsonFile) throws IOException {
        if (!isJsonFile) {
            throw new IllegalArgumentException("Для CSV используйте другой конструктор");
        }

        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new GsonBuilder().create();

            Client temp = gson.fromJson(reader, Client.class);

            this.name = validateName(temp.name);
            this.address = validateAddress(temp.address);
            this.phone = validatePhone(temp.phone);
            this.contactPerson = validateContactPerson(temp.contactPerson);
            this.inn = validateINN(temp.inn);
            this.ogrn = validateOGRN(temp.ogrn);

        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Ошибка в формате JSON", e);
        }
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

        String trimmed = contactPerson.trim();
        String[] words = trimmed.split("\\s+");

        if (words.length < 2 || words.length > 3)
            throw new IllegalArgumentException("Контактное лицо должно состоять из 2-3 слов");

        StringBuilder normalized = new StringBuilder();
        for (String word : words) {
            if (!word.matches("[A-Za-zА-Яа-яЁё]{2,50}"))
                throw new IllegalArgumentException("Каждое слово контактного лица должно содержать только буквы (2-50 символов)");

            if (!word.isEmpty()) {
                if (normalized.length() > 0) normalized.append(" ");
                normalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
        }
        return normalized.toString();
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

    public String toFullString() {
        return "Полная информация о клиенте: {" +
                "название организации:'" + name + '\'' +
                ", адрес:'" + address + '\'' +
                ", телефон:'" + phone + '\'' +
                ", контактное лицо:'" + contactPerson + '\'' +
                ", ИНН:'" + inn + '\'' +
                ", ОГРН:'" + ogrn + '\'' +
                '}';
    }

    public String toShortString() {
        return "Краткая информация о клиенте: {" +
                "название организации:'" + name + '\'' +
                ", контактное лицо:'" + contactPerson + '\'' +
                ", ИНН:'" + inn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return inn.equals(client.inn) && ogrn.equals(client.ogrn);
    }
}
