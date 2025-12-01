package org.example;

import java.sql.SQLException;
import java.util.List;

public class ClientRepDbDecorator extends ClientRepDb {
    protected ClientRepDb wrappee;

    public ClientRepDbDecorator(ClientRepDb wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public List<String> get_k_n_short_list(int n, int k) {
        return wrappee.get_k_n_short_list(n, k);
    }

    @Override
    public int getCount() {
        return wrappee.getCount();
    }

    @Override
    public Client getById(int id) {
        return wrappee.getById(id);
    }

    @Override
    public void add(Client client) throws SQLException {
        wrappee.add(client);
    }

    @Override
    public boolean replaceById(int id, Client newClient) throws SQLException {
        return wrappee.replaceById(id, newClient);
    }

    @Override
    public boolean deleteById(int id) throws SQLException {
        return wrappee.deleteById(id);
    }

    @Override
    public List<Client> getAll() {
        return wrappee.getAll();
    }
}
