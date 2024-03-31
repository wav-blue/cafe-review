package kr.sujin.cafereview.dto;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.entity.Bookmark;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookmarkReadDto {
    private Long id;

    private Long reviewId;
    
    private String cafeNm;

    private String menuNm;

    private CafeRegion cafeRegion;

    private String firstImgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public Bookmark createReview(){
        return modelMapper.map(this, Bookmark.class);
    }

    public static BookmarkReadDto of(Bookmark bookmark) {return modelMapper.map(bookmark, BookmarkReadDto.class);}

    @QueryProjection
    public BookmarkReadDto(Long id, Long reviewId, String cafeNm, String menuNm, CafeRegion cafeRegion, String firstImgUrl){
        this.id = id;
        this.reviewId = reviewId;
        this.cafeNm = cafeNm;
        this.menuNm = menuNm;
        this.cafeRegion = cafeRegion;
        this.firstImgUrl = firstImgUrl;
    }
}