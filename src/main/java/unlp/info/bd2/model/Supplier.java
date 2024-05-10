package unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_name", nullable = false, unique = true, length = 30)
    private String businessName;
    @Column(name = "authorization_number", nullable = false, unique = true, length = 10)
    private String authorizationNumber;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE })
    private List<Service> services;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Supplier(){ }

    public void addService(Service service) {
        if (!this.getServices().contains(service)){
            this.getServices().add(service);
        }
    }

}
