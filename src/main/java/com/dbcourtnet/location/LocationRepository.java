package com.dbcourtnet.location;

import com.dbcourtnet.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLocationName(String locationName);

    List<Location> findByAddressContaining(String address);
}
