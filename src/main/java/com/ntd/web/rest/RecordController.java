package com.ntd.web.rest;

import com.ntd.service.RecordService;
import com.ntd.service.dao.RecordDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Record Management System", description = "Operations pertaining to records in Record Management System")
@RestController
@RequestMapping("/v1/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @ApiOperation(value = "Create a new record", response = RecordDTO.class)
    @PostMapping
    public RecordDTO createRecord(@ApiParam(value = "Record details to create", required = true) @RequestBody RecordDTO recordDTO) {
        return recordService.createRecord(recordDTO);
    }

    @ApiOperation(value = "Update an existing record", response = RecordDTO.class)
    @PutMapping("/{id}")
    public RecordDTO updateRecord(@ApiParam(value = "Record ID to update", required = true) @PathVariable Long id,
                                  @ApiParam(value = "Updated record details", required = true) @RequestBody RecordDTO recordDTO) {
        return recordService.updateRecord(id, recordDTO);
    }

    @ApiOperation(value = "View a list of available records", response = List.class)
    @GetMapping
    public List<RecordDTO> getAllRecords() {
        return recordService.getAllRecords();
    }

    @ApiOperation(value = "Get a record by ID", response = RecordDTO.class)
    @GetMapping("/{id}")
    public RecordDTO getRecord(@ApiParam(value = "Record ID to get", required = true) @PathVariable Long id) {
        return recordService.getRecord(id);
    }

    @ApiOperation(value = "Find records by user ID", response = Page.class)
    @GetMapping("/user/{userId}")
    public Page<RecordDTO> findByUserId(@ApiParam(value = "User ID to find records", required = true) @PathVariable String userId,
                                        @ApiParam(value = "Pagination details") Pageable pageable) {
        return recordService.findByUserId(userId, pageable);
    }
}
