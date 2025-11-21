package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.IOException;

public class Client extends BaseClient{
    private String address;
    private String phone;
    private String ogrn;

    public Client(String name, String address, String phone, String contactPerson, String inn, String ogrn) {
        super(name, contactPerson,inn);
        this.address = validateAddress(address);
        this.phone = validatePhone(phone);
        this.ogrn = validateOGRN(ogrn);
    }

    public Client(String data){
        super();
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
        super();
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

    public static String validateOGRN(String ogrn) {
        if (ogrn == null || !ogrn.matches("^\\d{13}$"))
            throw new IllegalArgumentException("Некорректный ОГРН");
        return ogrn;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = validateAddress(address); }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = validatePhone(phone); }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return inn.equals(client.inn) && ogrn.equals(client.ogrn);
    }

}
