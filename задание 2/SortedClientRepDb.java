package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class SortedClientRepDb extends ClientRepDbDecorator {

    public SortedClientRepDb(ClientRepDb wrappee) {
        super(wrappee);
    }

    @Override
    public List<String> get_k_n_short_list(int n, int k) {
        List<Client> sorted = wrappee.getAll().stream()
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .collect(Collectors.toList());

        return sorted.stream()
                .skip(n)
                .limit(k)
                .map(Client::toShortString)
                .collect(Collectors.toList());
    }

    @Override
    public int getCount() {
        return wrappee.getCount();
    }
}
