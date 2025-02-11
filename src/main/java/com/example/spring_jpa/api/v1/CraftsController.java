package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.api.ErrorCode;
import com.example.spring_jpa.api.Message;
import com.example.spring_jpa.data.GuriSQL_COMM;
import com.example.spring_jpa.data.GuriSQL_CRAFTS;
import com.example.spring_jpa.object.Bit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/crafts")
public class CraftsController implements BackendApi {

    private final GuriSQL_COMM commSQL;
    private final GuriSQL_CRAFTS craftsSQL;

    @Override
    @PostConstruct
    public void assertConnection() {
    }

    @Override
    @PostConstruct
    public void loadData() {
    }

    @Operation(method = "GET",
            summary = "장인목록 헤더 공통 리스트 반환",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bit.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "crafts-table-header",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getCraftsTableHeader() {
        Map<String, List<Map<String, Object>>> resultList = new HashMap<String, List<Map<String, Object>>>();

        try {
             List<Map<String, Object>> craftsManMakeType    =  commSQL.craftsManMakeType();
		     List<Map<String, Object>> villageListInfo =  commSQL.villageListInfo();

		     resultList.put("craftsManMakeType", craftsManMakeType);
		     resultList.put("villageListInfo", villageListInfo);
        } catch ( Exception ex ) {
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
            summary = "장인목록 리스트 반환",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bit.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getCraftsListInfo(@RequestParam Map<String, String> map) {
        List<Map<String, Object>> craftsManList   = Arrays.asList();

        try {
             craftsManList    =  craftsSQL.getCraftsManList(map);

        } catch ( Exception ex ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        }

        return ResponseEntity.ok(craftsManList);
    }


}
