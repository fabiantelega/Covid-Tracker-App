package me.springprojects.covidtrackerapp.service.helper;

import me.springprojects.covidtrackerapp.exceptions.InternalServiceException;
import me.springprojects.covidtrackerapp.exceptions.enums.ServiceExceptions;
import me.springprojects.covidtrackerapp.model.Data;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataServiceHelper {

    public List<Data> sortDataBy(List<Data> data, String sort, boolean reverse){
        Comparator<Data> comparator;
        switch(sort){
            case "cases" -> comparator = Comparator.comparingLong(Data::getCases);
            case "today-cases" -> comparator = Comparator.comparingLong(Data::getTodayCases);
            case "deaths" -> comparator = Comparator.comparingLong(Data::getDeaths);
            case "today-deaths" -> comparator = Comparator.comparingLong(Data::getTodayDeaths);
            default -> throw new InternalServiceException(ServiceExceptions.INVALID_PARAMETER);
        }
        if(reverse) comparator = comparator.reversed();
        return data.stream()
                   .sorted(comparator)
                   .collect(Collectors.toList());
    }
}
