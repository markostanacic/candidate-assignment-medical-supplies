package ch.aaap.ca.be.medicalsupplies.model;

import java.io.Serializable;

public class MSConsolidatedDataProducts implements Serializable {
    private static final long serialVersionUID = -3471806883687764148L;

    private String id;
    private String name;
    private MSGenericNameModel msGenericNameModel;
    private String primaryCategory;
    private MSLicenceModel licenceModel;
    private MSProducerModel producerModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MSGenericNameModel getMsGenericNameModel() {
        return msGenericNameModel;
    }

    public void setMsGenericNameModel(MSGenericNameModel msGenericNameModel) {
        this.msGenericNameModel = msGenericNameModel;
    }

    public String getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public MSLicenceModel getLicenceModel() {
        return licenceModel;
    }

    public void setLicenceModel(MSLicenceModel licenceModel) {
        this.licenceModel = licenceModel;
    }

    public MSProducerModel getProducerModel() {
        return producerModel;
    }

    public void setProducerModel(MSProducerModel producerModel) {
        this.producerModel = producerModel;
    }
}
