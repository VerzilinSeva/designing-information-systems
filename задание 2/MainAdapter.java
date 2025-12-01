package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class MainAdapter {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        ClientRepDb dbRepository = new ClientRepDb();

        DbAdapter adapter = new DbAdapter(dbRepository);

        try {
            adapter.readAll();
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
        }

        while (true) {
            System.out.println("1. Показать всех клиентов");
            System.out.println("2. Добавить клиента");
            System.out.println("3. Найти клиента по ID");
            System.out.println("4. Удалить клиента по ID");
            System.out.println("5. Заменить клиента по ID");
            System.out.println("6. Получить список (часть данных)");
            System.out.println("7. Показать количество клиентов");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        List<Client> clients = adapter.getAll();
                        if (clients.isEmpty()) System.out.println("Список клиентов пуст.");
                        else clients.forEach(c -> System.out.println(c.toFullString()));
                    }

                    case 2 -> {
                        System.out.print("Название организации: ");
                        String name = scanner.nextLine();
                        System.out.print("Адрес: ");
                        String address = scanner.nextLine();
                        System.out.print("Телефон: ");
                        String phone = scanner.nextLine();
                        System.out.print("Контактное лицо: ");
                        String contact = scanner.nextLine();
                        System.out.print("ИНН (10 цифр): ");
                        String inn = scanner.nextLine();
                        System.out.print("ОГРН (13 цифр): ");
                        String ogrn = scanner.nextLine();

                        adapter.add(new Client(name, address, phone, contact, inn, ogrn));
                        System.out.println("Клиент добавлен.");
                    }

                    case 3 -> {
                        System.out.print("Введите ID клиента: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        Client c = adapter.getById(id);
                        System.out.println(c != null ? c.toFullString() : "Клиент не найден.");
                    }

                    case 4 -> {
                        System.out.print("Введите ID клиента для удаления: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        boolean removed = adapter.deleteById(id);
                        System.out.println(removed ? "Клиент удалён." : "Клиент не найден.");
                    }

                    case 5 -> {
                        System.out.print("Введите ID клиента для замены: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Новое название организации: ");
                        String name = scanner.nextLine();
                        System.out.print("Новый адрес: ");
                        String address = scanner.nextLine();
                        System.out.print("Новый телефон: ");
                        String phone = scanner.nextLine();
                        System.out.print("Новое контактное лицо: ");
                        String contact = scanner.nextLine();
                        System.out.print("Новый ИНН: ");
                        String inn = scanner.nextLine();
                        System.out.print("Новый ОГРН: ");
                        String ogrn = scanner.nextLine();

                        boolean replaced = adapter.replaceById(id,
                                new Client(name, address, phone, contact, inn, ogrn));
                        System.out.println(replaced ? "Клиент обновлён." : "Клиент не найден.");
                    }

                    case 6 -> {
                        System.out.print("Введите номер начала (n): ");
                        int n = scanner.nextInt();
                        System.out.print("Введите количество элементов (k): ");
                        int k = scanner.nextInt();
                        scanner.nextLine();

                        List<String> part = adapter.get_k_n_short_list(n, k);
                        if (part.isEmpty()) System.out.println("Нет данных.");
                        else part.forEach(System.out::println);
                    }

                    case 7 -> {
                        System.out.println("Количество клиентов: " + adapter.getCount());
                    }

                    case 0 -> {
                        System.out.println("Выход...");
                        return;
                    }

                    default -> System.out.println("Неверный ввод.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
