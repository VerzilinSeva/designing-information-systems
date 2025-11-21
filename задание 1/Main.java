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

        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
