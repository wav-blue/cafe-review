package kr.sujin.cafereview.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import kr.sujin.cafereview.dto.bookmark.BookmarkCreateDto;

@Entity
@Table(name="bookmark_item")
@Getter
@Setter
@ToString
public class Bookmark extends BaseEntity {
    @Id
    @Column(name="bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String firstImgUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public static Bookmark createBookmark(BookmarkCreateDto bookmarkCreateDto, String email, Review review){
        Bookmark bookmark = new Bookmark();
        bookmark.setReview(review);
        bookmark.setFirstImgUrl(bookmarkCreateDto.getFirstImgUrl());
        bookmark.setUserEmail(email);
        return bookmark;
    }
}
