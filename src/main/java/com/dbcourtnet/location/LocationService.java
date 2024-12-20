package com.dbcourtnet.location;

import com.dbcourtnet.location.dto.LocationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public Location findByLocationName(LocationRequestDTO locationRequest) {

        if(locationRequest.getLocationName() == null ) {
            throw new IllegalArgumentException("없는 구장입니다.");
        }

        return locationRepository.findByLocationName(locationRequest.getLocationName());
    }

}
