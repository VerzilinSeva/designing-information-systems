package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.setErr(new java.io.PrintStream(System.err, true, "UTF-8"));

            Scanner scanner = new Scanner(System.in, "UTF-8");

            System.out.println("Введите данные клиента");

            System.out.print("Название компании: ");
            String name = scanner.nextLine();

            System.out.print("Адрес: ");
            String address = scanner.nextLine();

            System.out.print("Телефон: ");
            String phone = scanner.nextLine();

            System.out.print("Контактное лицо (ФИО через пробел): ");
            String contactPerson = scanner.nextLine();

            System.out.print("ИНН (10 цифр): ");
            String inn = scanner.nextLine();

            System.out.print("ОГРН (13 цифр): ");
            String ogrn = scanner.nextLine();

            Client client = new Client(name, address, phone, contactPerson, inn, ogrn);

            System.out.println("\nПолная информация о клиенте:");
            System.out.println("Название: " + client.getName());
            System.out.println("Адрес: " + client.getAddress());
            System.out.println("Телефон: " + client.getPhone());
            System.out.println("Контактное лицо: " + client.getContactPerson());
            System.out.println("ИНН: " + client.getInn());
            System.out.println("ОГРН: " + client.getOgrn());

            System.out.println("\nJson:");
            String filePath = "src/main/resources/MyClient.json";

            Client clientJson = new Client(filePath, true);

            System.out.println("\nПолная информация о клиенте:");
            System.out.println("Название: " + clientJson.getName());
            System.out.println("Адрес: " + clientJson.getAddress());
            System.out.println("Телефон: " + clientJson.getPhone());
            System.out.println("Контактное лицо: " + clientJson.getContactPerson());
            System.out.println("ИНН: " + clientJson.getInn());
            System.out.println("ОГРН: " + clientJson.getOgrn());

            System.out.println("\nСтрока:");
            String data = "ООО Магнит;ул. Набережная д. 55;89182234521;Михайлов Сергей Валерьевич;1234567890;1234567890123";

            Client clientData = new Client(data);

            System.out.println(clientData.toFullString());
            System.out.println(clientData.toShortString());

            System.out.println("\nПолная информация о клиенте:");
            System.out.println("Название: " + clientData.getName());
            System.out.println("Адрес: " + clientData.getAddress());
            System.out.println("Телефон: " + clientData.getPhone());
            System.out.println("Контактное лицо: " + clientData.getContactPerson());
            System.out.println("ИНН: " + clientData.getInn());
            System.out.println("ОГРН: " + clientData.getOgrn());

            String ClientData1 = "ООО Магнит;ул. Набережная д. 55;89182234521;Михайлов Сергей Вадимович;1234567890;1234567890123";
            Client clientData1 = new Client(ClientData1);
            String ClientData2 = "ООО Магнит;ул. Набережная д. 55;89182234521;Михайлов Сергей Вадимович;1234567890;2234567890123";
            Client clientData2 = new Client(ClientData2);

            System.out.println(clientData1.equals(clientData2));

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
