package com.kp.station.service;

import com.kp.station.dto.EntryRequestDto;
import com.kp.station.dto.StationDetailResponseDto;
import com.kp.station.dto.StationDetailsRequestDto;
import com.kp.station.dto.StationResponseDto;
import com.kp.station.entity.Station;
import com.kp.station.exception.Error;
import com.kp.station.exception.ServiceException;
import com.kp.station.repository.StationRepository;
import com.kp.station.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;


    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }


    @Override
    public StationResponseDto saveEntryDetails(EntryRequestDto entryRequestDto, String operation) {
        Station station = Station.builder()
                .name(entryRequestDto.getStationName())
                .isEntry(Boolean.TRUE)
                .createdOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        stationRepository.save(station);
        return StationResponseDto.builder()
                .message("Station entry details has been saved successfully")
                .build();
    }

    @Override
    public StationResponseDto saveExitDetails(EntryRequestDto entryRequestDto, String operation) {
        Station station = Station.builder()
                .name(entryRequestDto.getStationName())
                .isEntry(Boolean.FALSE)
                .createdOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        stationRepository.save(station);
        return StationResponseDto.builder()
                .message("Station exit details has been saved successfully")
                .build();
    }

    @Override
    public List<StationDetailResponseDto> fetchAllStationDetails(String fromDate,String toDate, String operation) {

        if(!StringUtils.hasText(fromDate)){
            throw new ServiceException(operation, Error.FROM_DATE_IS_NULL);
        }

        if(!StringUtils.hasText(toDate)){
            throw new ServiceException(operation, Error.TO_DATE_IS_NULL);
        }

        Map<String, Date> map =
                DateUtils.isValidDateRange(fromDate, toDate, operation);

        Date startDate = DateUtils.getCurrentDate("FROM_DATE_TIME", map.get("fromDate"));
        Date endDate = DateUtils.getCurrentDate("TO_DATE_TIME", map.get("toDate"));


        List<Object[]> entryExitCounts = stationRepository.getEntryExitCountsBetweenDates(startDate, endDate);
        List<StationDetailResponseDto> responseDtoList = new ArrayList<>();
        if (!entryExitCounts.isEmpty()) {
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
        return responseDtoList;
    }

}
