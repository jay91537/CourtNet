package com.dbcourtnet.controller;

import com.dbcourtnet.court.CourtService;
import com.dbcourtnet.court.CourtTexture;
import com.dbcourtnet.dto.locationdto.LocationResponseDTO;
import com.dbcourtnet.location.Location;
import com.dbcourtnet.location.LocationService;
import com.dbcourtnet.dto.locationdto.ControllerLocationRequestDTO;
import com.dbcourtnet.login.session.SessionConst;
import com.dbcourtnet.review.Review;
import com.dbcourtnet.review.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/findLocation")
public class LocationController {

    private final LocationService locationService;
    private final CourtService courtService;
    private final ReviewService reviewService;

    // 특정 장소의 모든 구장 검색
    @GetMapping
    public ResponseEntity<List<LocationResponseDTO>> searchLocations(@RequestParam(required = false) String address,
                                                                     HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if(userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        List<Location> locations = locationService.findByAddress(address);

        List<LocationResponseDTO> response = locations.stream()
                .map(LocationResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    // 검색한 장소의 세부사항
    @GetMapping("/{locationid}")
    public ResponseEntity<LocationResponseDTO> getLocationDetail(@PathVariable Long locationid,
                                                                 HttpServletRequest request) {

        Long userId = (Long) request.getAttribute("userId");

        if(userId == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Location location = locationService.findLocationById(locationid)
                .orElseThrow(() -> new IllegalArgumentException("구장을 찾을 수 없습니다."));

        return ResponseEntity.ok(new LocationResponseDTO(
                location,
                courtService.findCourtTextures(location.getId()),
                reviewService.findAllByLocationId(locationid)
        ));

    }
}