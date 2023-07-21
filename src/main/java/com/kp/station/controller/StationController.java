package com.kp.station.controller;

import com.kp.station.dto.EntryRequestDto;
import com.kp.station.dto.StationDetailResponseDto;
import com.kp.station.dto.StationDetailsRequestDto;
import com.kp.station.dto.StationResponseDto;
import com.kp.station.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/station")
public class StationController {

    private static final String SAVE_ENTRY_DETAILS="SAVE_ENTRY_DETAILS";
    private static final String SAVE_EXIT_DETAILS="SAVE_EXIT_DETAILS";
    private static final String FETCH_STATIONS_DETAILS="FETCH_STATIONS_DETAILS";
    private final StationService stationService;


    public StationController(StationService stationService) {
        this.stationService = stationService;
    }


    @PostMapping("/entry")
    ResponseEntity<StationResponseDto> saveEntryDetails(@Valid @RequestBody EntryRequestDto entryRequestDto){
        StationResponseDto entryResponseDto = stationService.saveEntryDetails(entryRequestDto,SAVE_ENTRY_DETAILS);
        return ResponseEntity.status(HttpStatus.CREATED).body(entryResponseDto);
    }

    @PostMapping("/exit")
    ResponseEntity<StationResponseDto> saveExitDetails(@Valid @RequestBody EntryRequestDto entryRequestDto){
        StationResponseDto entryResponseDto = stationService.saveExitDetails(entryRequestDto,SAVE_EXIT_DETAILS);
        return ResponseEntity.status(HttpStatus.CREATED).body(entryResponseDto);
    }

    @RequestMapping("/details")
    ResponseEntity<List<StationDetailResponseDto>>  getAllStationDetails(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate){
        List<StationDetailResponseDto> responseDtoList = stationService.fetchAllStationDetails(fromDate,toDate,FETCH_STATIONS_DETAILS);
        return ResponseEntity.ok(responseDtoList);
    }

    @GetMapping
    CompletionStage<ResponseEntity<List<StationDetailResponseDto>>> fetchAllStationsDetails(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate){
        List<StationDetailResponseDto> responseDtoList = stationService.fetchAllStationDetails(fromDate,toDate,FETCH_STATIONS_DETAILS);
        return CompletableFuture.completedFuture(ResponseEntity.ok(responseDtoList));
    }
}
