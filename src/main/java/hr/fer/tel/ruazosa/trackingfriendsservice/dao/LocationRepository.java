package hr.fer.tel.ruazosa.trackingfriendsservice.dao;

import hr.fer.tel.ruazosa.trackingfriendsservice.models.Location;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends PagingAndSortingRepository<Location, String> {

    Location findByUserId(String userId);

}
