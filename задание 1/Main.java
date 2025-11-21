package org.example;

import java.util.Scanner;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные клиента");

        System.out.print("Название компании: ");
        String name = scanner.nextLine();

        System.out.print("Адрес: ");
        String address = scanner.nextLine();

        System.out.print("Телефон: ");
        String phone = scanner.nextLine();

        System.out.print("Контактное лицо: ");
        String contactPerson = scanner.nextLine();

        System.out.print("ИНН: ");
        String inn = scanner.nextLine();

        System.out.print("ОГРН: ");
        String ogrn = scanner.nextLine();

        Client client = new Client(name, address, phone, contactPerson, inn, ogrn);

        System.out.println("\nПолная информация о клиенте:");
        System.out.println("Название: " + client.getName());
        System.out.println("Адрес: " + client.getAddress());
        System.out.println("Телефон: " + client.getPhone());
        System.out.println("Контактное лицо: " + client.getContactPerson());
        System.out.println("ИНН: " + client.getInn());
        System.out.println("ОГРН: " + client.getOgrn());

        scanner.close();
    }
}

