package kr.sujin.cafereview.service.review;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.member.MemberReadWriterDto;
import kr.sujin.cafereview.service.member.MemberReadService;
import kr.sujin.cafereview.repository.ReviewImgRepository;
import kr.sujin.cafereview.repository.ReviewRepository;
import kr.sujin.cafereview.repository.ReviewTagRepository;
import kr.sujin.cafereview.dto.review.ReviewImgDto;
import kr.sujin.cafereview.dto.review.ReviewReadDetailDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.entity.ReviewTag;
import kr.sujin.cafereview.lib.constant.TagType;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ReviewReadDetailService {
    
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final MemberReadService memberReadService;
    private final ReviewTagRepository reviewTagRepository;

    public ReviewReadDetailDto getReviewDetail(Long reviewId){
        
        ReviewReadDetailDto reviewReadDetailDto;
    
        // Review 조회
        Review review = reviewRepository.findByIdAndDeletedDateIsNull(reviewId).orElseThrow(EntityNotFoundException::new);
        reviewReadDetailDto = ReviewReadDetailDto.of(review);

        // ReviewImg 조회
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);
        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
        for(ReviewImg reviewImg : reviewImgList){
            ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImg);
            reviewImgDtoList.add(reviewImgDto);
        }
        reviewReadDetailDto.setReviewImgDtoList(reviewImgDtoList);

        // ReviewTag 조회
        List<ReviewTag> reviewTagList = reviewTagRepository.findByReviewId(reviewId);

        List<TagType> reviewTagTypeList = new ArrayList<>();

        for(var i = 0 ; i < reviewTagList.size(); i++){
            reviewTagTypeList.add(reviewTagList.get(i).getTagType());
        }
        reviewReadDetailDto.setReviewTagList(reviewTagTypeList);
        System.out.println(reviewReadDetailDto.getReviewTagList());

        // 작성자 조회
        var writerEmail = reviewReadDetailDto.getEmail();

        MemberReadWriterDto writer = memberReadService.getMemberWriterByEmail(writerEmail);
        reviewReadDetailDto.setWriterName(writer.getName());

        return reviewReadDetailDto;
    }

    public Boolean checkReviewWriter(String email){
        return true;
    }
}
