package com.kp.station.service;

import com.kp.station.dto.EntryRequestDto;
import com.kp.station.dto.StationDetailResponseDto;
import com.kp.station.dto.StationResponseDto;
import com.kp.station.entity.StationDetails;
import com.kp.station.exception.Error;
import com.kp.station.exception.ServiceException;
import com.kp.station.repository.StationDetailsRepository;
import com.kp.station.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StationDetailsServiceImpl implements StationDetailsService {

    private final StationDetailsRepository stationDetailsRepository;


    public StationDetailsServiceImpl(StationDetailsRepository stationDetailsRepository) {
        this.stationDetailsRepository = stationDetailsRepository;
    }

    @Override
    public StationResponseDto saveStationEntryDetails(EntryRequestDto entryRequestDto, String operation) {

        Date fromDateTime = DateUtils.getCurrentDate("FROM_DATE_TIME", new Date());
        Date toDateTime = DateUtils.getCurrentDate("TO_DATE_TIME", new Date());

        System.out.println("from time  " + fromDateTime);
        System.out.println("to time  " + toDateTime);
        List<StationDetails> stationDetailsList
                = stationDetailsRepository.fetchStationDetails(entryRequestDto.getStationName(), fromDateTime, toDateTime);

        StationDetails stationDetails = null;
        if (stationDetailsList.isEmpty()) {
            stationDetails = StationDetails.builder()
                    .name(entryRequestDto.getStationName())
                    .totalEntry(1)
                    .totalExit(0)
                    .createdOn(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            stationDetailsRepository.save(stationDetails);
        } else {
            stationDetails = stationDetailsList.get(0);
            stationDetails.setTotalEntry(stationDetails.getTotalEntry() + 1);
            stationDetailsRepository.save(stationDetails);
        }

        return StationResponseDto.builder()
                .message("Station entry details has been saved successFully")
                .build();
    }

    @Override
    public StationResponseDto saveStationExitDetails(EntryRequestDto entryRequestDto, String operation) {
        Date fromDateTime = DateUtils.getCurrentDate("FROM_DATE_TIME", new Date());
        Date toDateTime = DateUtils.getCurrentDate("TO_DATE_TIME", new Date());

        List<StationDetails> stationDetailsList
                = stationDetailsRepository.fetchStationDetails(entryRequestDto.getStationName(), fromDateTime, toDateTime);

        StationDetails stationDetails = null;
        if (stationDetailsList.isEmpty()) {
            stationDetails = StationDetails.builder()
                    .name(entryRequestDto.getStationName())
                    .totalExit(1)
                    .totalEntry(0)
                    .createdOn(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            stationDetailsRepository.save(stationDetails);
        } else {
            stationDetails = stationDetailsList.get(0);
            stationDetails.setTotalExit(stationDetails.getTotalExit() + 1);
            stationDetailsRepository.save(stationDetails);
        }
        return StationResponseDto.builder()
                .message("Station exit details has been saved successFully")
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


        List<Object[]> entryExitCounts = stationDetailsRepository.getEntryExitCountsBetweenDates(startDate, endDate);
        List<StationDetailResponseDto> responseDtoList = new ArrayList<>();
        if (!entryExitCounts.isEmpty()) {
            entryExitCounts.stream().forEach(row -> {
                String name = (String) row[0];
                Long entryCount = (Long) row[1];
                Long exitCount = (Long) row[2];
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
