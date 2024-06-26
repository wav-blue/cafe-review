package kr.sujin.cafereview.dto.review;

import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.entity.ReviewTag;
import lombok.Getter;
import lombok.Setter;

import org.modelmapper.ModelMapper;

@Setter
@Getter
public class ReviewTagDto {

    private static ModelMapper modelMapper = new ModelMapper();

    private Long reviewId;

    private Long tagId;


    public static ReviewTagDto of(ReviewTag reviewTag){
        return modelMapper.map(reviewTag, ReviewTagDto.class);
    }
}
