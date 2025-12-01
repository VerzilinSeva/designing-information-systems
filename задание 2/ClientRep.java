package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class ClientRep {

    protected List<Client> clients = new ArrayList<>();

    protected abstract void readAll() throws Exception;

    protected abstract void writeAll() throws Exception;

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

    public void sortByField(String field, boolean asc) throws Exception {
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

    public void add(Client client) throws Exception {
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

    public boolean replaceById(int id, Client newClient) throws Exception {
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

    public boolean deleteById(int id) throws Exception {
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
