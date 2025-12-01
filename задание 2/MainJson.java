package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class MainJson {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        ClientRepJson repo = new ClientRepJson();

        while (true) {
            System.out.println("1. Показать всех клиентов");
            System.out.println("2. Добавить клиента");
            System.out.println("3. Найти клиента по ID");
            System.out.println("4. Удалить клиента по ID");
            System.out.println("5. Заменить клиента по ID");
            System.out.println("6. Сортировать по полю");
            System.out.println("7. Получить список (часть данных)");
            System.out.println("8. Показать количество клиентов");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        List<Client> clients = repo.getAll();
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

                        repo.add(new Client(name, address, phone, contact, inn, ogrn));
                        System.out.println("Клиент добавлен.");
                    }
                    case 3 -> {
                        System.out.print("Введите ID клиента: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        Client c = repo.getById(id);
                        System.out.println(c != null ? c.toFullString() : "Клиент не найден.");
                    }
                    case 4 -> {
                        System.out.print("Введите ID клиента для удаления: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println(repo.deleteById(id) ? "Удалено." : "Клиент не найден.");
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

                        boolean replaced = repo.replaceById(id, new Client(name, address, phone, contact, inn, ogrn));
                        System.out.println(replaced ? "Заменено." : "Клиент не найден.");
                    }
                    case 6 -> {
                        System.out.println("\nВыберите поле для сортировки:");
                        System.out.println("1. Название организации");
                        System.out.println("2. Адрес");
                        System.out.println("3. Телефон");
                        System.out.println("4. Контактное лицо");
                        System.out.println("5. ИНН");
                        System.out.println("6. ОГРН");
                        System.out.println("0. Отмена");
                        System.out.print("Выберите поле: ");
                        int f = scanner.nextInt();
                        scanner.nextLine();
                        if (f == 0) break;

                        System.out.println("1 — по возрастанию, 2 — по убыванию");
                        int order = scanner.nextInt();
                        scanner.nextLine();
                        boolean asc = order == 1;

                        String field = switch (f) {
                            case 1 -> "name";
                            case 2 -> "address";
                            case 3 -> "phone";
                            case 4 -> "contactPerson";
                            case 5 -> "inn";
                            case 6 -> "ogrn";
                            default -> "";
                        };
                        if (!field.isEmpty()) {
                            repo.sortByField(field, asc);
                            System.out.println("Сортировка выполнена.");
                        } else System.out.println("Неверное поле.");
                    }
                    case 7 -> {
                        System.out.print("Введите номер страницы (k): ");
                        int k = scanner.nextInt();
                        System.out.print("Введите размер страницы (n): ");
                        int n = scanner.nextInt();
                        scanner.nextLine();
                        List<String> page = repo.get_k_n_short_list(k, n);
                        if (page.isEmpty()) System.out.println("Нет данных для отображения.");
                        else page.forEach(System.out::println);
                    }
                    case 8 -> System.out.println("Количество клиентов: " + repo.getCount());
                    case 0 -> {
                        System.out.println("Выход из программы...");
                        return;
                    }
                    default -> System.out.println("Неверный ввод, попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}

