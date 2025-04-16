package unlp.info.bd2.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 10)
    private float price;
    @Column(name = "total_km", nullable = false, length = 6)
    private float totalKm;
    @Column(name = "max_number_users", nullable = false, length = 3)
    private int maxNumberUsers;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "route_stop",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "stop_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "stop_id"})
    )
    private List<Stop> stops;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "route_driver",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "driver_id"})
    )
    private List<DriverUser> driverList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "route_guideTour",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "guideTour_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "guideTour_id"})
    )
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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