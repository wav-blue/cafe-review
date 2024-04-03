package kr.sujin.cafereview.dto;

import com.querydsl.core.annotations.QueryProjection;

import kr.sujin.cafereview.constant.CafeRegion;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewReadRandomDto {
    private Long id;

    private String cafeNm;

    private String imgUrl;

    @QueryProjection
    public ReviewReadRandomDto(Long id, String cafeNm, String imgUrl){
        this.id = id;
        this.cafeNm = cafeNm;
        this.imgUrl = imgUrl;
    }
}
