package com.example.spring_jpa.object;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Schema(description = "User")
@Getter
@Builder
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class Member implements Serializable {

    @Id
    @Schema(description = "사용자 아이디")
    @NotEmpty(message = "사용자 아이디를 입력하세요.")
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Schema(description = "사용자 암호")
    @NotEmpty(message = "사용자 암호를 입력하세요.")
    @Column(name = "user_pwd", nullable = false)
    private String userPwd;

    @Schema(description = "사용자 이름")
    @NotEmpty(message = "사용자 이름을 입력하세요.")
    @Column(name = "user_name")
    private String userName;

    @Schema(description = "유저 전화번호")
    @Column(name = "user_tel")
    private String userTel;

    @Schema(description = "주소")
    @Column(name = "user_addr")
    private String userAddr;

    @Schema(description = "모바일")
    @Column(name = "user_mobile")
    private String userMobile;

    @Schema(description = "이메일")
    @Column(name = "user_emal")
    private String userEmal;


    @Schema(description = "삭제 여부 (Y/N)")
    @Column(name = "del_yn", nullable = false, columnDefinition = "char(1)")
    private String deleted;

    @Schema(description = "의정부 주민 여부 (Y/N)")
    @Column(name = "city_yn", nullable = false, columnDefinition = "char(1)")
    private String cityYn;

    @Schema(description = "마스터 권한 여부 (Y/N)")
    @Column(name = "master_yn", nullable = false, columnDefinition = "char(1)")
    private String masterYn;

     @Schema(description = "힌트 질문")
    @Column(name = "hint_ques")
    private String hintQuestion;

    @Schema(description = "힌트 답")
    @Column(name = "hint_ans")
    private String hintAnswer;

    @Schema(description = "수정 일시")
    @Column(name = "upd_dt")
    private Date updatedDate;

    @Schema(description = "등록 일시")
    @Column(name = "reg_dt", nullable = false)
    private Date registeredDate;



    @Transient
    @Enumerated(EnumType.STRING)
    private Authority authority = Authority.ROLE_ADMIN;

    @Builder
    public Member(String userId, String userPwd, String userName, String userTel, String userAddr, String userMobile, String userEmal, String deleted, String cityYn, String masterYn, String hintQuestion, String hintAnswer, Date updatedDate, Date registeredDate, Authority authority) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.userName = userName;
        this.userTel = userTel;
        this.userAddr = userAddr;
        this.userMobile = userMobile;
        this.userEmal = userEmal;
        this.deleted = deleted;
        this.cityYn = cityYn;
        this.masterYn = masterYn;
        this.hintQuestion = hintQuestion;
        this.hintAnswer = hintAnswer;
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
                ", userMobile='" + userMobile + '\'' +
                ", userEmal='" + userEmal + '\'' +
                ", deleted='" + deleted + '\'' +
                ", cityYn='" + cityYn + '\'' +
                ", masterYn='" + masterYn + '\'' +
                ", hintQuestion='" + hintQuestion + '\'' +
                ", hintAnswer='" + hintAnswer + '\'' +
                ", updatedDate=" + updatedDate +
                ", registeredDate=" + registeredDate +
                ", authority=" + authority +
                '}';
    }

    @Builder
    public Member(String userId, String userPwd, Authority authority) {

    }
}
