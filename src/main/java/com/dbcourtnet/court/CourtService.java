package com.dbcourtnet.court;

import com.dbcourtnet.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtService {

    private final LocationRepository locationRepository;
    private final CourtRepository courtRepository;

    public List<Court> findAllByLocationId(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("장소가 존재하지 않습니다.");
        }

        return courtRepository.findAllByLocationId(id);
    }

    public List<CourtTexture> findCourtTextures(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("장소가 존재하지 않습니다.");
        }

        List<Court> courtList = courtRepository.findAllByLocationId(id);
        List<CourtTexture> courtTextures = new ArrayList<>();

        int timberCount = 0;
        int asphaltCount = 0;
        int urethaneCount = 0;
        int plasticCount = 0;

        for (Court court : courtList) {
            if ((timberCount == 0) && (court.getCourtTexture() == CourtTexture.TIMBER)) {
                courtTextures.add(court.getCourtTexture());
                timberCount++;
            }
            if ((asphaltCount == 0) && court.getCourtTexture() == CourtTexture.ASPHALT) {
                courtTextures.add(court.getCourtTexture());
                asphaltCount++;
            }
            if ((urethaneCount == 0) && court.getCourtTexture() == CourtTexture.URETHANE) {
                courtTextures.add(court.getCourtTexture());
                urethaneCount++;
            }
            if ((plasticCount == 0) && court.getCourtTexture() == CourtTexture.PLASTIC) {
                courtTextures.add(court.getCourtTexture());
                plasticCount++;
            }

        }

        return courtTextures;
    }
}
