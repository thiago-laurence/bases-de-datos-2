package unlp.info.bd2.documents;

import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TourGuideUser extends User {

    @Field
    private String education;

    @DBRef(lazy = true)
    private List<Route> routes;

    public TourGuideUser() {}

    public TourGuideUser(String username, String password, String name, String email, Date birthdate, String phoneNumber, String education) {
        super(username, password, name, email, birthdate, phoneNumber);
        this.setEducation(education);
        this.setRoutes(new ArrayList<Route>());
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public void addRoute(Route route) {
        if (!this.routes.contains(route)) {
            this.routes.add(route);
        }
    }

    @Override
    public boolean isDeletable(){
        return (super.isDeletable() && this.getRoutes().isEmpty());
    }
}
