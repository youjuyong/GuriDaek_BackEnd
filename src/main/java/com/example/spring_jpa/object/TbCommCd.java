package com.example.spring_jpa.object;

import com.example.spring_jpa.IdObject.TbCommCdPK;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Schema(description = "tb_comm_cd")
@Getter
@Setter
@Builder
@NoArgsConstructor
@Entity(name = "\"TB_COMM_CD\"")
@Table(name = "\"TB_COMM_CD\"")
@IdClass(TbCommCdPK.class)
public class TbCommCd implements Serializable {

    @Id
    @Schema(description = "공통 분류 코드")
    @Column(name = "comm_cd", nullable = false)
    private String commCd;

    @Id
    @Schema(description = "공통 코드")
    @Column(name = "comm_clsf_cd", nullable = false)
    private String id;

    @Schema(description = "공통 코드 한글명칭")
    @Column(name = "comm_cd_kor_name")
    private String commCdKorName;

    @Schema(description = "공통 코드 영문명칭")
    @Column(name = "comm_cd_eng_name")
    private String commCdEngName;

    @Schema(description = "기타")
    @Column(name = "rmrk")
    private String rmrk;

    @Schema(description = "사용 여부 (Y/N)")
    @Column(name = "use_yn", columnDefinition = "char(1)")
    private String useYn;

    @Schema(description = "등록 ID")
    @Column(name = "reg_id")
    private String regId;

    @Schema(description = "등록 날짜")
    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Schema(description = "업데이트 ID")
    @Column(name = "upd_id")
    private String updId;

    @Schema(description = "수정 날짜")
    @Column(name = "upd_dt")
    private LocalDateTime updDt;

    public TbCommCd(String comm_clsf_cd, String comm_cd, String comm_cd_kor_name, String comm_cd_eng_name, String rmrk, String useYn, String reg_id, LocalDateTime reg_dt, String upd_id, LocalDateTime upd_Dt ) {
        this.id = comm_clsf_cd;
        this.commCd = comm_cd;
        this.commCdKorName = comm_cd_kor_name;
        this.commCdEngName = comm_cd_eng_name;
        this.rmrk = rmrk;
        this.useYn = useYn;
        this.regId = reg_id;
        this.regDt = reg_dt;
        this.updId = upd_id;
        this.updDt = upd_Dt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommCd() {
        return commCd;
    }

    public void setCommCd(String commCd) {
        this.commCd = commCd;
    }

    public String getCommCdKorName() {
        return commCdKorName;
    }

    public void setCommCdKorName(String commCdKorName) {
        this.commCdKorName = commCdKorName;
    }

    public String getCommCdEngName() {
        return commCdEngName;
    }

    public void setCommCdEngName(String commCdEngName) {
        this.commCdEngName = commCdEngName;
    }

    public String getRmrkText() {
        return rmrk;
    }

    public void setRmrkText(String rmrkText) {
        this.rmrk = rmrkText;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public LocalDateTime getRegDt() {
        return regDt;
    }

    public void setRegDt(LocalDateTime regDt) {
        this.regDt = regDt;
    }

    public String getUpdId() {
        return updId;
    }

    public void setUpdId(String updId) {
        this.updId = updId;
    }

    public LocalDateTime getUpdDt() {
        return updDt;
    }

    public void setUpdDt(LocalDateTime updDt) {
        this.updDt = updDt;
    }

    @Override
    public String toString() {
        return "TbCommCd{" +
                "id='" + id + '\'' +
                ", commCd='" + commCd + '\'' +
                ", commCdKorName='" + commCdKorName + '\'' +
                ", commCdEngName='" + commCdEngName + '\'' +
                ", rmrk='" + rmrk + '\'' +
                ", useYn='" + useYn + '\'' +
                ", regId='" + regId + '\'' +
                ", regDt=" + regDt +
                ", updId='" + updId + '\'' +
                ", updDt=" + updDt +
                '}';
    }
}
