package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepDb {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private final List<Client> clients = new ArrayList<>();

    public ClientRepDb() {
        try {
            readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readAll() throws SQLException {
        clients.clear();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM myclients ORDER BY client_id")) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("contact_person"),
                        rs.getString("inn"),
                        rs.getString("ogrn")
                );
                client.setClientId(rs.getInt("client_id"));
                clients.add(client);
            }
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

    public void add(Client client) throws SQLException {
        String getMaxIdSql = "SELECT MAX(client_id) FROM myclients";
        String checkUniqueSql = "SELECT COUNT(*) FROM myclients WHERE inn = ? OR ogrn = ?";
        String insertSql = "INSERT INTO myclients (client_id, name, address, phone, contact_person, inn, ogrn) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUniqueSql)) {
                checkStmt.setString(1, client.getInn());
                checkStmt.setString(2, client.getOgrn());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new IllegalArgumentException("Клиент с таким ИНН или ОГРН уже существует!");
                    }
                }
            }
            int nextId = 1;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(getMaxIdSql)) {
                if (rs.next()) {
                    int maxId = rs.getInt(1);
                    if (!rs.wasNull()) {
                        nextId = maxId + 1;
                    }
                }
            }

            client.setClientId(nextId);
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, client.getClientId());
                ps.setString(2, client.getName());
                ps.setString(3, client.getAddress());
                ps.setString(4, client.getPhone());
                ps.setString(5, client.getContactPerson());
                ps.setString(6, client.getInn());
                ps.setString(7, client.getOgrn());
                ps.executeUpdate();
            }

            clients.add(client);
        }
    }

    public boolean replaceById(int id, Client newClient) throws SQLException {
        String checkUniqueSql = "SELECT COUNT(*) FROM myclients WHERE (inn = ? OR ogrn = ?) AND client_id <> ?";
        boolean found;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUniqueSql)) {
                checkStmt.setString(1, newClient.getInn());
                checkStmt.setString(2, newClient.getOgrn());
                checkStmt.setInt(3, id);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new IllegalArgumentException("ИНН или ОГРН уже используется другим клиентом!");
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "UPDATE myclients SET name=?, address=?, phone=?, contact_person=?, inn=?, ogrn=? WHERE client_id=?")) {

                ps.setString(1, newClient.getName());
                ps.setString(2, newClient.getAddress());
                ps.setString(3, newClient.getPhone());
                ps.setString(4, newClient.getContactPerson());
                ps.setString(5, newClient.getInn());
                ps.setString(6, newClient.getOgrn());
                ps.setInt(7, id);

                found = ps.executeUpdate() > 0;
            }
            if (found) {
                for (int i = 0; i < clients.size(); i++) {
                    if (clients.get(i).getClientId() == id) {
                        newClient.setClientId(id);
                        clients.set(i, newClient);
                        break;
                    }
                }
            }
        }

        return found;
    }

    public boolean deleteById(int id) throws SQLException {
        boolean removed;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement("DELETE FROM myclients WHERE client_id=?")) {

            ps.setInt(1, id);
            removed = ps.executeUpdate() > 0;
        }

        if (removed) {
            clients.removeIf(c -> c.getClientId() == id);
        }

        return removed;
    }

    public int getCount() {
        return clients.size();
    }

    public List<Client> getAll() {
        return new ArrayList<>(clients);
    }
}
