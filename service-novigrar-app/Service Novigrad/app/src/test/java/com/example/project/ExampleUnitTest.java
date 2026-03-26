package com.example.project;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class ExampleUnitTest {

    @Test
    public void checkServiceName() {
        Service service = new Service("test",new ArrayList<String>(), new ArrayList<String>());
        assertEquals("test", service.getName());
    }
    @Test
    public void checkServiceFormFields() {
        ArrayList<String> formFields = new ArrayList<String>();
        formFields.add("First Name :");
        formFields.add("Last Name :");
        formFields.add("Phone Number :");
        formFields.add("Adress :");
        Service service = new Service("",formFields, new ArrayList<String>());
        assertEquals(formFields, service.getFormFields());
    }

    @Test
    public void checkServiceDocuments() {
        ArrayList<String> docs = new ArrayList<String>();
        docs.add("Driver License");
        Service service = new Service("",new ArrayList<String>(), docs);
        assertEquals(docs, service.getRequiredDocuments());
    }
    @Test
    public void  checkServiceFormFieldsSetters() {
        ArrayList<String> formFields = new ArrayList<String>();
        formFields.add("First Name :");
        formFields.add("Last Name :");
        formFields.add("Phone Number :");
        formFields.add("Adress :");
        Service service = new Service("",new ArrayList<String>(), new ArrayList<String>());
        service.setFormFields(formFields);
        assertEquals(formFields, service.getFormFields());
    }

    @Test
    public void  checkServiceDocumentSetters() {
        ArrayList<String> docs = new ArrayList<String>();
        docs.add("Driver License");
        Service service = new Service("",new ArrayList<String>(), new ArrayList<String>());
        service.setRequiredDocuments(docs);
        assertEquals(docs, service.getRequiredDocuments());
    }

    @Test
    public void CheckOfferedServicesGetter()
    {
        Service service = new Service("a",new ArrayList<String>(), new ArrayList<String>());
        ArrayList<Service> services = new ArrayList<>();
        EmployeeAccount employee = new EmployeeAccount("1","2","3","4",new ArrayList<>());
        employee.addOfferedService(service);
        services.add(employee.getOfferedServices().get(0));
        services.add(service);

        assertEquals(services, employee.getOfferedServices());
    }
    @Test
    public void ServiceRatingGetter()
    {
        Service service = new Service("a",new ArrayList<String>(), new ArrayList<String>());
        service.addRating(5);
        service.addRating(3);
        assertEquals(service.getRating(),(double) (5+3)/2,0.1);
    }
    @Test
    public void ServiceRequestGetter()
    {
        Service service = new Service("a",new ArrayList<String>(), new ArrayList<String>());
        ServiceRequest rq = new ServiceRequest(service,new ArrayList<String>(), new ArrayList<String>(),"Pending","test");
        assertEquals(rq.getService(),service);
    }
    @Test
    public void ServiceFormFieldsGetter()
    {
        Service service = new Service("a",new ArrayList<String>(), new ArrayList<String>());
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        ServiceRequest rq = new ServiceRequest(service,list,new ArrayList<String>(),"Pending","test");
        assertEquals(rq.getFormFieldValues(),list);
    }
    @Test
    public void ServiceDocFieldsGetter()
    {
        Service service = new Service("a",new ArrayList<String>(), new ArrayList<String>());
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        ServiceRequest rq = new ServiceRequest(service,new ArrayList<String>(),list,"Pending","test");
        assertEquals(rq.getDocumentValues(),list);
    }
    @Test
    public void ServiceStatusGetter()
    {
        Service service = new Service("a",new ArrayList<String>(), new ArrayList<String>());
        ServiceRequest rq = new ServiceRequest(service,new ArrayList<String>(), new ArrayList<String>(),"Pending","test");
        assertEquals(rq.getStatus(),"Pending");
    }
    @Test
    public void ServiceEmployeeIDGetter()
    {
        Service service = new Service("a",new ArrayList<String>(), new ArrayList<String>());
        ServiceRequest rq = new ServiceRequest(service,new ArrayList<String>(), new ArrayList<String>(),"Pending","test");
        assertEquals(rq.getEmployeeIdentifier(),"test");
    }

    @Test
    public void CheckOfferedServicesRemover()
    {
        Service service = new Service("a",new ArrayList<String>(), new ArrayList<String>());
        ArrayList<Service> services = new ArrayList<>();
        EmployeeAccount employee = new EmployeeAccount("1","2","3","4",new ArrayList<>());
        employee.addOfferedService(service);
        services.add(employee.getOfferedServices().get(0));
        employee.removeOfferedService(service);
        assertEquals(services, employee.getOfferedServices());
    }
}