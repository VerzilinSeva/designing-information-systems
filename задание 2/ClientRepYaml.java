package org.example;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class ClientRepYaml extends ClientRep {

    private static final String FILE_PATH = "src/main/resources/MyClient.yaml";
    private final Yaml yaml;

    public ClientRepYaml() {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);
        readAll();
    }

    @Override
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

    private static List<Client> parseClients(List<Object> data) {
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

    @Override
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
}
