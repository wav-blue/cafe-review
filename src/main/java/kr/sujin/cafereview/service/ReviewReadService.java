package kr.sujin.cafereview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.sujin.cafereview.dto.ReviewReadDto;
import kr.sujin.cafereview.dto.ReviewSearchDto;
import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewReadService {
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Page<ReviewReadDto> getReviewWithPaging(Pageable pageable){
        return reviewRepository.getReviewWithPaging(pageable);
    }

    public Page<ReviewReadDto> getReviewWithPagingBySearch(ReviewSearchDto reviewSearchDto, Pageable pageable){
        return reviewRepository.getReviewWithPagingBySearch(reviewSearchDto, pageable);
    }

    // //정보 업데이트
    // public Long updateItem(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList) throws Exception{
    //     //수정
    //     Review review = reviewRepository.findOneById(reviewFormDto.getId())
    //             .orElseThrow(EntityNotFoundException::new);
    //     review.updateReview(reviewFormDto);

    //     List<Long> reviewImgIds = reviewFormDto.getReviewImgIds();
    //     //이미지 등록
    //     for(int i = 0; i < reviewImgFileList.size(); i++){
    //         reviewImgService.updateReviewImg(reviewImgIds.get(i), reviewImgFileList.get(i));
    //     }

    //     return review.getId();
    // }

}
