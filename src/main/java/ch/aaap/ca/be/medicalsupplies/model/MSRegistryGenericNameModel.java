package ch.aaap.ca.be.medicalsupplies.model;

import java.io.Serializable;
import java.util.Set;

public class MSRegistryGenericNameModel implements Serializable {

    private static final long serialVersionUID = 8983315704347406510L;
    private Set <MSConsolidatedDataProducts> consolidatedDataProducts;
    private int msUniqueGenericNameModelSize;
    private int withoutGenericNames;
    private int genericNameSize;

    public int getGenericNameSize() {
        return genericNameSize;
    }

    public void setGenericNameSize(int genericNameSize) {
        this.genericNameSize = genericNameSize;
    }

    public int getWithoutGenericNames() {
        return withoutGenericNames;
    }

    public void setWithoutGenericNames(int withoutGenericNames) {
        this.withoutGenericNames = withoutGenericNames;
    }

    public Set<MSConsolidatedDataProducts> getConsolidatedDataProducts() {
        return consolidatedDataProducts;
    }

    public void setConsolidatedDataProducts(Set<MSConsolidatedDataProducts> consolidatedDataProducts) {
        this.consolidatedDataProducts = consolidatedDataProducts;
    }

    public int getMsUniqueGenericNameModelSize() {
        return msUniqueGenericNameModelSize;
    }

    public void setMsUniqueGenericNameModelSize(int msUniqueGenericNameModelSize) {
        this.msUniqueGenericNameModelSize = msUniqueGenericNameModelSize;
    }
}
