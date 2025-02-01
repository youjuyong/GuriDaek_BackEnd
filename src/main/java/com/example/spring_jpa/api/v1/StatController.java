package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.data.GuriSQL_COMM;
import com.example.spring_jpa.data.GuriSQL_HORSE;
import com.example.spring_jpa.data.GuriSQL_STAT;
import com.example.spring_jpa.object.Bit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/stat")
public class StatController implements BackendApi {

    private final GuriSQL_STAT statSQL;

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
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bit.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "yang-rank",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getHorseList() {
        List<Map<String, Object>> resultList = statSQL.yangiRankList();
        return ResponseEntity.ok(resultList);
    }

}
