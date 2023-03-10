package me.springprojects.covidtrackerapp.service;

import lombok.AllArgsConstructor;
import me.springprojects.covidtrackerapp.model.Data;
import me.springprojects.covidtrackerapp.service.helper.CovidDataApi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DataService {

    private final CovidDataApi dataApi;

    public List<Data> getAllData(){
        return dataApi.getDataFromAllCountries();
    }

    public List<Data> getSpecificData(String[] countries){
        return dataApi.getDataFromSpecificCountries(countries);
    }
}
