package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo
        WebSeries webSeries=new WebSeries();
        ProductionHouse productionHouse=productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId()).get();

        if (webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName())!=null) {
            throw new Exception("Series is already present");
        }


        webSeries.setSeriesName(webSeries.getSeriesName());
        webSeries.setAgeLimit(webSeriesEntryDto.getAgeLimit());
        webSeries.setRating(webSeriesEntryDto.getRating());
        webSeries.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());
        webSeries.setProductionHouse(productionHouse);

        int id;
        try {
           id = webSeriesRepository.save(webSeries).getId();
        }catch (Exception e){
            throw new Exception("Series is already present");
        }

        float ratings=0;

        List<WebSeries> webSeriesList=productionHouse.getWebSeriesList();

        for(WebSeries ws:webSeriesList){
            ratings+=ws.getRating();
        }

        productionHouse.setRatings(ratings/webSeriesList.size());
        productionHouse.getWebSeriesList().add(webSeries);

        productionHouseRepository.save(productionHouse);

        return id;
    }

}
