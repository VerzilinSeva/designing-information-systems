package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class ClientRepJson {

    private static final String filePath = "src/main/resources/MyClient.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private List<Client> clients = new ArrayList<>();
    private static final Type CLIENT_LIST_TYPE = new TypeToken<List<Client>>() {}.getType();

    public ClientRepJson() {
        readAll();
    }

    public void readAll() {
        try (FileReader reader = new FileReader(filePath)) {
            List<Client> loaded = gson.fromJson(reader, CLIENT_LIST_TYPE);
            if (loaded != null) {
                clients = loaded;
                int maxId = clients.stream()
                        .mapToInt(Client::getClientId)
                        .max()
                        .orElse(-1);
                BaseClient.idCounter = maxId + 1;
            }
        } catch (IOException e) {
            clients = new ArrayList<>();
        }
    }

    public void writeAll() {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(clients, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Client getById(int id) {
        return clients.stream()
                .filter(c -> c.getClientId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<String> get_k_n_short_list(int n, int k) {
        return clients.stream()
                .skip(n)
                .limit(k)
                .map(Client::toShortString)
                .toList();
    }

    public void sortByField(String field, boolean asc) {
        Comparator<Client> cmp;

        switch (field) {
            case "name" -> cmp = Comparator.comparing(Client::getName, String.CASE_INSENSITIVE_ORDER);
            case "address" -> cmp = Comparator.comparing(Client::getAddress, String.CASE_INSENSITIVE_ORDER);
            case "phone" -> cmp = Comparator.comparing(Client::getPhone, String.CASE_INSENSITIVE_ORDER);
            case "contactPerson" -> cmp = Comparator.comparing(Client::getContactPerson, String.CASE_INSENSITIVE_ORDER);
            case "inn" -> cmp = Comparator.comparing(Client::getInn);
            case "ogrn" -> cmp = Comparator.comparing(Client::getOgrn);
            default -> throw new IllegalArgumentException("Неизвестное поле сортировки: " + field);
        }

        if (!asc) cmp = cmp.reversed();

        clients.sort(cmp);
        writeAll();
    }

    public void add(Client client) {
        for (Client c : clients) {
            if (c.getInn().equals(client.getInn()) ||
                    c.getOgrn().equals(client.getOgrn()))
            {
                throw new IllegalArgumentException("Клиент с таким ИНН или ОГРН уже существует!");
            }
        }
        clients.add(client);
        writeAll();
    }

    public boolean replaceById(int id, Client newClient) {
        for (Client c : clients) {
            if (c.getClientId() != id &&
                    (c.getInn().equals(newClient.getInn()) ||
                            c.getOgrn().equals(newClient.getOgrn())))
            {
                throw new IllegalArgumentException("Нельзя заменить: клиент с такими ИНН или ОГРН уже есть!");
            }
        }

        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getClientId() == id) {
                newClient.setClientId(id);
                clients.set(i, newClient);
                writeAll();
                return true;
            }
        }
        return false;
    }

    public boolean deleteById(int id) {
        boolean removed = clients.removeIf(c -> c.getClientId() == id);
        if (removed) writeAll();
        return removed;
    }

    public int getCount() {
        return clients.size();
    }

    public List<Client> getAll() {
        return new ArrayList<>(clients);
    }
}
