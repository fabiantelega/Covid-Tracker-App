package me.springprojects.covidtrackerapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {

    private String country;
    private long cases;
    private long todayCases;
    private long deaths;
    private long todayDeaths;
    private long recovered;
    private long todayRecovered;
    private long casesPerOneMillion;
    private long deathsPerOneMillion;
    private long tests;
    private long testsPerOneMillion;
    private long recoveredPerOneMillion;
}
