package com.kp.station.controller;

import com.kp.station.dto.EntryRequestDto;
import com.kp.station.dto.StationDetailResponseDto;
import com.kp.station.dto.StationDetailsRequestDto;
import com.kp.station.dto.StationResponseDto;
import com.kp.station.service.StationDetailsService;
import com.kp.station.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stationdetails")
public class StationDetailsController {

    private static final String SAVE_ENTRY_DETAILS = "SAVE_ENTRY_DETAILS";
    private static final String SAVE_EXIT_DETAILS = "SAVE_EXIT_DETAILS";
    private static final String FETCH_STATIONS_DETAILS = "FETCH_STATIONS_DETAILS";
    private final StationDetailsService stationDetailsService;


    public StationDetailsController(StationDetailsService stationDetailsService) {
        this.stationDetailsService = stationDetailsService;
    }

    @PostMapping("/entry")
    ResponseEntity<StationResponseDto> saveEntryDetails(@Valid @RequestBody EntryRequestDto entryRequestDto) {
        StationResponseDto entryResponseDto = stationDetailsService.saveStationEntryDetails(entryRequestDto, SAVE_ENTRY_DETAILS);
        return ResponseEntity.status(HttpStatus.CREATED).body(entryResponseDto);
    }

    @PostMapping("/exit")
    ResponseEntity<StationResponseDto> saveExitDetails(@Valid @RequestBody EntryRequestDto entryRequestDto) {
        StationResponseDto entryResponseDto = stationDetailsService.saveStationExitDetails(entryRequestDto, SAVE_EXIT_DETAILS);
        return ResponseEntity.status(HttpStatus.CREATED).body(entryResponseDto);
    }

    @RequestMapping
    ResponseEntity<List<StationDetailResponseDto>> getAllStationDetails(@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate) {
        List<StationDetailResponseDto> responseDtoList = stationDetailsService.fetchAllStationDetails(fromDate,toDate, FETCH_STATIONS_DETAILS);
        return ResponseEntity.ok(responseDtoList);
    }
}
