package kr.sujin.cafereview.dto;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewReadDetailDto {
    private Long id;

    private String cafeNm;

    private String menuNm;

    private Integer rating;

    private String reviewDetail;

    private CafeRegion cafeRegion;

    //이미지 정보
    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public static ReviewReadDetailDto of(Review review) {return modelMapper.map(review, ReviewReadDetailDto.class);}

}
