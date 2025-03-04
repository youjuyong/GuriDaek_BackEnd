package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ErrorCode;
import com.example.spring_jpa.api.Message;
import com.example.spring_jpa.data.GuriSQL_COMM;
import com.google.common.collect.Maps;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.example.spring_jpa.data.GuriSQL_STAT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/stat")
public class StatController implements BackendApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatController.class);
    private final GuriSQL_STAT statSQL;
    private final GuriSQL_COMM commSQL;

    @Override
    @PostConstruct
    public void assertConnection() {
    }

    @Override
    @PostConstruct
    public void loadData() {
    }

    @Operation(method = "GET",
            summary = "양이 순위 통계",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "yang-rank",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getYangRankList() {
        List<Map<String, Object>> resultList = statSQL.yangiRankList();
        return ResponseEntity.ok(resultList);
    }

    @Operation(method = "POST",
            summary = "양이 데이터 업로드",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @Transactional
    @PostMapping(value = "yang-data-upload", produces = {"application/json"})
    public ResponseEntity<?> postYangDataUpload(HttpServletRequest request) {
        FileInputStream fis = null;
        String filePath = request.getSession().getServletContext().getRealPath("/");

        try {
            fis = new FileInputStream(filePath + "/report.xls");
            Workbook wb = new HSSFWorkbook(fis);
            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                HashMap<String, Object> dataMap = Maps.newHashMap();

                Row row = sheet.getRow(i);
                if (row == null) {
                    break;
                }

                if (!String.valueOf(row.getCell(0)).isEmpty() && !String.valueOf(row.getCell(0)).equals("null")) {
                    for (int j = 0; j <= 3; j++) {
                        Cell cell = row.getCell(j);
                        String value = "";
                        if (cell != null) {
                            value = switch (cell.getCellType()) {
                                case STRING -> cell.getStringCellValue();
                                case NUMERIC -> {
                                    double numericCellValue = cell.getNumericCellValue();
                                    yield String.valueOf((int) numericCellValue);
                                }
                                default -> "";
                            };
                        }

                        if (j == 0) {
                            int village_Id = statSQL.yangVillageList(value);
                            dataMap.put("villageId", village_Id);
                        } else if (j == 1) {
                            dataMap.put("time", value);
                        } else if (j == 2) {
                            dataMap.put("killYang", value);
                        } else {
                            dataMap.put("timeType", value);
                        }
                    }
                }

                statSQL.insertYangKillsVillage(dataMap);
            }
        } catch (RuntimeException | IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BackendApi.getErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Message.TRANSACTION_FAILURE,
                            ErrorCode.INVALID_PARAMETER,
                            ex.getMessage()
                    ));
        }


        return ResponseEntity.ok(BackendApi.getSuccessMessage(HttpStatus.OK.value(), Message.SUCCESS));
    }

    @Operation(method = "POST",
            summary = "마을 주신수 업로드",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @Transactional
    @PostMapping(value = "human-cnt-upload", produces = {"application/json"})
    public ResponseEntity<?> posthumanCntDataUpload(HttpServletRequest request) {
        FileInputStream fis = null;
        String filePath = request.getSession().getServletContext().getRealPath("/");

        try {
            fis = new FileInputStream(filePath + "/reportHuman.xls");
            Workbook wb = new HSSFWorkbook(fis);
            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                HashMap<String, Object> dataMap = Maps.newHashMap();

                Row row = sheet.getRow(i);
                if (row == null) {
                    break;
                }

                if (!String.valueOf(row.getCell(0)).isEmpty() && !String.valueOf(row.getCell(0)).equals("null")) {
                    for (int j = 0; j <= 2; j++) {
                        Cell cell = row.getCell(j);
                        String value = "";
                        if (cell != null) {
                            value = switch (cell.getCellType()) {
                                case STRING -> cell.getStringCellValue();
                                case NUMERIC -> {
                                    double numericCellValue = cell.getNumericCellValue();
                                    yield String.valueOf((int) numericCellValue);
                                }
                                default -> "";
                            };
                        }

                        if (j == 0) {
                            int village_Id = statSQL.yangVillageList(value);
                            dataMap.put("villageId", village_Id);
                        } else if (j == 1) {
                            dataMap.put("time", value);
                        } else {
                            dataMap.put("humanCnt", value);
                        }
                    }
                }

                statSQL.insertVillageHumanUpload(dataMap);
            }
        } catch (RuntimeException | IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BackendApi.getErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Message.TRANSACTION_FAILURE,
                            ErrorCode.INVALID_PARAMETER,
                            ex.getMessage()
                    ));
        }


        return ResponseEntity.ok(BackendApi.getSuccessMessage(HttpStatus.OK.value(), Message.SUCCESS));
    }

    @Operation(method = "GET",
            summary = "마을별 주민수 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "village-human-cnt",
            produces = {"application/json"}
    )
    public ResponseEntity<?> selectVillageHumanCountList() {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        resultList = statSQL.selectVillageHumanCountList();


        return ResponseEntity.ok(resultList);
    }

    @Operation(method = "POST",
            summary = "장인 리스트 업로드",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @Transactional
    @PostMapping(value = "crats-man-upload", produces = {"application/json"})
    public ResponseEntity<?> postCraftsManUpload(HttpServletRequest request) {
        FileInputStream fis = null;
        String filePath = request.getSession().getServletContext().getRealPath("/");

        try {
            fis = new FileInputStream(filePath + "/reportCrafts.xls");
            Workbook wb = new HSSFWorkbook(fis);
            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                HashMap<String, Object> dataMap = Maps.newHashMap();

                Row row = sheet.getRow(i);
                if (row == null) {
                    break;
                }

                if (!String.valueOf(row.getCell(0)).isEmpty() && !String.valueOf(row.getCell(0)).equals("null")) {
                    for (int j = 0; j <= 5; j++) {
                        Cell cell = row.getCell(j);
                        String value = "";
                        if (cell != null) {
                            value = switch (cell.getCellType()) {
                                case STRING -> cell.getStringCellValue();
                                case NUMERIC -> {
                                    double numericCellValue = cell.getNumericCellValue();
                                    yield String.valueOf((int) numericCellValue);
                                }
                                default -> "";
                            };
                        }
                        if (j == 0) {
                            dataMap.put("craftsId", value);
                        } else if (j == 1) {

                            if (value.equals("없음")) {
                                int village_Id = 999;
                                dataMap.put("villageId", village_Id);
                            } else {
                                int village_Id = statSQL.yangVillageList(value);
                                dataMap.put("villageId", village_Id);
                            }
                        } else if (j == 2) {
                            dataMap.put("craftsManName", value);
                        } else if (j == 3) {
                            dataMap.put("craftsLevel", value);
                        } else if (j == 4) {
                            dataMap.put("handLevel", value);
                        } else {
                            dataMap.put("craftsType", value);
                        }
                    }
                }
                if (dataMap.isEmpty()) {
                    continue;
                }
                System.out.println("dataMap " + dataMap);
                statSQL.insertCraftsManList(dataMap);
            }
        } catch (RuntimeException | IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BackendApi.getErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Message.TRANSACTION_FAILURE,
                            ErrorCode.INVALID_PARAMETER,
                            ex.getMessage()
                    ));
        }


        return ResponseEntity.ok(BackendApi.getSuccessMessage(HttpStatus.OK.value(), Message.SUCCESS));
    }

    @Operation(method = "GET",
            summary = "마을 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "village-list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> selectVillageList() {
        List<Map<String, Object>> resultList = Lists.newArrayList();
        try {
            resultList = commSQL.villageListInfo();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BackendApi.getErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Message.TRANSACTION_FAILURE,
                            ErrorCode.INVALID_PARAMETER,
                            ex.getMessage()
                    ));
        }
        return ResponseEntity.ok(resultList);
    }

    @Operation(method = "GET",
            summary = "마을 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "village-human-statics",
            produces = {"application/json"}
    )
    public ResponseEntity<?> staticsVillageHumanCnt(@RequestParam Map<String, Object> map) {
        Map<String, List<Map<String, Object>>> resultList = new HashMap<String, List<Map<String, Object>>>();
        try {
            List<Map<String, Object>> preCurrentCntList = statSQL.staticsVillageHumanCnt(map);
            List<Map<String, Object>> currentMonthCnt = statSQL.staticsCurrentHumanCnt(map);
            resultList.put("preCnt", preCurrentCntList);
            resultList.put("curCnt", currentMonthCnt);
        } catch (Exception ex) {
            LOGGER.info("staticsVillageHumanCnt are " + ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BackendApi.getErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Message.TRANSACTION_FAILURE,
                            ErrorCode.INVALID_PARAMETER,
                            ex.getMessage()
                    ));
        }
        return ResponseEntity.ok(resultList);
    }

    @Operation(method = "GET",
            summary = "양이 전쟁 전공 통계",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "yang-data-statics",
            produces = {"application/json"}
    )
    public ResponseEntity<?> staticsYangData(@RequestParam Map<String, Object> map) {
        Map<String, List<Map<String, Object>>> resultList = new HashMap<String, List<Map<String, Object>>>();
        try {
            List<Map<String, Object>> preYangDataList = statSQL.staticsYangCnt(map);
            List<Map<String, Object>> currentYangDataCnt = statSQL.staticsCurrentYangCnt(map);
            resultList.put("preCnt", preYangDataList);
            resultList.put("curCnt", currentYangDataCnt);
        } catch (Exception ex) {
            LOGGER.info("staticsYangData are " + ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BackendApi.getErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Message.TRANSACTION_FAILURE,
                            ErrorCode.INVALID_PARAMETER,
                            ex.getMessage()
                    ));
        }
        return ResponseEntity.ok(resultList);
    }

}
