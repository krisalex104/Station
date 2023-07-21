package com.kp.station;

import com.kp.station.dto.EntryRequestDto;
import com.kp.station.dto.StationDetailResponseDto;
import com.kp.station.dto.StationDetailsRequestDto;
import com.kp.station.dto.StationResponseDto;
import com.kp.station.entity.Station;
import com.kp.station.exception.Error;
import com.kp.station.exception.ServiceException;
import com.kp.station.repository.StationRepository;
import com.kp.station.service.StationServiceImpl;
import com.kp.station.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class StationApplicationTests {

	@Mock
	private StationRepository stationRepository;

	@InjectMocks
	private StationServiceImpl stationService;

	@InjectMocks
	private DateUtils dateUtils;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testSaveEntryDetails() {
		EntryRequestDto entryRequestDto = new EntryRequestDto();
		entryRequestDto.setStationName("Test Station");

		StationResponseDto expectedResponse = StationResponseDto.builder()
				.message("Station entry details has been saved successfully")
				.build();

		when(stationRepository.save(Mockito.any(Station.class))).thenReturn(null);

		StationResponseDto response = stationService.saveEntryDetails(entryRequestDto, "operation");
		assertEquals(expectedResponse.getMessage(), response.getMessage());
	}

	@Test
	void testSaveExitDetails() {
		EntryRequestDto entryRequestDto = new EntryRequestDto();
		entryRequestDto.setStationName("Test Station");

		StationResponseDto expectedResponse = StationResponseDto.builder()
				.message("Station exit details has been saved successfully")
				.build();

		when(stationRepository.save(Mockito.any(Station.class))).thenReturn(null);

		StationResponseDto response = stationService.saveExitDetails(entryRequestDto, "operation");
		assertEquals(expectedResponse.getMessage(), response.getMessage());
	}


	@Test
	void testFetchAllStationDetails_ValidDates_ReturnsData() throws ParseException {
		String fromDate = "2023-07-20";
		String toDate = "2023-07-21";

		List<Object[]> entryExitCounts = new ArrayList<>();

		when(stationRepository.getEntryExitCountsBetweenDates(Mockito.any(), Mockito.any()))
				.thenReturn(entryExitCounts);

		List<StationDetailResponseDto> response = stationService.fetchAllStationDetails(fromDate, toDate, "FETCH_STATION_DETAILS");

		assertEquals(entryExitCounts.size(), response.size());

	}

	@Test
	void testFetchAllStationDetails_InvalidFromDate_ThrowsServiceException() {
		String fromDate = "";
		String toDate = "2023-07-21";

		ServiceException exception = assertThrows(ServiceException.class, () ->
				stationService.fetchAllStationDetails(fromDate, toDate, "operation"));

		assertEquals(Error.FROM_DATE_IS_NULL, exception.getError());
	}

	@Test
	void testFetchAllStationDetails_InvalidToDate_ThrowsServiceException() {
		String fromDate = "2023-07-20";
		String toDate = "";

		ServiceException exception = assertThrows(ServiceException.class, () ->
				stationService.fetchAllStationDetails(fromDate, toDate, "operation"));

		assertEquals(Error.TO_DATE_IS_NULL, exception.getError());
	}

	@Test
	void testFetchAllStationDetails_EmptyEntryExitCounts_ReturnsEmptyList() {
		String fromDate = "2023-07-20";
		String toDate = "2023-07-21";

		when(stationRepository.getEntryExitCountsBetweenDates(Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());

		List<StationDetailResponseDto> response = stationService.fetchAllStationDetails(fromDate, toDate, "operation");
		assertTrue(response.isEmpty());
	}


}
