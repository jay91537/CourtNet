package com.dbcourtnet.location;

import com.dbcourtnet.dto.locationdto.LocationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public List<Location> findByAddress(String address) {

        if(address == null) {
            throw new IllegalArgumentException("유효하지 않은 주소 입니다.");
        }

        return locationRepository.findByAddressContaining(address);

    }

    public Optional<Location> findLocationById(Long id) {

        if(id == null) {
            throw new IllegalArgumentException("구장이 존재하지 않습니다.");
        }

        return locationRepository.findById(id);
    }
}
