package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class MainDecoratorDb {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);

        try {
            ClientRepDb db = new ClientRepDb();
            ClientRep adapterOriginal = new DbAdapter(db);

            System.out.print("Введите k (номер первого элемента): ");
            int k = scanner.nextInt();

            System.out.print("Введите n (количество элементов): ");
            int n = scanner.nextInt();
            scanner.nextLine();

            System.out.println();

            System.out.println("ОРИГИНАЛЬНЫЕ ДАННЫЕ");
            printList(adapterOriginal.get_k_n_short_list(k, n));
            System.out.println("Count: " + adapterOriginal.getCount());
            System.out.println();

            ClientRepDb sortedDb = new SortedClientRepDb(db);
            ClientRep adapterSorted = new DbAdapter(sortedDb);

            System.out.println("ТОЛЬКО СОРТИРОВКА (по имени)");
            printList(adapterSorted.get_k_n_short_list(k, n));
            System.out.println("Count: " + adapterSorted.getCount());
            System.out.println();

            ClientRepDb filteredDb = new FilteredClientRepDb(db);
            ClientRep adapterFiltered = new DbAdapter(filteredDb);

            System.out.println("ТОЛЬКО ФИЛЬТР (ИНН содержит '6')");
            printList(adapterFiltered.get_k_n_short_list(k, n));
            System.out.println("Count: " + adapterFiltered.getCount());
            System.out.println();

            ClientRepDb filteredAndSortedDb = new SortedClientRepDb(filteredDb);
            ClientRep adapterFilteredAndSorted = new DbAdapter(filteredAndSortedDb);

            System.out.println("ФИЛЬТР + СОРТИРОВКА");
            printList(adapterFilteredAndSorted.get_k_n_short_list(k, n));
            System.out.println("Count: " + adapterFilteredAndSorted.getCount());
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
            adapterOriginal.add(newClient);

            System.out.println("\nНовый клиент добавлен в БД.");

            System.out.println("\nОригинальные данные после добавления:");
            printList(adapterOriginal.get_k_n_short_list(k, n));

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
