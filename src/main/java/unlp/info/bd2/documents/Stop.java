package unlp.info.bd2.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "stops")
public class Stop {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @Field
    private String description;

    @DBRef(lazy = true)
    private List<Route> routes;

    public Stop(){ }

    public Stop(String name, String description) {
        this.setName(name);
        this.setDescription(description);
        this.setRoutes(new ArrayList<Route>());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Route> getRoutes() { return this.routes; }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public void addRoute(Route route) {
        if (!this.routes.contains(route)) {
            this.routes.add(route);
        }
    }
}
