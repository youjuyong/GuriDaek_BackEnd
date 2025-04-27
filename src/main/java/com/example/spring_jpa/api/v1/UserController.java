package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.ApiErrorMessage;
import com.example.spring_jpa.api.BackendApi;
import com.example.spring_jpa.api.ErrorCode;
import com.example.spring_jpa.api.Message;
import com.example.spring_jpa.api.v1.repository.TbUserRepo;
import com.example.spring_jpa.data.GuriSQL_USER;
import com.example.spring_jpa.jwt.MemberRequestDto;
import com.example.spring_jpa.jwt.TokenDto;
import com.example.spring_jpa.jwt.TokenProvider;
import com.example.spring_jpa.object.TbUser;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.spring_jpa.object.Member;

import javax.swing.text.html.Option;

@Slf4j
@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController implements BackendApi {

    private final GuriSQL_USER userSQL;
    private final TbUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Override
    @PostConstruct
    public void assertConnection() {
    }

    @Override
    @PostConstruct
    public void loadData() {
    }

    @PostMapping(value = "login")
    public ResponseEntity<?> login(MemberRequestDto member, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = member.toAuthentication();
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
            Member memberInfo = userSQL.getUserInfo2(member.getUserId());
            tokenDto.setMember(memberInfo);

            String userId = member.getUserId();
            userSQL.insertUserLoginHistory(userId);

            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BackendApi.getErrorMessage(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            Message.INVALID_USER,
                            ErrorCode.INVALID_USER,
                            "로그인 에러!"
                    ));
        }
    }

    @Operation(method = "GET",
            summary = "ID 중복 체크",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(value = "middle-check",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getUserMiddleCheck(@RequestParam Map<String, Object> map) {
        List<Map<String, Object>> resultList = null;
        try {
            resultList = userSQL.userMiddleCheck(map);
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
            summary = "회원가입",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @CrossOrigin(origins = "*", methods = RequestMethod.PUT)
    @PutMapping(value = "new-info",
            produces = {"application/json"}
    )
    public ResponseEntity<?> putUserInfo(@RequestParam Map<String, Object> map) {
        int result = 0;
        try {
            String pwd = String.valueOf(map.get("userPwd"));
            map.put("encryptPwd",passwordEncoder.encode(pwd));
            result = userSQL.insertUserInfo(map);
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

      @Operation(method = "PATCH",
            summary = "회원정보수정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @CrossOrigin(origins = "*", methods = RequestMethod.PATCH)
    @PatchMapping(value = "modify-info",
            produces = {"application/json"}
    )
    public ResponseEntity<?> patchUserInfo(@RequestParam Map<String, Object> map) {
        int result = 0;
        try {
            result = userSQL.updateUserInfo(map);
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

    @Operation(method = "PATCH",
            summary = "비밀번호수정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @CrossOrigin(origins = "*", methods = RequestMethod.PATCH)
    @PatchMapping(value = "pass-modify-info",
            produces = {"application/json"}
    )
    public ResponseEntity<?> patchPassWordInfo(@RequestParam Map<String, Object> map) {
        int result = 0;
        try {
            String userPwd = String.valueOf(map.get("userPwd"));
            map.put("encryptPwd",passwordEncoder.encode(userPwd));
            result = userSQL.updatePassWordInfo(map);
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
            summary = "ID 체크",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공, 페이로드에 array[json] 데이터 반환", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ApiResponses.class)))),
                    @ApiResponse(responseCode = "500", description = "실패, 에러 메시지 참조", content = @Content(schema = @Schema(implementation = ApiErrorMessage.class)))
            }
    )
    @CrossOrigin(origins = "*", methods = RequestMethod.GET)
    @GetMapping(value = "id-check",
            produces = {"application/json"}
    )
    public ResponseEntity<?> getUserIdCheck(@RequestParam Map<String, Object> map) {

        TbUser result;
        try {
            result = userRepo.findById(String.valueOf(map.get("userId")));
        } catch (RuntimeException ex) {
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
