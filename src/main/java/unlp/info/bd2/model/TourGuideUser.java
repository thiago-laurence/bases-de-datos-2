package unlp.info.bd2.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class TourGuideUser extends User {

    private String education;

    @ManyToMany(mappedBy = "tourGuideList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
    public boolean isDeleteable(){
        return (super.isDeleteable() && this.getRoutes().isEmpty());
    }
}
