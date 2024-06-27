package kr.sujin.cafereview.service.review;

import kr.sujin.cafereview.entity.ReviewTag;
import kr.sujin.cafereview.lib.constant.TagType;
import kr.sujin.cafereview.repository.ReviewTagRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewTagCreateService {
    private final ReviewTagRepository reviewTagRepository;

    public void createReviewTags(List<TagType> reviewTagList, Long reviewId) throws Exception{

        for(int i = 0; i < reviewTagList.size(); i++){

            ReviewTag reviewTag = ReviewTag.createReviewTag(reviewId, reviewTagList.get(i));
            reviewTagRepository.save(reviewTag);
        }
        return;
    }
}

