package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;


    public Integer addUser(User user){

        //Jut simply add the user to the Db and return the userId returned by the repository
       int id = userRepository.save(user).getId();
        return id;
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository

        List<WebSeries> webSeries=webSeriesRepository.findAll();

        User user=userRepository.findById(userId).get();

        int age=user.getAge(),count=0;
        SubscriptionType st=user.getSubscription().getSubscriptionType();

        for(WebSeries ws:webSeries){
            int ageLimit=ws.getAgeLimit();
            SubscriptionType webST=ws.getSubscriptionType();
            if(ageLimit<age) {
                if (webST.equals(SubscriptionType.ELITE) && st.equals(SubscriptionType.ELITE)) {
                    count++;
                } else if (webST.equals(SubscriptionType.PRO) && (webST.equals(SubscriptionType.ELITE) || webST.equals(SubscriptionType.PRO))) {
                    count++;
                } else if (webST.equals(SubscriptionType.BASIC) && (webST.equals(SubscriptionType.ELITE) || webST.equals(SubscriptionType.PRO) || webST.equals(SubscriptionType.BASIC))) {
                    count++;

                }

            }
        }


        return count;
    }


}
