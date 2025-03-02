package com.example.spring_jpa.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Schema(description = "User")
@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity(name = "tb_user")
@Table(name = "tb_user")
public class Member implements Serializable {

    @Id
    @Schema(description = "사용자 아이디")
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Schema(description = "사용자 암호")
    @Column(name = "user_pwd")
    private String userPwd;

    @Schema(description = "사용자 이름")
    @Column(name = "user_name")
    private String userName;

    @Schema(description = "유저 전화번호")
    @Column(name = "user_tel")
    private String userTel;

    @Schema(description = "주소")
    @Column(name = "user_addr")
    private String userAddr;

    @Schema(description = "이메일")
    @Column(name = "user_emal")
    private String userEmal;


    @Schema(description = "삭제 여부 (Y/N)")
    @Column(name = "del_yn", columnDefinition = "char(1)")
    private String deleted;

    @Schema(description = "의정부 주민 여부 (Y/N)")
    @Column(name = "city_yn", columnDefinition = "char(1)")
    private String cityYn;

    @Schema(description = "마스터 권한 여부 (Y/N)")
    @Column(name = "master_yn", columnDefinition = "char(1)")
    private String masterYn;

    @Schema(description = "힌트 질문")
    @Column(name = "hint_ques")
    private String hintQuestion;

    @Schema(description = "힌트 답")
    @Column(name = "hint_ans")
    private String hintAnswer;

    @Schema(description = "마을 주민 명")
    @Column(name = "city_user_name")
    private String cityUserName;

    @Schema(description = "수정 일시")
    @Column(name = "upd_dt")
    private LocalDateTime updatedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T' HH24:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T' HH24:mm:ss")
    @Schema(description = "등록 일시")
    @Column(name = "reg_dt")
    private LocalDateTime registeredDate;

    @Transient
    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.ROLE_ADMIN;

    @Builder
    public Member(String userId, String userPwd, String userName, String userTel, String userAddr, String userEmal, String deleted, String cityYn, String masterYn, String hintQuestion, String hintAnswer, String cityUserName, LocalDateTime updatedDate, LocalDateTime registeredDate, Authority authority) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.userName = userName;
        this.userTel = userTel;
        this.userAddr = userAddr;
        this.userEmal = userEmal;
        this.deleted = deleted;
        this.cityYn = cityYn;
        this.masterYn = masterYn;
        this.hintQuestion = hintQuestion;
        this.hintAnswer = hintAnswer;
        this.cityUserName = cityUserName;
        this.updatedDate = updatedDate;
        this.registeredDate = registeredDate;
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userId='" + userId + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userName='" + userName + '\'' +
                ", userTel='" + userTel + '\'' +
                ", userAddr='" + userAddr + '\'' +
                ", userEmal='" + userEmal + '\'' +
                ", deleted='" + deleted + '\'' +
                ", cityYn='" + cityYn + '\'' +
                ", masterYn='" + masterYn + '\'' +
                ", hintQuestion='" + hintQuestion + '\'' +
                ", hintAnswer='" + hintAnswer + '\'' +
                ", cityUserName='" + cityUserName + '\'' +
                ", updatedDate=" + updatedDate +
                ", registeredDate=" + registeredDate +
                ", authority=" + authority +
                '}';
    }

    @Builder
    public Member(String userId, String userPwd, Authority authority) {

    }
}
