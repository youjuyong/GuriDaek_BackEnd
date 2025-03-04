package com.example.spring_jpa.object;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Schema(description = "tb_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity(name = "\"TB_USER\"")
@Table(name = "\"TB_USER\"")
public class TbUser implements Serializable {

    @Id
    @GeneratedValue
    @Schema(description = "유저 ID")
    @Column(name = "user_id", nullable = false)
    private String id;

    @Schema(description = "비밀번호")
    @Column(name = "user_pwd")
    private String userPwd;

    @Schema(description = "유저 이름")
    @Column(name = "user_name")
    private String username;

    @Schema(description = "유저 전화번호")
    @Column(name = "user_tel")
    private String userTel;

    @Schema(description = "유저 모바일")
    @Column(name = "user_mobile")
    private String userMobile;

    @Schema(description = "유저 주소")
    @Column(name = "user_addr")
    private String userAddr;

    @Schema(description = "삭제 여부 (Y/N)")
    @Column(name = "del_yn", columnDefinition = "varchar(1)")
    private String delYn;

    @Schema(description = "의정부 주민 여부 (Y/N)")
    @Column(name = "city_yn", columnDefinition = "char(1)")
    private String cityYn;

    @Schema(description = "권한 여부 (Y/N)")
    @Column(name = "master_yn", columnDefinition = "char(1)")
    private String masterYn;

    @Schema(description = "힌트 질문")
    @Column(name = "hint_ques")
    private String hintQues;

    @Schema(description = "힌트 대답")
    @Column(name = "hint_ans")
    private String hintAns;

    @Schema(description = "힌트 대답")
    @Column(name = "city_user_name")
    private String cityUserName;

    @Schema(description = "수정 일시")
    @Column(name = "upd_dt")
    private LocalDateTime updDt;

    @Schema(description = "등록 날짜")
    @Column(name = "reg_dt", nullable = false)
    private LocalDateTime regDt;

    public TbUser(String id, String userPwd, String username, String userTel, String userMobile, String userAddr, String delYn, String cityYn, String masterYn, String hintQues, String hintAns, String cityUserName, LocalDateTime updDt, LocalDateTime regDt) {
        this.id = id;
        this.userPwd = userPwd;
        this.username = username;
        this.userTel = userTel;
        this.userMobile = userMobile;
        this.userAddr = userAddr;
        this.delYn = delYn;
        this.cityYn = cityYn;
        this.masterYn = masterYn;
        this.hintQues = hintQues;
        this.hintAns = hintAns;
        this.cityUserName = cityUserName;
        this.updDt = updDt;
        this.regDt = regDt;
    }

    @Override
    public String toString() {
        return "TbUser{" +
                "id='" + id + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", username='" + username + '\'' +
                ", userTel='" + userTel + '\'' +
                ", userMobild='" + userMobile + '\'' +
                ", userAddr='" + userAddr + '\'' +
                ", delYn='" + delYn + '\'' +
                ", cityYn='" + cityYn + '\'' +
                ", masterYn='" + masterYn + '\'' +
                ", hintQues='" + hintQues + '\'' +
                ", hintAns='" + hintAns + '\'' +
                ", cityUserName='" + cityUserName + '\'' +
                ", updDt=" + updDt +
                ", regDt=" + regDt +
                '}';
    }
}
