package kr.sujin.cafereview.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="review_tag")
@Getter
@Setter
public class ReviewTag {
    @Id
    @Column(name="review_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewTagId;

    @Column(name="review_id")
    private Long reviewId;

    @Column(name="tag_type")
    private String tagType;

    public static ReviewTag createReviewTag(Long reviewId, String tagType){
        ReviewTag reviewTag = new ReviewTag();
        reviewTag.reviewId = reviewId;
        reviewTag.tagType = tagType;
        return reviewTag;
    }
}
