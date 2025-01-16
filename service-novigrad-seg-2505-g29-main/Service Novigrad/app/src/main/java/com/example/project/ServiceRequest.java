package com.example.project;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ServiceRequest implements Parcelable {
    private Service service;
    private List<String> formFieldValues;
    private List<String> documentValues;
    private String status;
    private String employeeIdentifier;

    // Constructors, getters, and setters

    public ServiceRequest() {
        // Default constructor required for Firebase
    }

    public ServiceRequest(Service service, List<String> formFieldValues, List<String> documentValues, String status, String employeeIdentifier) {
        this.service = service;
        this.formFieldValues = formFieldValues;
        this.documentValues = documentValues;
        this.status = status;
        this.employeeIdentifier = employeeIdentifier;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<String> getFormFieldValues() {
        return formFieldValues;
    }

    public void setFormFieldValues(List<String> formFieldValues) {
        this.formFieldValues = formFieldValues;
    }

    public List<String> getDocumentValues() {
        return documentValues;
    }

    public void setDocumentValues(List<String> documentValues) {
        this.documentValues = documentValues;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployeeIdentifier() {
        return employeeIdentifier;
    }

    public void setEmployeeIdentifier(String employeeIdentifier) {
        this.employeeIdentifier = employeeIdentifier;
    }

    protected ServiceRequest(Parcel in) {
        service = in.readParcelable(Service.class.getClassLoader());
        formFieldValues = in.createStringArrayList();
        documentValues = in.createStringArrayList();
        status = in.readString();
        employeeIdentifier = in.readString();
    }

    public static final Creator<ServiceRequest> CREATOR = new Creator<ServiceRequest>() {
        @Override
        public ServiceRequest createFromParcel(Parcel in) {
            return new ServiceRequest(in);
        }

        @Override
        public ServiceRequest[] newArray(int size) {
            return new ServiceRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(service, flags);
        dest.writeStringList(formFieldValues);
        dest.writeStringList(documentValues);
        dest.writeString(status);
        dest.writeString(employeeIdentifier);
    }
}

