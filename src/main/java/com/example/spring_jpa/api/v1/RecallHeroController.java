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
