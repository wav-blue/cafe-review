package kr.sujin.cafereview.review.repository.dto;

import kr.sujin.cafereview.entity.ReviewImg;
import lombok.Getter;
import lombok.Setter;

import org.modelmapper.ModelMapper;

//음료 이미지에 대한 데이터를 전달

@Setter
@Getter
public class ReviewImgDto {

    private static ModelMapper modelMapper = new ModelMapper();

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String isThumbnail;


    public static ReviewImgDto of(ReviewImg reviewImg){
        return modelMapper.map(reviewImg, ReviewImgDto.class);
    }
}
