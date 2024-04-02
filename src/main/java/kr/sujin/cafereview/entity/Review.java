package kr.sujin.cafereview.entity;


import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import kr.sujin.cafereview.constant.CafeRegion;
import kr.sujin.cafereview.dto.ReviewFormDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@Table(name="review_item")
@ToString
@Where(clause = "deleted_date IS NULL")
@SQLDelete(sql = "UPDATE review_item SET deleted_date = NOW() WHERE review_id = ?")
public class Review extends BaseEntity{
    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String email; //작성자 이메일

    @Column(nullable = false, length = 100)
    private String cafeNm; //카페이름

    @Column(nullable = false, length = 100)
    private String menuNm; //메뉴이름

    @Column(nullable = false)
    private Integer rating; //추천도

    @Lob
    @Column(nullable = false)
    private String reviewDetail; //세부 감상

    @Column(nullable = false, length = 100)
    private CafeRegion cafeRegion; // 카페 지역

    public void updateReview(ReviewFormDto reviewFormDto){
        this.email = reviewFormDto.getEmail();
        this.cafeNm = reviewFormDto.getCafeNm();
        this.menuNm = reviewFormDto.getMenuNm();
        this.rating = reviewFormDto.getRating();
        this.reviewDetail = reviewFormDto.getReviewDetail();
        this.cafeRegion = reviewFormDto.getCafeRegion();
    }
}