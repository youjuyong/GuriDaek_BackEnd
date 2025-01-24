package com.example.spring_jpa.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Schema(description = "BIT 상태")
@Data
@Entity
@Table(name = "tb_bit_stts")
public class BitStatus implements Serializable {

	@Id
	@Schema(description = "BIT 아이디")
	@Column(name = "bit_id", nullable = false)
	private Integer id;

	@Schema(description = "통신 상태 코드")
	@Column(name = "cmnc_stts_cd", nullable=false)
	private String cmncStts;

	@Schema(description = "카메라 상태 코드")
	@Column(name = "cmra_stts_cd", nullable = false)
	private String cmraStts;

	@Schema(description = "제어 보드 상태 코드")
	@Column(name = "ctrl_bord_stts_cd", nullable = false)
	private String ctrlStts;

	@Schema(description = "장비 습도")
	@Column(name="devc_hmdt", nullable = false)
	private int devcHmdt;

	@Schema(description = "장비 온도")
	@Column(name="devc_tmpr",  nullable = false)
	private int devcTmpr;

	@Schema(description = "장보 조도")
	@Column(name="devc_brgh", nullable = false)
	private int  devcBrgh;

	@Schema(description = "표출 상태 코드")
	@Column(name="dspl_stts_cd", nullable = false)
	private String  dsplSttsCd;

	@Schema(description = "표출 모드 상태 코드")
	@Column(name="dspl_modl_stts_cd", nullable = false)
	private String dsplModlSttsCd;

	@Schema(description = "문 상태 코드")
	@Column(name="door_stts_cd", nullable = false)
	private String doorSttsCd;

	@Schema(description = "팬 상태 코드")
	@Column(name="fan_stts_cd" , nullable = false)
	private String fanSttsCd;

	@Schema(description = "히터 상태 코드")
	@Column(name="hetr_stts_cd", nullable = false)
	private String hetrSttsCd;

	@Schema(description = "외부조도")
	@Column(name="otsd_brgh", nullable = false)
	private int otsdBrgh;

	@Schema(description = "시스템 ID")
	@Column(name="syst_id", nullable = false)
	private int systId;

	@Schema(description = "수정 일시")
	@Column(name = "chk_dt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date chkDate;
}
