package com.example.ijobs.viewmodels;

public class ServiceOfferListItemViewModel {
    private String serviceName;
    private boolean isSelected;

    public ServiceOfferListItemViewModel(String serviceName, boolean isSelected){
        this.serviceName = serviceName;
        this.isSelected = isSelected;
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
