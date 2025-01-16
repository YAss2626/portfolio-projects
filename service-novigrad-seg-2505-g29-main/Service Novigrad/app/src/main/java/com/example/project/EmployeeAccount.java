package com.example.project;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeAccount extends Account{

    public String branchAdresse = "";
    public String branchPhoneNumber = "";
    public String mondayOpenHours = "";
    public String tuesdayOpenHours = "";
    public String wednesdayOpenHours = "";
    public String thursdayOpenHours = "";
    public String fridayOpenHours = "";
    public String saturdayOpenHours = "";
    public String sundayOpenHours = "";
    public String mondayCloseHours = "";
    public String tuesdayCloseHours = "";
    public String wednesdayCloseHours = "";
    public String thursdayCloseHours = "";
    public String fridayCloseHours = "";
    public String saturdayCloseHours = "";
    public String sundayCloseHours = "";
    List<Service> offeredServices;





    public EmployeeAccount(){
    }
    public EmployeeAccount(String firstName, String lastName, String email, String password, List<Service> arrayList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.offeredServices = new ArrayList<>();
        this.offeredServices.add(new Service("Default",new ArrayList<String>(), new ArrayList<String>()));
    }


    // Add a service to the offered services
    public void addOfferedService(Service service) {
        offeredServices.add(service);
    }

    // Remove a service from the offered services
    public void removeOfferedService(Service service) {
        offeredServices.remove(service);
    }

    // Get the list of offered services
    public List<Service> getOfferedServices() {
        return offeredServices;
    }

    public List<String> getOfferedServicesNames() {
        ArrayList<String> serviceNames = new ArrayList<String>();
        for(Service service : offeredServices){
            serviceNames.add(service.getName());
        }
        return serviceNames;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("email", email);
        result.put("password", password);
        result.put("offeredServices", offeredServices); // Assuming Service class is Serializable or Parcelable

        return result;
    }



}
