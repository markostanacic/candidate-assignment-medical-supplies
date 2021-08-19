package ch.aaap.ca.be.medicalsupplies.model;

import java.io.Serializable;

public class MSProducerModel implements Serializable {

    private static final long serialVersionUID = -6148036808391539807L;
    private String producerId;
    private String producerName;
    private String producerAddress;

    public MSProducerModel(String producerId, String producerName, String producerAddress) {
        this.producerId = producerId;
        this.producerName = producerName;
        this.producerAddress = producerAddress;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getProducerAddress() {
        return producerAddress;
    }

    public void setProducerAddress(String producerAddress) {
        this.producerAddress = producerAddress;
    }
}
