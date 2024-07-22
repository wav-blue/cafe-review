package kr.sujin.cafereview.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.repository.ReviewImgRepository;
import kr.sujin.cafereview.service.FileService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgCreateService {
    @Value("${uploadPath}")
    private String uploadPath;

    private final ReviewImgRepository reviewImgRepository;

    private final FileService fileService;

    public void createReviewImgs(List<MultipartFile> reviewImgFileList, Review review) throws Exception{
        for(int i = 0; i < reviewImgFileList.size(); i++){
            ReviewImg reviewImg = new ReviewImg();
            reviewImg.setReview(review);
            if(i==0) reviewImg.setIsThumbnail(true); //첫번째 이미지는 대표 이미지 값을 Y로 설정
            else reviewImg.setIsThumbnail(false);

            this.saveReviewImg(reviewImg, reviewImgFileList.get(i));
        }

        return;
    }

    private String saveReviewImg(ReviewImg reviewImg, MultipartFile reviewImgFile) throws Exception{
        String oriImgName = reviewImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(uploadPath, oriImgName,
                    reviewImgFile.getBytes());
            imgUrl = "/img/uploads/" + imgName;
        }
        
        //상품 이미지 정보 저장
        reviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        reviewImgRepository.save(reviewImg);

        return imgUrl;
    }

}
