package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.api.ErrorCode;
import com.example.spring_jpa.api.Message;
import com.example.spring_jpa.api.v1.repository.EventBoardRepo;
import com.example.spring_jpa.data.GuriSQL_BOARD;
import com.example.spring_jpa.data.GuriSQL_USER;
import com.example.spring_jpa.jwt.MemberRequestDto;
import com.example.spring_jpa.jwt.TokenDto;
import com.example.spring_jpa.jwt.TokenProvider;
import com.example.spring_jpa.object.EventBoard;
import com.example.spring_jpa.object.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.CookieGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
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
    private final EventBoardRepo evntRepo;

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

      @Operation(method = "POST",
            summary = "조회수",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @PostMapping(value = "viewCount",
            produces = {"application/json"}
    )
      @CrossOrigin("*")
    public ResponseEntity<?> viewCount(@RequestParam  Map<String, Object> map,   HttpServletRequest request,
                           HttpServletResponse response ) {
        List<Map<String, Object>> resultList = Arrays.asList();
        String textId = String.valueOf(map.get("text_id"));
        CookieGenerator cg = new CookieGenerator();
         Cookie[] cookies = request.getCookies();
         Cookie cookie = null;
         boolean isCookie = false;
        try {

               EventBoard evt = evntRepo.findById(textId);

               for ( int i =0 ; cookies != null && i < cookies.length; i ++ ) {

                   if ( cookies[i].getName().equals("postView") ){
                       cookie = cookies[i];

                       if ( !cookie.getValue().contains("[" + evt.getId() + "]")) {
                            evt.addViewCount();
                            evt.setRtrvCnt(evt.getRtrvCnt());
                            cookie.setValue(cookie.getValue() + "[" + evt.getId() + "]");
                       }

                       isCookie = true;
                       break;
                   }
               }

               if ( !isCookie ) {
                    evt.addViewCount();
                    evt.setRtrvCnt(evt.getRtrvCnt());
                    cookie = new Cookie("postView", "[" + evt.getId() + "]"); // oldCookie에 새 쿠키 생성
               }

               // 쿠키 유지시간을 오늘 하루 자정까지로 설정
            long todayEndSecond = LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC);
            long currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            cookie.setPath("/");

            cookie.setMaxAge((int) (todayEndSecond - currentSecond));
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

//            response.addHeader("Set-Cookie","name=value; path=/; SameSite=Lax" );
//
//            ResponseCookie strictCookie = ResponseCookie.from("strict","strict").path("/").sameSite("strict").domain(".guridaek.com")
//                    .build();
//            response.addHeader("Set-Cookie", strictCookie.toString());
//
//            ResponseCookie laxCookie = ResponseCookie.from("Lax","Lax").path("/").sameSite("Lax").domain(".guridaek.com")
//                    .build();
//            response.addHeader("Set-Cookie", laxCookie.toString());
//
//            ResponseCookie noneCookie = ResponseCookie.from("none","none").path("/").sameSite("none").domain(".guridaek.com")
//                    .build();
//            response.addHeader("Set-Cookie", noneCookie.toString());
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
