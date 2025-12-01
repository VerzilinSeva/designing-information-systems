package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortedClientRep extends ClientRepDecorator {

    public SortedClientRep(ClientRep wrappee) {
        super(wrappee);
    }

    @Override
    public List<String> get_k_n_short_list(int n, int k) {
        List<Client> sorted = wrappee.getAll().stream()
                .sorted(Comparator.comparing(Client::getName,
                        String.CASE_INSENSITIVE_ORDER))
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
