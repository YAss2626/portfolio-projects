package com.example.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

public class Service implements Parcelable {
    private String name;
    private List<String> formFields;
    private List<String> requiredDocuments;

    double rating;

    List<Service> serviceList;

    // Constructors, getters, and setters

    public Service(String name, List<String> formFields, List<String> requiredDocuments) {

        this.name = name;
        this.formFields = formFields;
        this.requiredDocuments = requiredDocuments;
        rating = 0;
        serviceList = new ArrayList<>();
    }

    // Getter and Setter methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<String> formFields) {
        this.formFields = formFields;
    }

    public List<String> getRequiredDocuments() {
        return requiredDocuments;
    }

    public void setRequiredDocuments(List<String> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public void addRating(int rate) {
        rating = rating + rate;
        if(rating > 5){rating = rating/2;}
    }

    public boolean containsService(Service service) {
        return serviceList.contains(service);
    }

    public double getRating() {
        return rating;
    }

    public Service() {
    }

    // Method to convert data to a map for Firebase
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("formFields", formFields);
        map.put("requiredDocuments", requiredDocuments);
        return map;
    }

    protected Service(Parcel in) {
        name = in.readString();
        formFields = in.createStringArrayList();
        requiredDocuments = in.createStringArrayList();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeStringList(formFields);
        dest.writeStringList(requiredDocuments);
    }
}