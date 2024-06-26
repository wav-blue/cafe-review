package kr.sujin.cafereview.service.review;

import kr.sujin.cafereview.dto.review.ReviewFormDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.entity.ReviewImg;
import kr.sujin.cafereview.entity.ReviewTag;
import kr.sujin.cafereview.repository.ReviewRepository;
import kr.sujin.cafereview.repository.ReviewTagRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewTagCreateService {
    private final ReviewTagRepository reviewTagRepository;

    public void createReviewTags(List<String> reviewTagList, Long reviewId) throws Exception{

        for(int i = 0; i < reviewTagList.size(); i++){

            ReviewTag reviewTag = ReviewTag.createReviewTag(reviewId, reviewTagList.get(i));
            reviewTagRepository.save(reviewTag);
        }
        return;
    }
}

