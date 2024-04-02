package kr.sujin.cafereview.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import kr.sujin.cafereview.dto.ReviewFormDto;
import kr.sujin.cafereview.dto.ReviewImgDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.repository.ReviewImgRepository;
import kr.sujin.cafereview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewUpdateService {
    @Value("${uploadPath}")
    private String uploadPath;

    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final FileService fileService;
    private final ReviewImgService reviewImgService;

    // 수정을 위한 조회
    @Transactional(readOnly = true)
    public ReviewFormDto getReviewDetail(Long reviewId){

        // ReviewImg 조회
        List<ReviewImg> reviewImgList = reviewImgRepository.findByReviewIdOrderByIdAsc(reviewId);
        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
        for(ReviewImg reviewImg : reviewImgList){
            ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImg);
            reviewImgDtoList.add(reviewImgDto);
        }

        // Review 조회
        Review review = reviewRepository.findById(reviewId).orElseThrow(EntityNotFoundException::new);
        ReviewFormDto reviewFormDto = ReviewFormDto.of(review);
        reviewFormDto.setReviewImgDtoList(reviewImgDtoList);
        return reviewFormDto;
    }

    // 이미지 수정
    @Transactional
    public void updateReviewImg(Long reviewImgId, MultipartFile reviewImgFile) throws Exception{

        if(!reviewImgFile.isEmpty()){
            ReviewImg savedReviewImg = reviewImgRepository.findById(reviewImgId)
            .orElseThrow(EntityNotFoundException::new);
            // 기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedReviewImg.getImgName())){
                fileService.deleteFile(uploadPath + "/" + savedReviewImg.getImgName());
            }

            String oriImgName = reviewImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(uploadPath, oriImgName, reviewImgFile.getBytes());
            String imgUrl = "/img/uploads/" + imgName;
            savedReviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        }
    }

    @Transactional
    public Long updateReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList) throws Exception{
        Review review = reviewRepository.findById(reviewFormDto.getId())
        .orElseThrow(EntityNotFoundException::new);
        review.updateReview(reviewFormDto);

        List<Long> reviewImgIds = reviewFormDto.getReviewImgIds();

        for(int i = 0; i < reviewImgFileList.size(); i++){
            reviewImgService.updateReviewImg(reviewImgIds.get(i), 
            reviewImgFileList.get(i));
        }

        return review.getId();
    }

}
