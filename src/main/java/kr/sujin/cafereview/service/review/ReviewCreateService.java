package kr.sujin.cafereview.service.review;

import kr.sujin.cafereview.dto.review.ReviewFormDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCreateService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgService reviewImgService;
    private final ReviewTagCreateService reviewTagCreateService;

    public Long createReview(ReviewFormDto reviewFormDto,
                             List<MultipartFile> reviewImgFileList, String email) throws Exception{
        reviewFormDto.setEmail(email);

        // 리뷰 등록
        Review review = reviewFormDto.createReview();
        reviewRepository.save(review);

        // 리뷰 태그 등록
        reviewTagCreateService.createReviewTags(reviewFormDto.getTagTypes(), review.getId());

        //이미지 등록
        for(int i = 0; i < reviewImgFileList.size(); i++){
            ReviewImg reviewImg = new ReviewImg();
            reviewImg.setReview(review);
            if(i==0) reviewImg.setIsThumbnail(true); //첫번째 이미지는 대표 이미지 값을 Y로 설정
            else reviewImg.setIsThumbnail(false);
            reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));
        }
        return review.getId();
    }
}

