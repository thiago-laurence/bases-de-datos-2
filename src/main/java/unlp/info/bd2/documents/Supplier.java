package unlp.info.bd2.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "suppliers")
public class Supplier {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("business_name")
    private String businessName;

    @Indexed(unique = true)
    @Field("authorization_number")
    private String authorizationNumber;

    @DBRef(lazy = true)
    private List<Service> services;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Supplier(String businessName, String authorizationNumber) {
        this.setBusinessName(businessName);
        this.setAuthorizationNumber(authorizationNumber);
        this.setServices(new ArrayList<Service>());
    }

    public Supplier(){
        this.services = new ArrayList<Service>();
    }

    public void addService(Service service) {
        if (!this.getServices().contains(service)){
            this.getServices().add(service);
        }
    }

}