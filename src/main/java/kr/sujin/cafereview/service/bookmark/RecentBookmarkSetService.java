package kr.sujin.cafereview.service.bookmark;

import java.util.List;

import org.springframework.stereotype.Service;
import kr.sujin.cafereview.repository.RedisRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RecentBookmarkSetService {
    private final RedisRepository redisRepository;
    private final RecentBookmarkReadService recentBookmarkReadService;

    public void setRecentBookmarkReviews(Long reviewId){
        System.out.println(reviewId);
        List<Long> reviewIdList = recentBookmarkReadService.readRecentBookmarkReviewIds();
        
        if(reviewIdList.size() < 5){
            reviewIdList.add(reviewId);
        } else{
            reviewIdList.remove(0);
            reviewIdList.add(reviewId);
        }
        redisRepository.setValues("RecentBookmarkReviews", parseListToString(reviewIdList));
    }

    private String parseListToString(List<Long> reviewIdList){
        if(reviewIdList.isEmpty()){
            return "";
        }
        String value = reviewIdList.get(0).toString();
        for(int i = 1 ; i < reviewIdList.size() ; i++){
            System.out.println("현재 value: " + value);
            value = value + " " + reviewIdList.get(i).toString();
        }
        System.out.println("최종 value: " + value);
        return value;
    }

}
