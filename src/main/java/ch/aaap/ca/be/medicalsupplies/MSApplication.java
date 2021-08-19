package ch.aaap.ca.be.medicalsupplies;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ch.aaap.ca.be.medicalsupplies.data.CSVUtil;
import ch.aaap.ca.be.medicalsupplies.data.MSGenericNameRow;
import ch.aaap.ca.be.medicalsupplies.data.MSProductIdentity;
import ch.aaap.ca.be.medicalsupplies.data.MSProductRow;
import ch.aaap.ca.be.medicalsupplies.model.*;

public class MSApplication {

    private final Set<MSGenericNameRow> genericNames;
    private final Set<MSProductRow> registry;
    private final MSRegistryGenericNameModel msRegistryGenericNameModel;


    public MSApplication() {
        genericNames = CSVUtil.getGenericNames();
        registry = CSVUtil.getRegistry();
        msRegistryGenericNameModel = createModel(genericNames, registry);

    }

    public static void main(String[] args) {
        MSApplication main = new MSApplication();

        System.err.println("generic names count: " + main.genericNames.size());
        System.err.println("registry count: " + main.registry.size());

        System.err.println("1st of generic name list: " + main.genericNames.iterator().next());
        System.err.println("1st of registry list: " + main.registry.iterator().next());

    }

    /**
     * Create a model / data structure that combines the input sets.
     *
     * @param genericNameRows
     * @param productRows
     * @return
     */
    public MSRegistryGenericNameModel createModel(Set<MSGenericNameRow> genericNameRows, Set<MSProductRow> productRows) {

        MSRegistryGenericNameModel msRegistryGenericNameModel = new MSRegistryGenericNameModel();

        Set<MSConsolidatedDataProducts> msConsolidatedDataProducts = new HashSet<>();
        Set<MSGenericNameModel> msGenericNameModels = new HashSet<>();
        Set<String> uniqueGenericNames = new HashSet<>();
        List<String> withoutGenericNames = new ArrayList<>();


        // we do model mapping from msGenericNameRow to msGenericNameModel (not need to import mapstruct or etc)
        genericNameRows.forEach(msGenericNameRow -> {
            MSGenericNameModel msGenericNameModel = new MSGenericNameModel(msGenericNameRow.getId(), msGenericNameRow.getName(), msGenericNameRow.getCategory1(),
                    msGenericNameRow.getCategory2(), msGenericNameRow.getCategory3(), msGenericNameRow.getCategory4());

            msGenericNameModels.add(msGenericNameModel);
            uniqueGenericNames.add(msGenericNameModel.getName());
        });

        msRegistryGenericNameModel.setMsUniqueGenericNameModelSize(uniqueGenericNames.size());
        msRegistryGenericNameModel.setGenericNameSize(msGenericNameModels.size());

        productRows.forEach(msProductRow -> {
            MSConsolidatedDataProducts consolidatedDataProduct = new MSConsolidatedDataProducts();
            consolidatedDataProduct.setId(msProductRow.getId());
            consolidatedDataProduct.setName(msProductRow.getName());
            consolidatedDataProduct.setPrimaryCategory(msProductRow.getPrimaryCategory());
            consolidatedDataProduct.setLicenceModel(new MSLicenceModel(msProductRow.getLicenseHolderId(), msProductRow.getLicenseHolderName(), msProductRow.getLicenseHolderAddress()));
            consolidatedDataProduct.setProducerModel(new MSProducerModel(msProductRow.getProducerId(), msProductRow.getProducerName(), msProductRow.getProducerAddress()));

            // now genericName
            Optional<MSGenericNameModel> msGenericNameModel = msGenericNameModels.stream().filter(genericNameModel -> msProductRow.getGenericName().equals(genericNameModel.getName())).findFirst();

            if (msGenericNameModel.isPresent()) {

                consolidatedDataProduct.setMsGenericNameModel(new MSGenericNameModel(msGenericNameModel.get().getId(), msGenericNameModel.get().getName()
                        , msGenericNameModel.get().getCategory1(), msGenericNameModel.get().getCategory2(), msGenericNameModel.get().getCategory3(), msGenericNameModel.get().getCategory4()));

                msConsolidatedDataProducts.add(consolidatedDataProduct);
            } else {
                withoutGenericNames.add(msProductRow.getGenericName());
            }
        });

        msRegistryGenericNameModel.setConsolidatedDataProducts(msConsolidatedDataProducts);
        msRegistryGenericNameModel.setWithoutGenericNames(withoutGenericNames.size());

        return msRegistryGenericNameModel;
    }

    /* MS Generic Names */

    /**
     * Method find the number of unique generic names.
     *
     * @return
     */
    public Object numberOfUniqueGenericNames() throws RuntimeException {
        return msRegistryGenericNameModel.getMsUniqueGenericNameModelSize();
    }

    /**
     * Method finds the number of generic names which are duplicated.
     *
     * @return
     */
    public Object numberOfDuplicateGenericNames() throws RuntimeException {
        return msRegistryGenericNameModel.getGenericNameSize() - msRegistryGenericNameModel.getMsUniqueGenericNameModelSize();
    }

    /* MS Products */

    /**
     * Method finds the number of products which have a generic name which can be
     * determined.
     *
     * @return
     */
    public Object numberOfMSProductsWithGenericName() throws RuntimeException {
        return msRegistryGenericNameModel.getConsolidatedDataProducts().size();
    }

    /**
     * Method finds the number of products which have a generic name which can NOT
     * be determined.
     *
     * @return
     */
    public Object numberOfMSProductsWithoutGenericName() throws RuntimeException {
        return msRegistryGenericNameModel.getWithoutGenericNames();
    }

    /**
     * Method finds the name of the company which is both the producer and license holder for the
     * most number of products.
     *
     * @return
     */
    public Object nameOfCompanyWhichIsProducerAndLicenseHolderForMostNumberOfMSProducts() throws RuntimeException {
        Map<String, Long> counts = msRegistryGenericNameModel.getConsolidatedDataProducts().stream()
                .filter(msConsolidatedDataProduct -> msConsolidatedDataProduct.getLicenceModel().getLicenseHolderName().equals(msConsolidatedDataProduct.getProducerModel().getProducerName()))
                .collect(Collectors.groupingBy(msConsolidatedDataProduct -> msConsolidatedDataProduct.getProducerModel().getProducerName(), Collectors.counting()));

        return counts.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }

    /**
     * Method finds the number of products whose producer name starts with
     * <i>companyName</i>.
     *
     * @param companyName
     * @return
     */
    public Object numberOfMSProductsByProducerName(String companyName) throws RuntimeException {
        return msRegistryGenericNameModel.getConsolidatedDataProducts()
                .stream()
                .filter(msConsolidatedDataProduct -> msConsolidatedDataProduct.getProducerModel().getProducerName().toLowerCase().startsWith(companyName.toLowerCase())
                ).collect(Collectors.toList()).size();
    }

    /**
     * Method finds the products whose generic name has the category of interest.
     * 
     * @param category
     * @return
     */
    public Set<MSConsolidatedDataProducts> findMSProductsWithGenericNameCategory(String category) throws RuntimeException{
       return msRegistryGenericNameModel.getConsolidatedDataProducts()
                .stream()
                .filter(msConsolidatedDataProduct -> msConsolidatedDataProduct.getPrimaryCategory().equals(category))
                .collect(Collectors.toSet());
    }
}
