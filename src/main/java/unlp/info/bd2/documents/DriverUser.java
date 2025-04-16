package unlp.info.bd2.documents;

import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DriverUser extends User{

    @Field
    private String expedient;

    @DBRef(lazy = true)
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
