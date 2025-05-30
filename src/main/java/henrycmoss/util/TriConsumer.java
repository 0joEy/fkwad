package henrycmoss.util;

@FunctionalInterface
public interface TriConsumer<W, P, V> {

    void accept(W world, P player, V victim);
}
