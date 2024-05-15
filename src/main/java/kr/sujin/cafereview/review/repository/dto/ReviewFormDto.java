package kr.sujin.cafereview.review.repository.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import kr.sujin.cafereview.constant.CafeRegion;

//리뷰 데이터 정보를 전달

import kr.sujin.cafereview.entity.Review;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ReviewFormDto {
    @Nullable
    private Long id;

    @Nullable
    private String email;

    @NotEmpty(message = "카페 이름을 입력해주세요")
    private String cafeNm;

    @NotEmpty(message = "추천 메뉴를 입력해주세요")
    private String menuNm;

    @NotNull(message = "평점을 입력해주세요")
    private Integer rating;

    @Nullable
    private String reviewDetail;

    @NotNull
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
