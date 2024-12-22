package com.dbcourtnet;

import com.dbcourtnet.court.Court;
import com.dbcourtnet.court.CourtRepository;
import com.dbcourtnet.court.CourtTexture;
import com.dbcourtnet.location.Location;
import com.dbcourtnet.review.Review;
import com.dbcourtnet.review.ReviewRepository;
import com.dbcourtnet.user.Gender;
import com.dbcourtnet.user.User;
import com.dbcourtnet.user.UserRepository;
import com.dbcourtnet.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MakeInitData {

    private final LocationRepository locationRepository;
    private final CourtRepository courtRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @PostConstruct
    public void makeLocationAndCourt() {

        // 유저 하드코딩
        User user1 = User.builder()
                .loginId("user1")
                .password("1234")
                .username("재현")
                .gender(Gender.MALE)
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .loginId("user2")
                .password("1234")
                .username("민지")
                .gender(Gender.FEMALE)
                .build();
        userRepository.save(user2);

        User user3 = User.builder()
                .loginId("user3")
                .password("1234")
                .username("문군")
                .gender(Gender.MALE)
                .build();
        userRepository.save(user3);

        User user4 = User.builder()
                .loginId("user4")
                .password("1234")
                .username("동언")
                .gender(Gender.MALE)
                .build();
        userRepository.save(user4);

        User user5 = User.builder()
                .loginId("user5")
                .password("1234")
                .username("민서")
                .gender(Gender.FEMALE)
                .build();
        userRepository.save(user5);

        // 구장 하드코딩
        Location location1 = Location.builder()
                .locationName("평촌중앙공원")
                .address("안양시 동안구")
                .courtNum(8L)
                .build();
        locationRepository.save(location1);

        Location location2 = Location.builder()
                .locationName("반포종합운동장")
                .address("서울특별시 서초구")
                .courtNum(6L)
                .build();
        locationRepository.save(location2);

        Location location3 = Location.builder()
                .locationName("송도글로벌파크")
                .address("인천광역시 연수구")
                .courtNum(10L)
                .build();
        locationRepository.save(location3);

        Location location4 = Location.builder()
                .locationName("홍익대학교운동장")
                .address("서울특별시 마포구")
                .courtNum(2L)
                .build();
        locationRepository.save(location4);

        Location location5 = Location.builder()
                .locationName("여의도공원")
                .address("서울특별시 영등포구")
                .courtNum(4L)
                .build();
        locationRepository.save(location5);

        // 리뷰 하드코딩

        Review review1 = Review.builder()
                .username("재현")
                .rating(5L)
                .text("농구하지 제일 좋은 곳입니다~")
                .date("2024-12-01")
                .user(user1)
                .location(location4)
                .build();
        reviewRepository.save(review1);

        Review review2 = Review.builder()
                .username("민지")
                .rating(4L)
                .text("재현이랑 같이 농구하니까 좋았어요~")
                .date("2024-12-02")
                .user(user2)
                .location(location4)
                .build();
        reviewRepository.save(review2);

        Review review3 = Review.builder()
                .username("문군")
                .rating(3L)
                .text("농구하기 딱좋음")
                .date("2024-12-05")
                .user(user3)
                .location(location1)
                .build();
        reviewRepository.save(review3);

        Review review4 = Review.builder()
                .username("재현")
                .rating(2L)
                .text("백보드가 너무 잘 튕겨서 별로....")
                .date("2024-12-11")
                .user(user1)
                .location(location2)
                .build();
        reviewRepository.save(review4);

        Review review5 = Review.builder()
                .username("동언")
                .rating(5L)
                .text("시간가는 줄 모르고 농구함.")
                .date("2024-11-02")
                .user(user4)
                .location(location3)
                .build();
        reviewRepository.save(review5);

        Review review6 = Review.builder()
                .username("민서")
                .rating(5L)
                .text("농구 처음하는데 재밌었다.")
                .date("2024-5-02")
                .user(user5)
                .location(location3)
                .build();
        reviewRepository.save(review6);

        // 코트 하드코딩
        // location1에 코트 8개 추가
        Court court1 = Court.builder()
                .courtTexture(CourtTexture.TIMBER)
                .location(location1)
                .build();
        courtRepository.save(court1);

        Court court2 = Court.builder()
                .courtTexture(CourtTexture.ASPHALT)
                .location(location1)
                .build();
        courtRepository.save(court2);

        Court court3 = Court.builder()
                .courtTexture(CourtTexture.URETHANE)
                .location(location1)
                .build();
        courtRepository.save(court3);

        Court court4 = Court.builder()
                .courtTexture(CourtTexture.PLASTIC)
                .location(location1)
                .build();
        courtRepository.save(court4);

        Court court5 = Court.builder()
                .courtTexture(CourtTexture.TIMBER)
                .location(location1)
                .build();
        courtRepository.save(court5);

        Court court6 = Court.builder()
                .courtTexture(CourtTexture.ASPHALT)
                .location(location1)
                .build();
        courtRepository.save(court6);

        Court court7 = Court.builder()
                .courtTexture(CourtTexture.URETHANE)
                .location(location1)
                .build();
        courtRepository.save(court7);

        Court court8 = Court.builder()
                .courtTexture(CourtTexture.PLASTIC)
                .location(location1)
                .build();
        courtRepository.save(court8);

        // Location 2에 6개 코트 추가
        Court court9 = Court.builder()
                .courtTexture(CourtTexture.ASPHALT)
                .location(location2)
                .build();
        courtRepository.save(court9);

        Court court10 = Court.builder()
                .courtTexture(CourtTexture.PLASTIC)
                .location(location2)
                .build();
        courtRepository.save(court10);

        Court court11 = Court.builder()
                .courtTexture(CourtTexture.TIMBER)
                .location(location2)
                .build();
        courtRepository.save(court11);

        Court court12 = Court.builder()
                .courtTexture(CourtTexture.URETHANE)
                .location(location2)
                .build();
        courtRepository.save(court12);

        Court court13 = Court.builder()
                .courtTexture(CourtTexture.ASPHALT)
                .location(location2)
                .build();
        courtRepository.save(court13);

        Court court14 = Court.builder()
                .courtTexture(CourtTexture.PLASTIC)
                .location(location2)
                .build();
        courtRepository.save(court14);

        // Location 3에 10개 코트 추가
        Court court15 = Court.builder()
                .courtTexture(CourtTexture.PLASTIC)
                .location(location3)
                .build();
        courtRepository.save(court15);

        Court court16 = Court.builder()
                .courtTexture(CourtTexture.ASPHALT)
                .location(location3)
                .build();
        courtRepository.save(court16);

        Court court17 = Court.builder()
                .courtTexture(CourtTexture.TIMBER)
                .location(location3)
                .build();
        courtRepository.save(court17);

        Court court18 = Court.builder()
                .courtTexture(CourtTexture.URETHANE)
                .location(location3)
                .build();
        courtRepository.save(court18);

        Court court19 = Court.builder()
                .courtTexture(CourtTexture.PLASTIC)
                .location(location3)
                .build();
        courtRepository.save(court19);

        Court court20 = Court.builder()
                .courtTexture(CourtTexture.ASPHALT)
                .location(location3)
                .build();
        courtRepository.save(court20);

        Court court21 = Court.builder()
                .courtTexture(CourtTexture.TIMBER)
                .location(location3)
                .build();
        courtRepository.save(court21);

        Court court22 = Court.builder()
                .courtTexture(CourtTexture.URETHANE)
                .location(location3)
                .build();
        courtRepository.save(court22);

        Court court23 = Court.builder()
                .courtTexture(CourtTexture.PLASTIC)
                .location(location3)
                .build();
        courtRepository.save(court23);

        Court court24 = Court.builder()
                .courtTexture(CourtTexture.ASPHALT)
                .location(location3)
                .build();
        courtRepository.save(court24);

        // Location 4에 2개 코트 추가
        Court court25 = Court.builder()
                .courtTexture(CourtTexture.URETHANE)
                .location(location4)
                .build();
        courtRepository.save(court25);

        Court court26 = Court.builder()
                .courtTexture(CourtTexture.PLASTIC)
                .location(location4)
                .build();
        courtRepository.save(court26);


        // Location 5에 4개 코트 추가
        Court court27 = Court.builder()
                .courtTexture(CourtTexture.ASPHALT)
                .location(location5)
                .build();
        courtRepository.save(court27);

        Court court28 = Court.builder()
                .courtTexture(CourtTexture.TIMBER)
                .location(location5)
                .build();
        courtRepository.save(court28);

        Court court29 = Court.builder()
                .courtTexture(CourtTexture.URETHANE)
                .location(location5)
                .build();
        courtRepository.save(court29);

        Court court30 = Court.builder()
                .courtTexture(CourtTexture.PLASTIC)
                .location(location5)
                .build();
        courtRepository.save(court30);

    }
}
