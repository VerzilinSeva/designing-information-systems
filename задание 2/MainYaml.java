package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class MainYaml {

    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        ClientRepYaml yamlRep = new ClientRepYaml();

        while (true) {
            System.out.println("1. Показать всех клиентов");
            System.out.println("2. Добавить клиента");
            System.out.println("3. Найти клиента по ID");
            System.out.println("4. Удалить клиента по ID");
            System.out.println("5. Заменить клиента по ID");
            System.out.println("6. Сортировать по полю");
            System.out.println("7. Получить список (пагинация)");
            System.out.println("8. Показать количество клиентов");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {

                    case 1 -> {
                        List<Client> clients = yamlRep.getAll();
                        if (clients.isEmpty()) System.out.println("Список пуст.");
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
                        String contactPerson = scanner.nextLine();
                        System.out.print("ИНН: ");
                        String inn = scanner.nextLine();
                        System.out.print("ОГРН: ");
                        String ogrn = scanner.nextLine();

                        Client newClient = new Client(name, address, phone, contactPerson, inn, ogrn);
                        yamlRep.add(newClient);
                        System.out.println("Клиент добавлен.");
                    }

                    case 3 -> {
                        System.out.print("Введите ID: ");
                        int id = scanner.nextInt();
                        Client found = yamlRep.getById(id);
                        System.out.println(found != null ? found.toFullString() : "Не найден.");
                    }

                    case 4 -> {
                        System.out.print("Введите ID для удаления: ");
                        int id = scanner.nextInt();
                        boolean removed = yamlRep.deleteById(id);
                        System.out.println(removed ? "Удалено." : "Не найден.");
                    }

                    case 5 -> {
                        System.out.print("Введите ID для замены: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Новое название организации: ");
                        String name = scanner.nextLine();
                        System.out.print("Новый адрес: ");
                        String address = scanner.nextLine();
                        System.out.print("Новый телефон: ");
                        String phone = scanner.nextLine();
                        System.out.print("Новое контактное лицо: ");
                        String contactPerson = scanner.nextLine();
                        System.out.print("Новый ИНН: ");
                        String inn = scanner.nextLine();
                        System.out.print("Новый ОГРН: ");
                        String ogrn = scanner.nextLine();

                        Client newClient = new Client(name, address, phone, contactPerson, inn, ogrn);
                        boolean replaced = yamlRep.replaceById(id, newClient);
                        System.out.println(replaced ? "Заменено." : "Не найден.");
                    }

                    case 6 -> {
                        System.out.println("Выберите поле для сортировки:");
                        System.out.println("1. name");
                        System.out.println("2. address");
                        System.out.println("3. phone");
                        System.out.println("4. contactPerson");
                        System.out.println("5. inn");
                        System.out.println("6. ogrn");
                        System.out.print("Ваш выбор: ");
                        int fieldChoice = scanner.nextInt();
                        scanner.nextLine();

                        String field;
                        switch (fieldChoice) {
                            case 1 -> field = "name";
                            case 2 -> field = "address";
                            case 3 -> field = "phone";
                            case 4 -> field = "contactPerson";
                            case 5 -> field = "inn";
                            case 6 -> field = "ogrn";
                            default -> {
                                System.out.println("Неверный выбор, сортировка отменена.");
                                continue;
                            }
                        }

                        System.out.print("Сортировка по возрастанию? (true/false): ");
                        boolean asc = scanner.nextBoolean();
                        scanner.nextLine();

                        yamlRep.sortByField(field, asc);
                        System.out.println("Сортировка выполнена по полю " + field + ".");
                    }

                    case 7 -> {
                        System.out.print("Введите n (пропуск): ");
                        int n = scanner.nextInt();
                        System.out.print("Введите k (количество элементов): ");
                        int k = scanner.nextInt();
                        scanner.nextLine();

                        List<String> shortList = yamlRep.get_k_n_short_list(n, k);
                        if (shortList.isEmpty()) System.out.println("Нет данных.");
                        else shortList.forEach(System.out::println);
                    }

                    case 8 -> {
                        System.out.println("Количество клиентов: " + yamlRep.getCount());
                    }

                    case 0 -> {
                        System.out.println("Выход...");
                        return;
                    }

                    default -> System.out.println("Неверный пункт.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
