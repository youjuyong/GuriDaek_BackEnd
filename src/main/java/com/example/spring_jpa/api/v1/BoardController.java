package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.*;
import com.example.spring_jpa.api.v1.repository.EventBoardPrizeRepo;
import com.example.spring_jpa.api.v1.repository.EventBoardRepo;
import com.example.spring_jpa.data.GuriSQL_BOARD;
import com.example.spring_jpa.data.GuriSQL_USER;
import com.example.spring_jpa.jwt.MemberRequestDto;
import com.example.spring_jpa.jwt.TokenDto;
import com.example.spring_jpa.jwt.TokenProvider;
import com.example.spring_jpa.object.EventBoard;
import com.example.spring_jpa.object.EventBoardPrize;
import com.example.spring_jpa.object.Member;
import com.google.common.collect.Maps;
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
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.SQLException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
    private final GuriSQL_BOARD       boardSQL;
    private final EventBoardRepo      evntRepo;
    private final EventBoardPrizeRepo eventPrizeRepo;
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
            summary = "이벤트 시간 공지 리뷰 리스트",
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
      @Transactional
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

    @Operation(method = "POST",
            summary = "이벤트 공지 리뷰 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @PostMapping(value = "event-board-file-download",
            produces = {"application/json"}
    )
    @CrossOrigin("*")
    public void eventBoardFileDownload(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> map) {
        Map<String, Object> fileMap = null;
        BufferedInputStream ips = null;
        OutputStream ops = null;
        response.reset();
    System.out.println(map);
        try {
            fileMap = boardSQL.fileDownload(map);
                System.out.println(fileMap);
            String filenm = fileMap.get("FILE_NAME").toString();
            String encordedFilename = URLEncoder.encode(filenm, "UTF-8").replace("+", "%20");
              System.out.println(encordedFilename);
            response.setContentType(getMimeType(filenm));
            response.setHeader("Content-Disposition", "attachment; filename=\'" + filenm + "\';");
            response.setHeader("Content-Disposition", "attachment;filename=" + encordedFilename + ";filenm*= UTF-8''" + encordedFilename);
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");
            if ( ops == null ) return;
            ops = response.getOutputStream();
            Blob blob = (Blob) fileMap.get("FILE_DATA");
            response.setContentLength((int) blob.length());

            ips = new BufferedInputStream(blob.getBinaryStream());
            byte[] buffer = new byte[2048];
            int r = 0;
            while ((r = ips.read(buffer)) != -1) {
                ops.write(buffer, 0, r);
            }
        } catch ( RuntimeException ex ) {
           throw new RuntimeException(ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(ips != null){
				try {
					ips.close();
				} catch (IOException e) {
				}
			}
			try {
                if ( ops == null ) return;
				else ops.flush();
			} catch (IOException e) {
			}
        }

        fileMap=null;
    }

    private String getMimeType(String name) {
		String ret = "application/octet-stream";
		if (name.contains(".jpg") || name.contains(".jpeg")) {
			ret = "image/jpg";
		} else if (name.contains(".xls")) {
			ret = "application/vnd.ms-excel";
		} else if (name.contains(".ppt")) {
			ret = "application/vnd.ms-powerpoint";
		} else if (name.contains(".gif")) {
			ret = "image/gif";
		} else if (name.contains(".pdf")) {
			ret = "application/pdf";
		} else if (name.contains(".txt")) {
			ret = "text/plain";
		} else if (name.contains(".hwp")) {
			ret = "application/x-hwp";
		} else if (name.contains(".png")) {
			ret = "image/png";
		} else if (name.contains(".xlsx")) {
			ret = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else if (name.contains(".docx")) {
			ret = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else if (name.contains(".pptx")) {
			ret = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		}
		return ret;
	}

    @Operation(method = "GET",
            summary = "공지 컨텐트 미지ㅣ 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "event-board-content-list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEventBoardContentList(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> contentList = Arrays.asList();
        List<Map<String, Object>> imgList = Arrays.asList();

        Map<String, List<Map<String, Object>>> resultMap = Maps.newHashMap();

        try {
            contentList = boardSQL.getEventBoardContentList(map);
            imgList     = boardSQL.getEventBoardContentImgList(map);

            if ( imgList.size() != 0 ) {
                for ( int i = 0; i < imgList.size(); i++ ) {

                    if( imgList.get(i).get("CONT_IMAG_DATA") == null) {
				        continue;
			        }

                    byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64((byte[]) imgList.get(i).get("CONT_IMAG_DATA"));
                    String encoedeString = new String(encoded);
                    String extension = FilenameUtils.getExtension(String.valueOf(imgList.get(i).get("CONT_IMAG_NAME")));
                    imgList.get(i).put("extension", extension);
                    imgList.get(i).remove("CONT_IMAG_DATA");
                    imgList.get(i).put("encodeStr", encoedeString);

                }
            }
            resultMap.put("contentList", contentList);
            resultMap.put("imgList",         imgList);
        } catch ( RuntimeException ex ) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }

        return ResponseEntity.ok(resultMap);
    }

    @Operation(method = "GET",
            summary = "공지 컨텐트 미지ㅣ 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "event-board-prize-list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEventBoardPrizeList(@RequestParam Map<String, Object> map) {

        List<Map<String, Object>> resultList = Arrays.asList();
        try {
            resultList = boardSQL.getPrizeUserList(map);
        } catch ( RuntimeException ex ) {
          LOGGER.info("getEventBoardPrizeList are " + ex.toString());
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
            summary = "이벤트 경품 추첨",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @PostMapping(value = "event-board-prize-sumit",
            produces = {"application/json"}
    )
     @Transactional
    public ResponseEntity<?> postEventPrizeSumit(@RequestParam Map<String, Object> map) {
        List<EventBoardPrize> evtBoardP = null;
        List<EventBoardPrize> evtBoardNotUser = null;

        List<Map<String, Object>> resultList = Arrays.asList();
        String textId = String.valueOf(map.get("text_id"));
        String userId = String.valueOf(map.get("user_id"));

        String SuccessCode =  "";

        try {
                 evtBoardP       = eventPrizeRepo.findByIdAndUserId(textId, userId);
                 evtBoardNotUser = eventPrizeRepo.findByUserIdIsNullAndId(textId);

                 // 경품 신청을 안했을시
                 if ( evtBoardP.size() == 0 ) {

                     // 경품 목록이 다 나갔을 경우
                     if ( evtBoardNotUser.size() == 0) {
                          SuccessCode = Message.EVENT_PRIZE_USER_LIMIT;
                     } else {
                          int index = (int) (Math.random() * evtBoardNotUser.size());
                        EventBoardPrize evt = evtBoardNotUser.get(index);
                        evt.setUserId(userId);
                        evt.setUserId(evt.getUserId());

                        SuccessCode = Message.EVENT_PRIZE_SUMIT_SUCCESS;
                     }
                 // 경품 신청을 이미 했을시
                 } else {
                     SuccessCode = Message.EVENT_PRIZE_USER_YN;
                 }
        } catch ( RuntimeException ex ) {
            LOGGER.info("getEventBoardPrizeList are " + ex.toString());
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							null
					));
        }


        return ResponseEntity.status(HttpStatus.OK)
                            .body(BackendApi.getSuccessMessage(
                                    HttpStatus.OK.value(),
                                    SuccessCode
                            ));
    }

}
