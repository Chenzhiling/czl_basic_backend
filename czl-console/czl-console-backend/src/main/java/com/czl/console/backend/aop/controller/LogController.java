package com.czl.console.backend.aop.controller;

import com.czl.console.backend.aop.log.Log;
import com.czl.console.backend.aop.dto.LogCriteria;
import com.czl.console.backend.aop.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "日志管理")
@RestController
@RequestMapping("log/")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }


    @ApiOperation("查询异常详情")
    @PostMapping(value = "/findByErrDetail")
    public ResponseEntity<Object> findByErrDetail(@RequestParam Long id) {
        return new ResponseEntity<>(logService.findByErrDetail(id), HttpStatus.OK);
    }


    @ApiOperation("高级筛选用户日志")
    @PostMapping(value = "/selectLogInfo")
    public ResponseEntity<Object> selectLogInfo(@RequestBody LogCriteria criteria) {
        return new ResponseEntity<>(logService.selectLogInfo(criteria), HttpStatus.OK);
    }

    @Log("删除所有日志")
    @ApiOperation("删除所有日志")
    @PreAuthorize("@czl.check('log:delete')")
    @PostMapping(value = "/delAllLogs")
    public ResponseEntity<Object> delAllLogs() {
        return new ResponseEntity<>(logService.delAllLogs(), HttpStatus.OK);
    }
}
