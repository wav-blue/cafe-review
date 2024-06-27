package kr.sujin.cafereview.dto.review;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import kr.sujin.cafereview.lib.constant.CafeRegion;
import kr.sujin.cafereview.lib.constant.TagType;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewTag;
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

    // 작성자 정보
    private String email;

    private String writerName;

    // 이미지 정보
    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

    // 태그 정보
    private List<TagType> reviewTagList = new ArrayList<>();

    // 날짜 정보
    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ReviewReadDetailDto of(Review review) {return modelMapper.map(review, ReviewReadDetailDto.class);}

}
