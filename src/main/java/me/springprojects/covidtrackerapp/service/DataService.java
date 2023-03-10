package me.springprojects.covidtrackerapp.service;

import lombok.AllArgsConstructor;
import me.springprojects.covidtrackerapp.model.Data;
import me.springprojects.covidtrackerapp.service.helper.CovidDataApi;
import me.springprojects.covidtrackerapp.service.helper.DataServiceHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DataService {

    private final CovidDataApi dataApi;
    private final DataServiceHelper dataServiceHelper;

    public List<Data> getAllData(String sort, boolean reverse){
        List<Data> data = dataApi.getDataFromAllCountries();
        if(sort!=null) data = dataServiceHelper.sortDataBy(data, sort, reverse);
        return data;
    }

    public List<Data> getSpecificData(String[] countries, String sort, boolean reverse){
        List<Data> data = dataApi.getDataFromSpecificCountries(countries);
        if(sort!=null) data = dataServiceHelper.sortDataBy(data, sort, reverse);
        return data;
    }
}
