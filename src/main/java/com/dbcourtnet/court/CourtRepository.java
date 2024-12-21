package com.dbcourtnet.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {


    List<Court> findAllByLocationId(Long LocationId);


}
