package nominal;


import java.util.HashSet;
import java.util.Set;

public class NominalSet {

    private final Set<Integer> nominals;

    public NominalSet(Set<Integer> nominals) {
        this.nominals = new HashSet<>();
        this.nominals.addAll(nominals);
    }

    public Integer getMaxNominal() {
        return nominals.stream()
                .max((o1, o2) -> o1 > o2 ? 1: -1).get();
    }

    public int getSize() {
        return nominals.size();
    }

    public void removeNominal(int nominal) {
        nominals.remove(nominal);
    }
}
