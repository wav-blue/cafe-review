package kr.sujin.cafereview.dto.review;

import kr.sujin.cafereview.lib.constant.CafeRegion;
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
