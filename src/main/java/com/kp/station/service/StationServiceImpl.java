package com.kp.station.service;

import com.kp.station.dto.EntryRequestDto;
import com.kp.station.dto.StationDetailResponseDto;
import com.kp.station.dto.StationDetailsRequestDto;
import com.kp.station.dto.StationResponseDto;
import com.kp.station.entity.Station;
import com.kp.station.repository.StationRepository;
import com.kp.station.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class StationServiceImpl implements StationService{

    private final StationRepository stationRepository;


    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    @Override
    public StationResponseDto saveEntryDetails(EntryRequestDto entryRequestDto,String operation) {
        Station station= Station.builder()
                .name(entryRequestDto.getStationName())
                .isEntry(Boolean.TRUE)
                .createdOn(new Date())
                .build();

        stationRepository.save(station);
        return StationResponseDto.builder()
                .message("Station entry details has been saved successFully")
                .build();
    }

    @Override
    public StationResponseDto saveExitDetails(EntryRequestDto entryRequestDto,String operation) {
        Station station= Station.builder()
                .name(entryRequestDto.getStationName())
                .isEntry(Boolean.FALSE)
                .createdOn(new Date())
                .build();

        stationRepository.save(station);
        return StationResponseDto.builder()
                .message("Station exit details has been saved successFully")
                .build();
    }

    @Override
    public List<StationDetailResponseDto>  fetchAllStationDetails(StationDetailsRequestDto requestDto,String operation){

        Map<String, String> map = DateUtils.isValidDateRange(requestDto.getStartDate(), requestDto.getEndDate(),operation);
        String startDate = map.get("startDate");
        String endDate = map.get("endDate");
        startDate="2023-07-20 11:55:06.690000";
        endDate="2023-07-20 19:55:06.690000";

        List<Object[]> entryExitCounts = stationRepository.getEntryExitCountsBetweenDates(startDate,endDate);
        List<StationDetailResponseDto> responseDtoList=new ArrayList<>();
        if(!entryExitCounts.isEmpty()){
            entryExitCounts.stream().forEach(row -> {
                String name = (String) row[0];
                BigInteger entryCount = (BigInteger) row[1];
                BigInteger exitCount = (BigInteger) row[2];
                responseDtoList.add(StationDetailResponseDto.builder()
                                .stationName(name)
                                .totalEntry(entryCount.intValue())
                                .totalExit(exitCount.intValue())
                                .build());
            });
        }
        return  responseDtoList;
    }
}
