package kr.sujin.cafereview.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import kr.sujin.cafereview.constant.CafeRegion;

//리뷰 데이터 정보를 전달

import kr.sujin.cafereview.entity.Review;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ReviewFormDto {
    private Long id;

    private String email;

    private String cafeNm;

    private String menuNm;

    private Integer rating;

    private String reviewDetail;

    private CafeRegion cafeRegion;

    //음료를 저장한 후 수정할 때 이미지 정보를 저장
    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    private List<Long> reviewImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Review createReview(){
        return modelMapper.map(this, Review.class);
    }

    public static ReviewFormDto of(Review review) {return modelMapper.map(review, ReviewFormDto.class);}
}
