package kr.sujin.cafereview.dto;

import org.modelmapper.ModelMapper;

import kr.sujin.cafereview.entity.Bookmark;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookmarkCreateDto {
    private Long reviewId;

    private String firstImgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public Bookmark createReview(){
        return modelMapper.map(this, Bookmark.class);
    }

    public static BookmarkCreateDto of(Bookmark bookmark) {return modelMapper.map(bookmark, BookmarkCreateDto.class);}
}