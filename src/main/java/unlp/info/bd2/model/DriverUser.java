package unlp.info.bd2.model;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class DriverUser extends User {

    private String expedient;

    @ManyToMany(mappedBy = "driverList")
    private List<Route> routes;

    public DriverUser() {}

    public DriverUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String expedient){
        super(username, password, name, email, birthdate, phoneNumber);
        this.expedient = expedient;
    }

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRouts(List<Route> routs) {
        this.routes = routs;
    }
}
