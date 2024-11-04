package kr.sujin.cafereview.service.bookmark;

import java.util.ArrayList;
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
        List<Long> reviewIdLists = getRecentBookmarkReviews();
        System.out.println(reviewId);

        if(reviewIdLists.size() < 5){
            reviewIdLists.add(reviewId);
        } else{
            reviewIdLists.remove(0);
            reviewIdLists.add(reviewId);
        }

        String temp = reviewIdLists.get(0).toString();
        for(int i = 1 ; i < reviewIdLists.size() ; i++){
            temp = temp + " " + reviewIdLists.get(i).toString();
        }
        redisRepository.setValues("RecentBookmarkReviews", temp);
    }

    public List<Long> getRecentBookmarkReviews(){
        System.out.println("getRecentBookmarkReviews");
        String reviewIdLists = redisRepository.getValues("RecentBookmarkReviews");
        List<Long> castReviewIdLists = new ArrayList<>();
        System.out.println("reviewIdLists size: "+ reviewIdLists.length());
        if(reviewIdLists.length()==0){
            return castReviewIdLists;
        }
        for(String e :reviewIdLists.split(" ")){
            castReviewIdLists.add(Long.valueOf(e));
        }
        
        System.out.println("여기!!!");
        for(int i = 0 ; i < castReviewIdLists.size(); i++){
            System.out.println(castReviewIdLists.get(i));
        }

        return castReviewIdLists;
    }
}
