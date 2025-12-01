package org.example;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class ClientRepYaml {

    private static final String FILE_PATH = "src/main/resources/MyClient.yaml";

    private final Yaml yaml;

    private List<Client> clients = new ArrayList<>();

    public ClientRepYaml() {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);

        readAll();
    }

    public void readAll() {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            clients = new ArrayList<>();
            BaseClient.idCounter = 0;
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            List<Object> data = yaml.load(reader);

            if (data == null) {
                clients = new ArrayList<>();
                BaseClient.idCounter = 0;
                return;
            }

            clients = parseClients(data);

            int maxId = clients.stream()
                    .mapToInt(Client::getClientId)
                    .max()
                    .orElse(-1);

            BaseClient.idCounter = maxId + 1;

        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения YAML: " + e.getMessage());
        }
    }

    private static List<Client> parseClients(List<Object> data) throws Exception {
        List<Client> clients = new ArrayList<>();

        for (Object obj : data) {
            Map<String, Object> map = (Map<String, Object>) obj;

            Client client = new Client(
                    (String) map.get("name"),
                    (String) map.get("address"),
                    (String) map.get("phone"),
                    (String) map.get("contactPerson"),
                    (String) map.get("inn"),
                    (String) map.get("ogrn")
            );

            if (map.get("clientId") != null) {
                client.setClientId((Integer) map.get("clientId"));
            }

            clients.add(client);
        }
        return clients;
    }

    public void writeAll() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {

            List<Map<String, Object>> data = new ArrayList<>();

            for (Client c : clients) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("clientId", c.getClientId());
                map.put("name", c.getName());
                map.put("address", c.getAddress());
                map.put("phone", c.getPhone());
                map.put("contactPerson", c.getContactPerson());
                map.put("inn", c.getInn());
                map.put("ogrn", c.getOgrn());
                data.add(map);
            }

            yaml.dump(data, writer);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка записи YAML: " + e.getMessage());
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

    private boolean existsDuplicate(Client newClient, Integer ignoreId) {
        return clients.stream().anyMatch(c ->
                !Objects.equals(c.getClientId(), ignoreId) &&
                        (c.getInn().equals(newClient.getInn()) ||
                                c.getOgrn().equals(newClient.getOgrn()))
        );
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
