package kr.sujin.cafereview.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="review_img")
@Getter
@Setter
public class ReviewImg {
    @Id
    @Column(name="review_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgName;

    @Column(nullable = false)
    private String oriImgName;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private Boolean isThumbnail;

    // 다대일 단방향 매핑 (+ 지연로딩 설정)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public void updateReviewImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
