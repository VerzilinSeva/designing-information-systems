package org.example;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilteredClientRep extends ClientRepDecorator {

    private final Predicate<Client> predicate;

    public FilteredClientRep(ClientRep wrappee) {
        super(wrappee);
        this.predicate = c -> c.getInn() != null && c.getInn().contains("7");
    }

    @Override
    public List<String> get_k_n_short_list(int n, int k) {
        return wrappee.getAll().stream()
                .filter(predicate)
                .skip(n)
                .limit(k)
                .map(Client::toShortString)
                .collect(Collectors.toList());
    }

    @Override
    public int getCount() {
        return (int) wrappee.getAll().stream()
                .filter(predicate)
                .count();
    }
}
