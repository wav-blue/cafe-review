package kr.sujin.cafereview.dto;

import java.time.LocalDateTime;

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

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private LocalDateTime deletedDate;

    @QueryProjection
    public ReviewReadAdminDto(Long id, String email, String cafeNm, String menuNm, DeletedStatus deletedStatus, LocalDateTime createdDate, LocalDateTime lastModifiedDate, LocalDateTime deletedDate){
        this.id = id;
        this.email = email;
        this.cafeNm = cafeNm;
        this.menuNm = menuNm;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.deletedDate = deletedDate;
        this.deletedStatus = deletedStatus;
    }
}
