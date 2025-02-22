package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.api.ErrorCode;
import com.example.spring_jpa.api.Message;
import com.example.spring_jpa.data.GuriSQL_BOARD;
import com.example.spring_jpa.data.GuriSQL_USER;
import com.example.spring_jpa.jwt.MemberRequestDto;
import com.example.spring_jpa.jwt.TokenDto;
import com.example.spring_jpa.jwt.TokenProvider;
import com.example.spring_jpa.object.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardController implements BackendApi {

    private final GuriSQL_BOARD boardSQL;

    @Override
    @PostConstruct
    public void assertConnection() {
    }

    @Override
    @PostConstruct
    public void loadData() {
    }

    @Operation(method = "GET",
            summary = "이벤트 공지 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "event-board-list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEventBoard() {
        List<Map<String, Object>> resultList = Arrays.asList();

        try {
            resultList = boardSQL.getEvnetBoardList();
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

     @Operation(method = "GET",
            summary = "이벤트 날짜 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "event-board-day-time",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEventBoardDateTime(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> resultList = Arrays.asList();

        try {
            resultList = boardSQL.getEventBoardDateTime(map);
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
            summary = "이벤트 날짜 신청",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @PutMapping(value = "event-board-day-time-sumit",
            produces = {"application/json"}
    )
    public ResponseEntity<?> putEventDayTime(@RequestParam Map<String, Object> map) {
        int result = 0;

        try {
            result = boardSQL.putEventDayTime(map);
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

    @Operation(method = "GET",
            summary = "이벤트 날짜 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "event-board-user-check",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEventBoardUserCheck(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> resultList = Arrays.asList();

        try {
            resultList = boardSQL.getEventBoardUserCheck(map);
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

    @Operation(method = "GET",
            summary = "이벤트 차트 날짜 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "event-board-day-time-chart",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEventBoardDateTimeChart(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> resultList = Arrays.asList();

        try {
            resultList = boardSQL.getEventBoardDateTimeChart(map);
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

     @Operation(method = "GET",
            summary = "탈것 리뷰 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "event-board-review-list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEventBoardReviewList(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> resultList = Arrays.asList();

        try {
            resultList = boardSQL.getEventBoardReviewList(map);
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
            summary = "이벤트 공지 리뷰 입력",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @PutMapping(value = "review",
            produces = {"application/json"}
    )
    public ResponseEntity<?> putEventBoardReviewInfo(@RequestParam Map<String, Object> map) {
        int result = 0;

        try {
            result = boardSQL.putEventBoardReviewInfo(map);
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
            summary = "이벤트 공지 리뷰 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @DeleteMapping(value = "review-remove",
            produces = {"application/json"}
    )
    public ResponseEntity<?> deleteEventBoardRevieInfo(@RequestParam Map<String, Object> map) {
        int result = 0;

        try {
            result = boardSQL.deleteEventBoardRevieInfo(map);
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
