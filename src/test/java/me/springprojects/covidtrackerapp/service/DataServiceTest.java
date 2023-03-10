package me.springprojects.covidtrackerapp.service;

import me.springprojects.covidtrackerapp.exceptions.InternalServiceException;
import me.springprojects.covidtrackerapp.model.Data;
import me.springprojects.covidtrackerapp.service.helper.CovidDataApi;
import me.springprojects.covidtrackerapp.service.helper.DataServiceHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class DataServiceTest {

    private final CovidDataApi dataApi = mock(CovidDataApi.class);
    private final DataServiceHelper dataServiceHelper = new DataServiceHelper();
    private final DataService dataService = new DataService(dataApi, dataServiceHelper);
    private static List<Data> data;

    @BeforeAll
    public static void beforeAll(){
        Data data1 = Data.builder()
                    .country("Poland")
                    .cases(100)
                    .todayCases(10)
                    .deaths(100)
                    .todayDeaths(100023)
                    .build();

        Data data2 = Data.builder()
                    .country("France")
                    .cases(1000)
                    .todayCases(2)
                    .deaths(5000)
                    .todayDeaths(12)
                    .build();

        data = new ArrayList<>();
        data.add(data1);
        data.add(data2);
    }

    @Test
    public void checkIfFetchesAllData(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        List<Data> res = dataService.getAllData(null, false);

        assertEquals(res, data);
    }

    @Test
    public void checkIfSortsDataByCases(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        List<Data> res = dataService.getAllData("cases", false);

        assertEquals(data.size(), res.size());
        assertEquals(data.get(0).getCountry(), res.get(0).getCountry());
        assertEquals(data.get(1).getCountry(), res.get(1).getCountry());
    }

    @Test
    public void checkIfReversesDataByCases(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        List<Data> res = dataService.getAllData("cases", true);

        assertEquals(data.size(), res.size());
        assertEquals(data.get(0).getCountry(), res.get(1).getCountry());
        assertEquals(data.get(1).getCountry(), res.get(0).getCountry());
    }

    @Test
    public void checkIfSortsDataByTodayCases(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        List<Data> res = dataService.getAllData("today-cases", false);

        assertEquals(data.size(), res.size());
        assertEquals(data.get(1).getCountry(), res.get(0).getCountry());
        assertEquals(data.get(0).getCountry(), res.get(1).getCountry());
    }

    @Test
    public void checkIfReversesDataByTodayCases(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        List<Data> res = dataService.getAllData("today-cases", true);

        assertEquals(data.size(), res.size());
        assertEquals(data.get(0).getCountry(), res.get(0).getCountry());
        assertEquals(data.get(1).getCountry(), res.get(1).getCountry());
    }

    @Test
    public void checkIfSortsDataByDeaths(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        List<Data> res = dataService.getAllData("deaths", false);

        assertEquals(data.size(), res.size());
        assertEquals(data.get(0).getCountry(), res.get(0).getCountry());
        assertEquals(data.get(1).getCountry(), res.get(1).getCountry());
    }

    @Test
    public void checkIfReversesDataByDeaths(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        List<Data> res = dataService.getAllData("deaths", true);

        assertEquals(data.size(), res.size());
        assertEquals(data.get(0).getCountry(), res.get(1).getCountry());
        assertEquals(data.get(1).getCountry(), res.get(0).getCountry());
    }

    @Test
    public void checkIfSortsDataByTodayDeaths(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        List<Data> res = dataService.getAllData("today-deaths", false);

        assertEquals(data.size(), res.size());
        assertEquals(data.get(0).getCountry(), res.get(1).getCountry());
        assertEquals(data.get(1).getCountry(), res.get(0).getCountry());
    }

    @Test
    public void checkIfReversesDataByTodayDeaths(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        List<Data> res = dataService.getAllData("today-deaths", true);

        assertEquals(data.size(), res.size());
        assertEquals(data.get(0).getCountry(), res.get(0).getCountry());
        assertEquals(data.get(1).getCountry(), res.get(1).getCountry());
    }

    @Test
    public void checkIfThrowsExceptionIfSortTypeDoesNotExist(){
        given(dataApi.getDataFromAllCountries()).willReturn(data);

        assertThrows(InternalServiceException.class, () -> dataService.getAllData("invalid-sort-parameter", false));
    }
}