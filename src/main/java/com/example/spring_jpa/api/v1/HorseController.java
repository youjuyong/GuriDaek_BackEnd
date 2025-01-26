package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.api.v1.repository.BitSttsRepo;
import com.example.spring_jpa.api.v1.repository.TestRepo;
import com.example.spring_jpa.data.GuriSQL_COMM;
import com.example.spring_jpa.data.GuriSQL_HORSE;
import com.example.spring_jpa.object.Bit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/horse")
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
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bit.class)))),
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
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bit.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getHorseList(@RequestParam Map<String, String> map) {
        System.out.println(map);
        List<Map<String, Object>> resultList = horseSQL.horseList(map);
 System.out.println(resultList);
        return ResponseEntity.ok(resultList);
    }

     @GetMapping(value = "img-down",
            produces = {"application/json"}
    )
    public int getFileDown() {

            return 1;
    }

}
