package kr.sujin.cafereview.dto.review;

import com.querydsl.core.annotations.QueryProjection;

import kr.sujin.cafereview.lib.constant.CafeRegion;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewReadDto {
    private Long id;

    private String cafeNm;

    private String menuNm;

    private Integer rating;

    private CafeRegion cafeRegion;

    private String imgUrl;

    @QueryProjection
    public ReviewReadDto(Long id, String cafeNm, String menuNm, Integer rating, String reviewDetail, CafeRegion cafeRegion, String imgUrl){
        this.id = id;
        this.cafeNm = cafeNm;
        this.menuNm = menuNm;
        this.rating = rating;
        this.cafeRegion = cafeRegion;
        this.imgUrl = imgUrl;
    }
}
