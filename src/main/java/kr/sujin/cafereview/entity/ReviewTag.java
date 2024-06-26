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
    @Column(name="review_id")
    private Long reviewId;

    @Column(name="tag_type")
    private String tagType;

}
