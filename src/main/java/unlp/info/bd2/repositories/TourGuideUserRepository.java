package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unlp.info.bd2.model.Purchase;
import unlp.info.bd2.model.TourGuideUser;

import java.util.List;

@Repository
public interface TourGuideUserRepository extends UserRepository {

    @Query ("SELECT t FROM Purchase p JOIN p.review rv JOIN p.route.tourGuideList t WHERE rv.rating = 1")
    List<TourGuideUser> getTourGuidesWithRating1();
}
