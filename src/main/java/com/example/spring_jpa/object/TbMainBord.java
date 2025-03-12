package com.example.spring_jpa.object;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Schema(description = "tb_main_bord")
@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity(name = "\"TB_MAIN_BORD\"")
@Table(name = "\"TB_MAIN_BORD\"")
public class TbMainBord implements Serializable {

    @Id
    @Schema(description = "공지 ID")
    @Column(name = "text_id", nullable = false)
    private String id;

    @Schema(description = "공지 제목")
    @Column(name = "text_titl")
    private String textTitl;

    @Schema(description = "조회수")
    @Column(name = "rtrv_cnt")
    private Integer rtrvCnt;

    @Schema(description = "등록 날짜")
    @Column(name = "reg_dt", nullable = false)
    private LocalDateTime regDt;

    @Schema(description = "수정 날짜")
    @Column(name = "upd_dt", nullable = false)
    private LocalDateTime updDt;

    @Schema(description = "사용 여부 (Y/N)")
    @Column(name = "use_yn", columnDefinition = "varchar(1)")
    private String useYn;

    @Schema(description = "중요 여부 (Y/N)")
    @Column(name = "impt_yn", columnDefinition = "char(1)")
    private String imptYn;

    @Schema(description = "팝업 표출 여부 (Y/N)")
    @Column(name = "pop_dspl_yn", columnDefinition = "char(1)")
    private String popDsplYn;

    @Schema(description = "팝업 시작 일시")
    @Column(name = "pop_strt_dt")
    private LocalDateTime popStrtDt;

    @Schema(description = "팝업 종료 일시")
    @Column(name = "pop_end_dt")
    private LocalDateTime popEndDt;

    @Schema(description = "작성자")
    @Column(name = "wrtr")
    private String wrtr;

    @Schema(description = "비밀번호")
    @Column(name = "pwd")
    private String pwd;

    @Schema(description = "이메일")
    @Column(name = "emal")
    private String emal;

    public TbMainBord(String id, String textTitl, Integer rtrvCnt, LocalDateTime regDt, LocalDateTime updDt, String useYn, String imptYn, String popDsplYn, LocalDateTime popStrtDt, LocalDateTime popEndDt, String wrtr, String pwd, String emal) {
        this.id = id;
        this.textTitl = textTitl;
        this.rtrvCnt = rtrvCnt;
        this.regDt = regDt;
        this.updDt = updDt;
        this.useYn = useYn;
        this.imptYn = imptYn;
        this.popDsplYn = popDsplYn;
        this.popStrtDt = popStrtDt;
        this.popEndDt = popEndDt;
        this.wrtr = wrtr;
        this.pwd = pwd;
        this.emal = emal;
    }

    @Override
    public String toString() {
        return "TbMainBord{" +
                "id='" + id + '\'' +
                ", textTitl='" + textTitl + '\'' +
                ", rtrvCnt=" + rtrvCnt +
                ", regDt=" + regDt +
                ", updDt=" + updDt +
                ", useYn='" + useYn + '\'' +
                ", imptYn='" + imptYn + '\'' +
                ", popDsplYn='" + popDsplYn + '\'' +
                ", popStrtDt=" + popStrtDt +
                ", popEndDt=" + popEndDt +
                ", wrtr='" + wrtr + '\'' +
                ", pwd='" + pwd + '\'' +
                ", emal='" + emal + '\'' +
                '}';
    }

    public void addViewCount() {
         this.rtrvCnt++;
    }
}
