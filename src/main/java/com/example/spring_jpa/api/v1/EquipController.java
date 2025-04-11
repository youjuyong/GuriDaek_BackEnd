package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.api.ErrorCode;
import com.example.spring_jpa.api.Message;
import com.example.spring_jpa.data.GuriSQL_EQUIP;
import com.example.spring_jpa.object.TbUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/equip")
public class EquipController implements BackendApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquipController.class);
    private final GuriSQL_EQUIP equipSQL;

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
    @GetMapping(value = "equip-list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEventBoard(@RequestParam Map<String , Object> map) {
        List<Map<String, Object>> resultList = Arrays.asList();

        try {
            resultList = equipSQL.equipList(map);

            if (resultList.size() > 0) {

                for (Map<String, Object> remap : resultList) {
                    byte[] bytes = (byte[]) remap.get("EQUIP_IMG");
                    Blob blob = new SerialBlob(bytes);
                    byte[] img = blob.getBytes(1, (int) blob.length());

                    if (img != null) {
                        String encodeStr = Base64.encodeBase64String(img);
                        remap.put("imgUrl", encodeStr);
                    }
                }

            }
        } catch ( SerialException se ) {
            log.error(se.getMessage());
        } catch ( RuntimeException ex) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(resultList);
    }

    @Operation(method = "GET",
            summary = "장비 상세 리스트",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @GetMapping(value = "equip-detl-list",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEquipDetlList(@RequestParam Map<String , Object> map) {
        List<Map<String, Object>> resultList = Arrays.asList();

        try {
            resultList = equipSQL.equipDetlList(map);

            if (resultList.size() > 0) {

                for (Map<String, Object> remap : resultList) {
                    byte[] bytes = (byte[]) remap.get("EQUIP_IMG");
                    Blob blob = new SerialBlob(bytes);
                    byte[] img = blob.getBytes(1, (int) blob.length());

                    if (img != null) {
                        String encodeStr = Base64.encodeBase64String(img);
                        remap.put("imgUrl", encodeStr);
                    }
                }

            }
        } catch ( SerialException se ) {
            log.error(se.getMessage());
        } catch ( RuntimeException ex) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(BackendApi.getErrorMessage(
							HttpStatus.INTERNAL_SERVER_ERROR.value(),
							Message.TRANSACTION_FAILURE,
							ErrorCode.INVALID_PARAMETER,
							ex.getMessage()
					));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(resultList);
    }

     @Operation(method = "PUT",
            summary = "장비 대여 신청",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @PutMapping(value = "equip-lent-name",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getEquipLent(@RequestParam Map<String , Object> map) {
         System.out.println("test#################################");
        int result = 0;
        try {
            result = equipSQL.equipLentRequest(map);
        } catch ( RuntimeException ex) {
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
