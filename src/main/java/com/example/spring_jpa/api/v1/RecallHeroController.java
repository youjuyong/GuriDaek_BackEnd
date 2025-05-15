package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.api.ErrorCode;
import com.example.spring_jpa.api.Message;
import com.example.spring_jpa.api.v1.repository.TbCommCdRepo;
import com.example.spring_jpa.data.GuriSQL_COMM;
import com.example.spring_jpa.data.GuriSQL_HERO;
import com.example.spring_jpa.data.GuriSQL_HORSE;
import com.example.spring_jpa.object.TbCommCd;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/hero")
public class RecallHeroController implements BackendApi {

    private final GuriSQL_COMM commSQL;
    private final GuriSQL_HERO heroSQL;

    private final TbCommCdRepo tbCommCdRepo;

    @Override
    @PostConstruct
    public void assertConnection() {
    }

    @Override
    @PostConstruct
    public void loadData() {
    }

    @Operation(method = "GET",
            summary = "영웅 테이블 헤더 공통 리스트 반환",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "recall-table-header",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getRecallHeroTableHeader() {
        Map<String, List<TbCommCd>> resultList = new HashMap<String, List<TbCommCd>>();

		List<TbCommCd> heroType        =  tbCommCdRepo.findByIdAndUseYn("RHDT", "Y");
		List<TbCommCd> versionType     =  tbCommCdRepo.findByIdAndUseYn("RHVT", "Y");
		List<TbCommCd> heroWeaponType  =  tbCommCdRepo.findByIdAndUseYn("RHWT", "Y");
		List<TbCommCd> heroBurpType    =  tbCommCdRepo.findByIdAndUseYn("RHBT", "Y");

        resultList.put("heroType", heroType);
        resultList.put("versionType", versionType);
        resultList.put("heroWeaponType", heroWeaponType);
        resultList.put("heroBurpType", heroBurpType);

        return ResponseEntity.ok(resultList);
    }

      @Operation(method = "GET",
            summary = "소환영웅 리스트 조회",
            parameters = {
                    @Parameter(name = "heroType", description = "소환영웅 전투 타입 조건(없음 : RHDT0, 전투/공격형 : RHDT1, 전투/방어형 : RHDT2, 전투/마법형 : RHDT3, 제조/보조형 : RHDT4, 버프형 : RHDT5, 파병용 : RHDT6)", required = true, example = "RHDT1, RHDT2, RHDT3, RHDT4, RHDT5, RHDT6"),
                    @Parameter(name = "versionType", description = "소환영웅 버전 타입 조건(없음 : RHVT0, 구버전 : RHVT1, 신버전 : RHVT2)", required = true, example = "RHVT0, RHVT1, RHVT2"),
                    @Parameter(name = "heroWeaponType", description = "소환영웅 무기 타입 조건(없음 : RHWT0, 총 : RHWT1, 창 : RHWT2, 도끼 : RHWT3, 검 : RHWT4, 지팡이 : RHWT5, 활 : RHWT6, 대포 : RHWT7, 대포제외 모든 무기 : RHWT8, 모든 무기 : RHWT9)", required = true, example = "RHWT0, RHWT1, RHWT2, RHWT3, RHWT4, RHWT5, RHWT6, RHWT7, RHWT8, RHWT9"),
                    @Parameter(name = "heroBurpType", description = "소환영웅 버프 타입(없음 : RHBT0, 물리공격력 : RHBT1, 마법공격력 : RHBT2, 공격력/방어력 : RHBT3, 방어력 : RHBT4)", required = true, example = "RHBT0, RHBT1, RHWT2, RHBT3, RHBT4"),
                    @Parameter(name = "keyword", description = "소환영웅 이름", required = true, example = "이사부"),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getHeroList(@RequestParam Map<String, String> map) {
        List<Map<String, Object>> resultList = null;

         try {
            resultList = heroSQL.heroList(map);
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

    @Operation(method = "PUT",
            summary = "영웅 리뷰 입력",
            parameters = {
                    @Parameter(name = "objectId", description = "영웅 ID", required = true, example = "REC01 ~ REC44"),
                    @Parameter(name = "userId", description = "유저 ID", required = true, example = "admin"),
                    @Parameter(name = "score", description = "영웅 평점", required = true, example = "1 ~ 5"),
                    @Parameter(name = "content", description = "리뷰", required = true, example = "너무 잘싸운다"),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @PutMapping(value = "review",
            produces = {"application/json"}
    )
    public ResponseEntity<?> putHeroRevieInfo(@RequestParam Map<String, Object> map) {
        int result = 0;

        try {
            result = heroSQL.putHeroRevieInfo(map);
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
            summary = "영웅 리뷰 리스트",
             parameters = {
                     @Parameter(name = "objectId", description = "영웅 ID", required = true, example = "REC01 ~ REC44")
             },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "review-list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getHeroReviewList(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> resultList = Arrays.asList();

        try {
            resultList = heroSQL.getHeroReviewList(map);
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

    @Operation(method = "DELETE",
            summary = "영웅 리뷰 삭제",
            parameters = {
                     @Parameter(name = "objectId", description = "영웅 ID", required = true, example = "REC01 ~ REC44")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @DeleteMapping(value = "review-remove",
            produces = {"application/json"}
    )
    public ResponseEntity<?> deleteHeroRevieInfo(@RequestParam Map<String, Object> map) {
        int result = 0;

        try {
            result = heroSQL.deleteHeroRevieInfo(map);
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
