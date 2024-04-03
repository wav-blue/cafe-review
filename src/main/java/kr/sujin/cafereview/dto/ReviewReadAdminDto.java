package kr.sujin.cafereview.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.sujin.cafereview.constant.DeletedStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewReadAdminDto {
    private Long id;

    private String email;

    private String cafeNm;

    private String menuNm;

    private DeletedStatus deletedStatus;

    @QueryProjection
    public ReviewReadAdminDto(Long id, String email, String cafeNm, String menuNm, DeletedStatus deletedStatus){
        this.id = id;
        this.email = email;
        this.cafeNm = cafeNm;
        this.menuNm = menuNm;
        this.deletedStatus = deletedStatus;
    }
}
