package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class FilteredClientRepDb extends ClientRepDbDecorator {

    public FilteredClientRepDb(ClientRepDb wrappee) {
        super(wrappee);
    }

    @Override
    public List<String> get_k_n_short_list(int n, int k) {
        List<Client> filtered = wrappee.getAll().stream()
                .filter(c -> c.getInn() != null && c.getInn().contains("6"))
                .collect(Collectors.toList());

        return filtered.stream()
                .skip(n)
                .limit(k)
                .map(Client::toShortString)
                .collect(Collectors.toList());
    }

    @Override
    public int getCount() {
        return (int) wrappee.getAll().stream()
                .filter(c -> c.getInn() != null && c.getInn().contains("6"))
                .count();
    }
}
