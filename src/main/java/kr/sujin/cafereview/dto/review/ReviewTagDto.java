package kr.sujin.cafereview.dto.review;

import kr.sujin.cafereview.lib.constant.TagType;
import lombok.Getter;
import lombok.Setter;

import org.modelmapper.ModelMapper;

@Setter
@Getter
public class ReviewTagDto {

    private static ModelMapper modelMapper = new ModelMapper();

    private TagType tagType;

    public static ReviewTagDto of(String reviewTag){
        return modelMapper.map(reviewTag, ReviewTagDto.class);
    }
}
