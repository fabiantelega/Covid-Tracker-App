package me.springprojects.covidtrackerapp.controller;

import lombok.AllArgsConstructor;
import me.springprojects.covidtrackerapp.model.Data;
import me.springprojects.covidtrackerapp.service.DataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/covid/data")
public class DataController {

    private final DataService dataService;

    @GetMapping
    public List<Data> getAllData(){
        return dataService.getAllData();
    }

    @GetMapping(path = "/{countries}")
    public List<Data> getSpecificData(@PathVariable(name = "countries") String[] countries){
        return dataService.getSpecificData(countries);
    }
}
