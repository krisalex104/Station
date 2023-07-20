package com.kp.station;

import com.kp.station.dto.EntryRequestDto;
import com.kp.station.dto.StationDetailResponseDto;
import com.kp.station.dto.StationDetailsRequestDto;
import com.kp.station.dto.StationResponseDto;
import com.kp.station.repository.StationRepository;
import com.kp.station.service.StationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class StationApplicationTests {

	@Mock
	private StationRepository stationRepository;

	@InjectMocks
	private StationServiceImpl stationService;

	@Test
	public void testSaveEntryDetails() {
		EntryRequestDto entryRequestDto = EntryRequestDto.builder()
				.stationName("Anand Vihar")
				.build();

		StationResponseDto responseDto = stationService.saveEntryDetails(entryRequestDto, "SAVE-ENTRY-DETAILS");
		assertNotNull(responseDto);
		assertEquals("Station entry details has been saved successFully", responseDto.getMessage());
	}

	@Test
	public void testSaveExitDetails() {
		EntryRequestDto entryRequestDto = EntryRequestDto.builder()
				.stationName("New Delhi")
				.build();

		StationResponseDto responseDto = stationService.saveExitDetails(entryRequestDto, "SAVE-EXIT-DETAILS");
		assertNotNull(responseDto);
		assertEquals("Station exit details has been saved successFully", responseDto.getMessage());
	}

	@Test
	public void testFetchAllStationDetails() {
		StationDetailsRequestDto requestDto = StationDetailsRequestDto.builder()
				.startDate("2023-07-20 11:55:06.690000")
				.endDate("2023-07-20 19:55:06.690000")
				.build();

		List<Object[]> mockEntryExitCounts = new ArrayList<>();
		mockEntryExitCounts.add(new Object[]{"New Delhi", BigInteger.valueOf(5), BigInteger.valueOf(3)});
		when(stationRepository.getEntryExitCountsBetweenDates(any(), any())).thenReturn(mockEntryExitCounts);

		List<StationDetailResponseDto> responseDtoList = stationService.fetchAllStationDetails(requestDto, "FETCH-STATIONS_DETAILS");

		assertNotNull(responseDtoList);
		assertFalse(responseDtoList.isEmpty());
		assertEquals(1, responseDtoList.size());

		StationDetailResponseDto responseDto = responseDtoList.get(0);
		assertEquals("New Delhi", responseDto.getStationName());
		assertEquals(5, responseDto.getTotalEntry());
		assertEquals(3, responseDto.getTotalExit());
	}

}
