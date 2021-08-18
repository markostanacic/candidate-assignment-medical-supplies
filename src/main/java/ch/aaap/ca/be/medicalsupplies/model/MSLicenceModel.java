package ch.aaap.ca.be.medicalsupplies.model;

import java.io.Serializable;

public class MSLicenceModel implements Serializable {

    private static final long serialVersionUID = -7121093178291815265L;
    private String licenseHolderId;
    private String licenseHolderName;
    private String licenseHolderAddress;

    public MSLicenceModel(String licenseHolderId, String licenseHolderName, String licenseHolderAddress) {
        this.licenseHolderId = licenseHolderId;
        this.licenseHolderName = licenseHolderName;
        this.licenseHolderAddress = licenseHolderAddress;
    }

    public String getLicenseHolderId() {
        return licenseHolderId;
    }

    public void setLicenseHolderId(String licenseHolderId) {
        this.licenseHolderId = licenseHolderId;
    }

    public String getLicenseHolderName() {
        return licenseHolderName;
    }

    public void setLicenseHolderName(String licenseHolderName) {
        this.licenseHolderName = licenseHolderName;
    }

    public String getLicenseHolderAddress() {
        return licenseHolderAddress;
    }

    public void setLicenseHolderAddress(String licenseHolderAddress) {
        this.licenseHolderAddress = licenseHolderAddress;
    }
}
