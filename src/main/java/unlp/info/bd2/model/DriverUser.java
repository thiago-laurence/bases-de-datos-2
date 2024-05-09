package unlp.info.bd2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class DriverUser extends User {

    @Column(nullable = true, length = 50)
    private String expedient;

    @ManyToMany(mappedBy = "driverList", fetch = FetchType.EAGER)
    private List<Route> routes;

    public DriverUser() {}

    public DriverUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String expedient){
        super(username, password, name, email, birthdate, phoneNumber);
        this.setExpedient(expedient);
        this.setRouts(new ArrayList<Route>());
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

    public void addRoute(Route route){
        if (!this.routes.contains(route)){
            this.routes.add(route);
        }
    }
}
