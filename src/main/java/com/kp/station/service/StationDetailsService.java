package com.kp.station.service;

import com.kp.station.dto.EntryRequestDto;
import com.kp.station.dto.StationDetailResponseDto;
import com.kp.station.dto.StationDetailsRequestDto;
import com.kp.station.dto.StationResponseDto;

import java.util.List;

public interface StationDetailsService {
    StationResponseDto saveStationEntryDetails(EntryRequestDto entryRequestDto, String operation);
    StationResponseDto saveStationExitDetails(EntryRequestDto entryRequestDto,String operation);

    List<StationDetailResponseDto> fetchAllStationDetails(String fromDate,String toDate, String operation);

}
