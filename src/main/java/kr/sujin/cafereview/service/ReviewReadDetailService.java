package kr.sujin.cafereview.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.MemberReadWriterDto;
import kr.sujin.cafereview.dto.ReviewImgDto;
import kr.sujin.cafereview.dto.ReviewReadDetailDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.repository.ReviewImgRepository;
import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewReadDetailService {
    
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final MemberReadService memberReadService;

    public ReviewReadDetailDto getReviewDetail(Long reviewId){
        
        ReviewReadDetailDto reviewReadDetailDto;
    
        // Review 조회
        Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
        reviewReadDetailDto = ReviewReadDetailDto.of(review);
        // ReviewImg 조회
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);
        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
        for(ReviewImg reviewImg : reviewImgList){
            ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImg);
            reviewImgDtoList.add(reviewImgDto);
        }
        reviewReadDetailDto.setReviewImgDtoList(reviewImgDtoList);

        // 작성자 조회
        var writerEmail = reviewReadDetailDto.getEmail();

        MemberReadWriterDto writer = memberReadService.getMemberWriterByEmail(writerEmail);
        System.out.println(writer);
        reviewReadDetailDto.setWriterName(writer.getName());

        return reviewReadDetailDto;
    }

    public Boolean checkReviewWriter(String email){
        return true;
    }
}
