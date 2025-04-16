package unlp.info.bd2.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "routes")
public class Route {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @Field
    private float price;

    @Field("total_km")
    private float totalKm;

    @Field("max_number_users")
    private int maxNumberUsers;

    @DBRef(lazy = true)
    private List<Stop> stops;

    @DBRef(lazy = true)
    private List<DriverUser> driverList;

    @DBRef(lazy = true)
    private List<TourGuideUser> tourGuideList;

    public Route(){ }

    public Route(String name, float price, float totalKm, int maxNumberUsers, List<Stop> stops) {
        this.setName(name);
        this.setPrice(price);
        this.setTotalKm(totalKm);
        this.setMaxNumberUsers(maxNumberUsers);
        this.setStops(stops);
        this.setTourGuideList(new ArrayList<TourGuideUser>());
        this.setDriverList(new ArrayList<DriverUser>());
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(float totalKm) {
        this.totalKm = totalKm;
    }

    public int getMaxNumberUsers() {
        return maxNumberUsers;
    }

    public void setMaxNumberUsers(int maxNumberUsers) {
        this.maxNumberUsers = maxNumberUsers;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public List<DriverUser> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<DriverUser> driverList) {
        this.driverList = driverList;
    }

    public List<TourGuideUser> getTourGuideList() {
        return tourGuideList;
    }

    public void setTourGuideList(List<TourGuideUser> tourGuideList) {
        this.tourGuideList = tourGuideList;
    }

    public void addDriver(DriverUser driverUser) {
        if (!this.driverList.contains(driverUser)) {
            this.driverList.add(driverUser);
        }
    }

    public void addTourGuide(TourGuideUser tourGuideUser) {
        if (!this.tourGuideList.contains(tourGuideUser)) {
            this.tourGuideList.add(tourGuideUser);
        }
    }
}
