package kr.sujin.cafereview.service;

import kr.sujin.cafereview.dto.ReviewFormDto;
import kr.sujin.cafereview.dto.ReviewImgDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.repository.ReviewImgRepository;
import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImgService reviewImgService;
    private final ReviewImgRepository reviewImgRepository;

    public Long saveReview(ReviewFormDto reviewFormDto,
                             List<MultipartFile> reviewImgFileList) throws Exception{
        // 리뷰 등록
        Review review = reviewFormDto.createReview();
        reviewRepository.save(review);

        //이미지 등록
        for(int i = 0; i < reviewImgFileList.size(); i++){
            ReviewImg reviewImg = new ReviewImg();
            reviewImg.setReview(review);
            if(i==0) reviewImg.setRepimgYn("Y"); //첫번째 이미지는 대표 이미지 값을 Y로 설정
            else reviewImg.setRepimgYn("N");
            reviewImgService.saveReviewImg(reviewImg, reviewImgFileList.get(i));
        }
        return review.getId();
    }

    @Transactional(readOnly = true)
    public ReviewFormDto getReviewDtl(Long id){

        List<ReviewImg> reviewImgList = reviewImgRepository.findByIdOrderByIdAsc(id);
        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
        for(ReviewImg reviewImg : reviewImgList){
            ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImg);
            reviewImgDtoList.add(reviewImgDto);
        }

        Review review = reviewRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ReviewFormDto reviewFormDto = ReviewFormDto.of(review);
        reviewFormDto.setReviewImgDtoList(reviewImgDtoList);
        return reviewFormDto;
    }

    //정보 업데이트
    public Long updateItem(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList) throws Exception{
        //수정
        Review review = reviewRepository.findOneById(reviewFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        review.updateReview(reviewFormDto);

        List<Long> reviewImgIds = reviewFormDto.getReviewImgIds();
        //이미지 등록
        for(int i = 0; i < reviewImgFileList.size(); i++){
            reviewImgService.updateReviewImg(reviewImgIds.get(i), reviewImgFileList.get(i));
        }

        return review.getId();
    }

}
