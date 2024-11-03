package kr.sujin.cafereview.service.bookmark;

import java.util.List;

import org.springframework.stereotype.Service;
import kr.sujin.cafereview.repository.RedisRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RecentBookmarkService {
    private final RedisRepository redisRepository;

    public void setRecentBookmarkReviews(Long reviewId){
        System.out.println(reviewId);
        List<Long> reviewIdLists = (List<Long>) redisRepository.getValues("RecentBookmarkReviews");
        
        if(reviewIdLists.size() < 5){
            reviewIdLists.add(reviewId);
        } else{
            reviewIdLists.remove(0);
            reviewIdLists.add(reviewId);
        }
        redisRepository.setValues("RecentBookmarkReviews", reviewIdLists);
    }

    public List<Long> getRecentBookmarkReviews(){
        return (List<Long>) redisRepository.getValues("RecentBookmarkReviews");
    }
}
