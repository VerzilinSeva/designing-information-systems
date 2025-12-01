package org.example;

import java.util.List;

public class DbAdapter extends ClientRep {

    private final ClientRepDb dbRepository;

    public DbAdapter(ClientRepDb dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    protected void readAll() throws Exception {
        dbRepository.readAll();
        clients.clear();
        clients.addAll(dbRepository.getAll());
    }

    @Override
    protected void writeAll() throws Exception {
        throw new UnsupportedOperationException("writeAll not supported for DB");
    }

    @Override
    public Client getById(int id) {
        return dbRepository.getById(id);
    }

    @Override
    public List<String> get_k_n_short_list(int n, int k) {
        return dbRepository.get_k_n_short_list(n, k);
    }

    @Override
    public void add(Client client) throws Exception {
        dbRepository.add(client);
    }

    @Override
    public boolean replaceById(int id, Client newClient) throws Exception {
        return dbRepository.replaceById(id, newClient);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        return dbRepository.deleteById(id);
    }

    @Override
    public int getCount() {
        return dbRepository.getCount();
    }

    @Override
    public List<Client> getAll() {
        return dbRepository.getAll();
    }
}
