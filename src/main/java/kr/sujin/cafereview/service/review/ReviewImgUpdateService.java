package kr.sujin.cafereview.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.repository.ReviewImgRepository;
import kr.sujin.cafereview.service.FileService;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgUpdateService {
    @Value("${uploadPath}")
    private String uploadPath;

    private final ReviewImgRepository reviewImgRepository;

    private final FileService fileService;

    public void updateReviewImg(Long reviewImgId, MultipartFile reviewImgFile) throws Exception{
        if(!reviewImgFile.isEmpty()){
            ReviewImg savedReviewImg = reviewImgRepository.findById(reviewImgId)
                    .orElseThrow(EntityNotFoundException::new);
            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedReviewImg.getImgName())){
                fileService.deleteFile(uploadPath+"/"+savedReviewImg.getImgName());
            }

            String oriImgName = reviewImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(uploadPath,
                    oriImgName, reviewImgFile.getBytes());
            String imgUrl = "/img/uploads/" + imgName;
            savedReviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        }
    }
}
