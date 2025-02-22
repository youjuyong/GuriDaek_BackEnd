package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.api.ErrorCode;
import com.example.spring_jpa.api.Message;
import com.example.spring_jpa.data.GuriSQL_COMM;
import com.example.spring_jpa.data.GuriSQL_HORSE;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/horse")
public class HorseController implements BackendApi {

    private final GuriSQL_COMM commSQL;
    private final GuriSQL_HORSE horseSQL;

    @Override
    @PostConstruct
    public void assertConnection() {
    }

    @Override
    @PostConstruct
    public void loadData() {
    }

    @Operation(method = "GET",
            summary = "탈것 테이블 헤더 공통 리스트 반환",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "stall-table-header",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getHorseStallTableHeader() {
        Map<String, List<Map<String, Object>>> resultList = new HashMap<String, List<Map<String, Object>>>();

		List<Map<String, Object>> rideLimitType          =  commSQL.horseRideLimitType();
        List<Map<String, Object>> horseBurpType          =  commSQL.horseBurpType();
        List<Map<String, Object>> horseLifeType          =  commSQL.horseLifeType();
        List<Map<String, Object>> horseHouseBurpType     =  commSQL.horseHouseBurpType();
        List<Map<String, Object>> horseHouseBurpPercent  =  commSQL.horseHouseBurpPercent();

		resultList.put("rideLimitType", rideLimitType);
		resultList.put("horseBurpType", horseBurpType);
		resultList.put("horseLifeType", horseLifeType);
		resultList.put("horseHouseBurpType", horseHouseBurpType);
		resultList.put("horseHouseBurpPercent", horseHouseBurpPercent);

        return ResponseEntity.ok(resultList);
    }

    @Operation(method = "GET",
            summary = "탈것 리스트 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getHorseList(@RequestParam Map<String, String> map) {
        List<Map<String, Object>> resultList = horseSQL.horseList(map);
        return ResponseEntity.ok(resultList);
    }

     @GetMapping(value = "img-down",
            produces = {"application/json"}
    )
    public int getFileDown() {

            return 1;
    }

    @Operation(method = "GET",
            summary = "탈것 리뷰 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "review-list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getHorseReview(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> resultList = Arrays.asList();

        try {
            resultList = horseSQL.getHorseReviewList(map);
        } catch ( RuntimeException ex ) {
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

    @Operation(method = "PUT",
            summary = "탈것 리뷰 입력",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @PutMapping(value = "review",
            produces = {"application/json"}
    )
    public ResponseEntity<?> putHorseRevieInfo(@RequestParam Map<String, Object> map) {
        int result = 0;

        try {
            result = horseSQL.putHorseRevieInfo(map);
        } catch ( RuntimeException ex ) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }
        return ResponseEntity.ok(result);
    }

     @Operation(method = "DELETE",
            summary = "탈것 리뷰 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @DeleteMapping(value = "review-remove",
            produces = {"application/json"}
    )
    public ResponseEntity<?> deleteHorseRevieInfo(@RequestParam Map<String, Object> map) {
        int result = 0;

        try {
            result = horseSQL.deleteHorseRevieInfo(map);
        } catch ( RuntimeException ex ) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }
        return ResponseEntity.ok(result);
    }
}
