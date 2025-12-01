package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class MainDecoratorFile {
    public static void main(String[] args) {

        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("Выберите источник данных:");
            System.out.println("1 — JSON");
            System.out.println("2 — YAML");
            System.out.print("Ваш выбор: ");

            int choose = scanner.nextInt();
            scanner.nextLine();

            ClientRep fileRep;

            if (choose == 2) {
                fileRep = new ClientRepYaml();
                System.out.println("Используется YAML");
            } else {
                fileRep = new ClientRepJson();
                System.out.println("Используется JSON");
            }

            System.out.print("\nВведите k (номер первого элемента): ");
            int k = scanner.nextInt();

            System.out.print("Введите n (количество элементов): ");
            int n = scanner.nextInt();
            scanner.nextLine();

            System.out.println();

            System.out.println("ОРИГИНАЛЬНЫЕ ДАННЫЕ");
            printList(fileRep.get_k_n_short_list(k, n));
            System.out.println("Count: " + fileRep.getCount());
            System.out.println();

            ClientRep sorted = new SortedClientRep(fileRep);

            System.out.println("ТОЛЬКО СОРТИРОВКА (по имени)");
            printList(sorted.get_k_n_short_list(k, n));
            System.out.println("Count: " + sorted.getCount());
            System.out.println();

            ClientRep filtered = new FilteredClientRep(fileRep);

            System.out.println("ТОЛЬКО ФИЛЬТР (ИНН содержит '7')");
            printList(filtered.get_k_n_short_list(k, n));
            System.out.println("Count: " + filtered.getCount());
            System.out.println();

            ClientRep filteredSorted = new SortedClientRep(filtered);

            System.out.println("ФИЛЬТР + СОРТИРОВКА");
            printList(filteredSorted.get_k_n_short_list(k, n));
            System.out.println("Count: " + filteredSorted.getCount());
            System.out.println();

            System.out.println("Введите данные нового клиента:");

            System.out.print("Название: ");
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
            fileRep.add(newClient);

            System.out.println("\nНовый клиент добавлен.");

            System.out.println("\nОригинальные данные после добавления:");
            printList(fileRep.get_k_n_short_list(k, n));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printList(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }
}
