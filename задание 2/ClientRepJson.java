package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class ClientRepJson extends ClientRep {

    private static final String filePath = "src/main/resources/MyClient.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type CLIENT_LIST_TYPE = new TypeToken<List<Client>>() {}.getType();

    public ClientRepJson() {
        readAll();
    }

    @Override
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

    @Override
    public void writeAll() {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(clients, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
