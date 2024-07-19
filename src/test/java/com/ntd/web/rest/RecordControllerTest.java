package com.ntd.web.rest;

import com.ntd.entity.Type;
import com.ntd.service.RecordService;
import com.ntd.service.dao.OperationDTO;
import com.ntd.service.dao.RecordDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RecordControllerTest {

    private Page<RecordDTO> recordPage;

    @MockBean
    private RecordService recordService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        RecordDTO recordDTO = new RecordDTO();
        recordDTO.setUserId("test@example.com");
        recordDTO.setOperation(new OperationDTO(1L, Type.DIVISION, 10.0));
        recordDTO.setFirstValue(10.0);
        recordDTO.setUserBalance(90.0);
        recordDTO.setOperationResponse("100.0");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("date"));
        recordPage = new PageImpl<>(Collections.singletonList(recordDTO), pageable, 1);
    }

    @Test
    public void testGetRecords() throws Exception {
        when(recordService.findByUserId("test@example.com", PageRequest.of(0, 10, Sort.by("date"))))
                .thenReturn(recordPage);

//        mockMvc.perform(get("/api/records/userId/{userId}")
//                        .param("userId", "test@example.com")
//                        .param("page", "0")
//                        .param("size", "10")
//                        .param("sortBy", "date")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.content[0].id").value(1L))
//                .andExpect(jsonPath("$.content[0].userId").value(1L))
//                .andExpect(jsonPath("$.content[0].amount").value(10.0))
//                .andExpect(jsonPath("$.content[0].userBalance").value(90.0))
//                .andExpect(jsonPath("$.content[0].operationResponse").value("Success"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/records//user/{userId}", "test@example.com")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "date"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}
