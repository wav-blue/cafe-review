package kr.sujin.cafereview.review.repository.dto;

import kr.sujin.cafereview.constant.CafeRegion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSearchDto {
    
    private String searchDateType;

    private CafeRegion region;

    private String searchBy;

    private String searchKeyword;

}
