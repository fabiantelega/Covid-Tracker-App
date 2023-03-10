package me.springprojects.covidtrackerapp.service.helper;

import lombok.RequiredArgsConstructor;
import me.springprojects.covidtrackerapp.exceptions.InternalServiceException;
import me.springprojects.covidtrackerapp.exceptions.enums.ServiceExceptions;
import me.springprojects.covidtrackerapp.model.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CovidDataApi {

    private final RestTemplate restTemplate;
    @Value(value = "${data-api}")
    private String apiPath;

    public List<Data> getDataFromAllCountries(){
        ResponseEntity<List<Data>> data = restTemplate.exchange(apiPath, HttpMethod.GET, null, new ParameterizedTypeReference<List<Data>>() {});
        if(!data.hasBody()) throw new InternalServiceException(ServiceExceptions.DATA_NOT_FOUND);
        return data.getBody();
    }

    private Data getDataFromSingleCountry(String country){
        try{
            ResponseEntity<Data> data = restTemplate.exchange(apiPath + "/" + country, HttpMethod.GET, null, new ParameterizedTypeReference<Data>() {});
            return data.getBody();
        } catch (Exception e){
            throw new InternalServiceException(ServiceExceptions.DATA_NOT_FOUND);
        }
    }

    public List<Data> getDataFromSpecificCountries(String[] countries){
        List<Data> data = new ArrayList<>();
        for(String country : countries){
            data.add(this.getDataFromSingleCountry(country));
        }
        return data;
    }
}
