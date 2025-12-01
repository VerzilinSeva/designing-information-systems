package org.example;

import java.util.List;

public abstract class ClientRepDecorator extends ClientRep {

    protected final ClientRep wrappee;

    public ClientRepDecorator(ClientRep wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    protected void readAll() throws Exception {
        wrappee.readAll();
    }

    @Override
    protected void writeAll() throws Exception {
        wrappee.writeAll();
    }

    @Override
    public Client getById(int id) {
        return wrappee.getById(id);
    }

    @Override
    public List<String> get_k_n_short_list(int n, int k) {
        return wrappee.get_k_n_short_list(n, k);
    }

    @Override
    public void add(Client client) throws Exception {
        wrappee.add(client);
    }

    @Override
    public boolean replaceById(int id, Client newClient) throws Exception {
        return wrappee.replaceById(id, newClient);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        return wrappee.deleteById(id);
    }

    @Override
    public int getCount() {
        return wrappee.getCount();
    }

    @Override
    public List<Client> getAll() {
        return wrappee.getAll();
    }
}
