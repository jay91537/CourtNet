package com.dbcourtnet.court;

import com.dbcourtnet.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final LocationRepository locationRepository;

}
