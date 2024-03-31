package kr.sujin.cafereview.entity;

import kr.sujin.cafereview.dto.BookmarkCreateDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="bookmark_item")
@Getter
@Setter
@ToString
public class Bookmark {
    @Id
    @Column(name="bookmark_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

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
