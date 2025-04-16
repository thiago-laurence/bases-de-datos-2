package unlp.info.bd2.repositories.mongo;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DuplicateKeyException;
import com.mongodb.MongoWriteException;

import unlp.info.bd2.documents.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class MongoToursRepositoryImpl implements MongoToursRepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void remove(Object object) {
        this.mongoTemplate.remove(object);
    }

    @Override
    public Object merge(Object object) throws ToursException {
        try{
            return this.mongoTemplate.save(object);
        }catch (DuplicateKeyException | MongoWriteException e){
            throw new ToursException("Constraint Violation");
        }
    }

    @Override
    public void save(Object object) throws ToursException {
        try{
            this.mongoTemplate.insert(object);
        }catch (DuplicateKeyException | MongoWriteException e){
            throw new ToursException("Constraint Violation");
        }
    }

    @Override
    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(this.mongoTemplate.findById(id, User.class));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(
                this.mongoTemplate.findOne(new Query(Criteria.where("username").is(username)), User.class)
        );
    }

    @Override
    public Optional<TourGuideUser> getTourGuideUserByUsername(String username) {
        return Optional.ofNullable(
                this.mongoTemplate.findOne(new Query(Criteria.where("username").is(username).and("user_type").is("TourGuideUser")), TourGuideUser.class)
        );
    }

    @Override
    public Optional<DriverUser> getDriverUserByUsername(String username) {
        return Optional.ofNullable(
                this.mongoTemplate.findOne(new Query(Criteria.where("username").is(username).and("user_type").is("DriverUser")), DriverUser.class)
        );
    }

    @Override
    public List<Stop> getStopByNameStart(String name) {
        return (
                mongoTemplate.find(new Query(Criteria.where("name").regex("^" + name)), Stop.class)
        );
    }

    @Override
    public Optional<Route> getRouteById(String id) {
        return (
            Optional.ofNullable(mongoTemplate.findById(id, Route.class))
        );
    }

    @Override
    public Optional<Service> getServiceById(String id) {
        return (
                Optional.ofNullable(mongoTemplate.findById(id, Service.class))
        );
    }

    @Override
    public List<TourGuideUser> getTourGuidesWithRating1() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("reviews", "review.$id", "_id", "review"),
                Aggregation.unwind("review"),
                Aggregation.match(Criteria.where("review.rating").is(1)),
                Aggregation.lookup("routes", "route.$id", "_id", "route"),
                Aggregation.unwind("route"),
                Aggregation.unwind("route.tourGuideList"),
                Aggregation.lookup("users", "route.tourGuideList.$id", "_id", "guide"),
                Aggregation.unwind("guide"),
                Aggregation.group("guide._id").first("guide").as("guide"),
                Aggregation.replaceRoot("guide")
        );

        return (
                mongoTemplate.aggregate(agg, "purchases", TourGuideUser.class).getMappedResults()
        );
    }

    @Override
    public Long getMaxStopOfRoutes() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project().and("stops").size().as("stopCount"),
                Aggregation.sort(Sort.Direction.DESC, "stopCount"),
                Aggregation.limit(1),
                Aggregation.project("stopCount")
        );

        AggregationResults<Document> result = mongoTemplate.aggregate(agg, "routes", Document.class);
        Document doc = result.getUniqueMappedResult();

        return (
                (doc != null) ? doc.getInteger("stopCount") : 0L
        );
    }

    @Override
    public List<Route> getRoutesBelowPrice(float price) {
        return (
                mongoTemplate.find(new Query(Criteria.where("price").lt(price)), Route.class)
        );
    }

    @Override
    public List<Route> getRoutesWithStop(Stop stop) {
        return (
                mongoTemplate.find(new Query(Criteria.where("stops").is(stop.getId())), Route.class)
        );
    }

    @Override
    public List<Route> getTop3RoutesWithMaxRating() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("review").ne(null)),
                Aggregation.lookup("reviews", "review.$id", "_id", "review"),
                Aggregation.unwind("review"),
                Aggregation.group("route").avg("review.rating").as("avgRating"),
                Aggregation.sort(Sort.Direction.DESC, "avgRating"),
                Aggregation.limit(3),
                Aggregation.lookup("routes", "_id.$id", "_id", "route"),
                Aggregation.unwind("route"),
                Aggregation.replaceRoot("route")
        );

        return (
                mongoTemplate.aggregate(agg, "purchases", Route.class).getMappedResults()
        );
    }

    @Override
    public List<Route> getRoutsNotSell() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("purchases", "_id", "route.$id", "related_purchases"),
                Aggregation.match(Criteria.where("related_purchases").is(Collections.emptyList()))
        );

        return (
                mongoTemplate.aggregate(agg, "routes", Route.class).getMappedResults()
        );
    }

    @Override
    public Optional<Purchase> getPurchaseByCode(String code) {
        return (
            Optional.ofNullable(this.mongoTemplate.findOne(new Query(Criteria.where("code").is(code)), Purchase.class))
        );
    }

    @Override
    public long countUsersRouteInDate(Date date, Route route) {
        Query query = new Query(
                Criteria.where("date").is(date)
                        .and("route_id").is(route.getId())
        );

        return (
                mongoTemplate.count(query, Purchase.class)
        );
    }

    @Override
    public Optional<Supplier> getSupplierById(String id) {
        return (
            Optional.ofNullable(this.mongoTemplate.findById(id, Supplier.class))
        );
    }

    @Override
    public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
        Query query = new Query(Criteria.where("authorizationNumber").is(authorizationNumber));

        return (
                Optional.ofNullable(mongoTemplate.findOne(query, Supplier.class))
        );
    }

    @Override
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("services", "service.$id", "_id", "service"),
                Aggregation.unwind("service"),
                Aggregation.lookup("suppliers", "service.supplier.$id", "_id", "supplier"),
                Aggregation.unwind("supplier"),
                Aggregation.group("supplier._id").first("supplier").as("supplier").count().as("purchaseCount"),
                Aggregation.sort(Sort.Direction.DESC, "purchaseCount"),
                Aggregation.limit(n),
                Aggregation.replaceRoot("supplier")
        );

        return (
            mongoTemplate.aggregate(agg, "items_services", Supplier.class).getMappedResults()
        );
    }

    @Override
    public Optional<Service> getServiceByNameAndSupplierId(String name, String supplierId) {
        Query query = new Query(Criteria.where("name").is(name).and("supplier_id").is(supplierId));

        return (
            Optional.ofNullable(mongoTemplate.findOne(query, Service.class))
        );
    }

    @Override
    public Service getMostDemandedService() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("service").sum("quantity").as("totalQuantity"),
                Aggregation.sort(Sort.Direction.DESC, "totalQuantity"),
                Aggregation.limit(1),
                Aggregation.lookup("services", "_id.$id", "_id", "service"),
                Aggregation.unwind("service"),
                Aggregation.replaceRoot("service")
        );

        return (
            mongoTemplate.aggregate(agg, "items_services", Service.class).getUniqueMappedResult()
        );
    }

    @Override
    public List<Service> getServiceNoAddedToPurchases() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("items_services", "_id", "service.$id", "related_items"),
                Aggregation.match(Criteria.where("related_items").is(Collections.emptyList()))
        );

        return (
            mongoTemplate.aggregate(agg, "services", Service.class).getMappedResults()
        );
    }

    @Override
    public List<User> findTop5UsersByNumberOfPurchases() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("$user.$id").count().as("purchaseCount"),
                Aggregation.sort(Sort.Direction.DESC, "purchaseCount"),
                Aggregation.limit(5),
                Aggregation.lookup("users", "_id", "_id", "user"),
                Aggregation.unwind("user"),
                Aggregation.replaceRoot("user")
        );

        return (
                mongoTemplate.aggregate(agg, "purchases", User.class).getMappedResults()
        );
    }

    @Override
    public long countPurchasesBetweenDates(Date startDate, Date endDate) {
        return (
            mongoTemplate.count(new Query(Criteria.where("date").gte(startDate).lte(endDate)), Purchase.class)
        );
    }

    @Override
    public List<Purchase> getTop10MoreExpensivePurchasesInServices() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("items_services", "_id", "purchase.$id", "items"),
                Aggregation.match(Criteria.where("items.0").exists(true)),
                Aggregation.sort(Sort.Direction.DESC, "total_price"),
                Aggregation.limit(10)
        );

        return (
            mongoTemplate.aggregate(agg, "purchases", Purchase.class).getMappedResults()
        );
    }

    @Override
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        User user = mongoTemplate.findOne(
                new Query(Criteria.where("username").is(username)),
                User.class
        );
        if (user == null) return List.of();

        return (
            mongoTemplate.find(new Query(Criteria.where("user_id").is(user.getId())), Purchase.class)
        );
    }

    @Override
    public List<User> getUserSpendingMoreThan(float mount) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("total_price").gte(mount)),
                Aggregation.lookup("users", "user.$id", "_id", "user"),
                Aggregation.unwind("user"),
                Aggregation.replaceRoot("user"),
                Aggregation.group("_id").first("$$ROOT").as("user"),
                Aggregation.replaceRoot("user")
        );

        return (
                mongoTemplate.aggregate(agg, "purchases", User.class).getMappedResults()
        );
    }
}
