package com.example.spring_jpa.object;

import com.example.spring_jpa.api.v1.repository.BitSttsRepo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Schema(description = "BIT")
@Data
@Entity
@Table(name = "tb_bit")
public class Bit implements Serializable {
	
	@Id
	@Schema(description = "BIT 아이디")
	@Column(name = "bit_id", nullable = false)
	private Integer id;
	
	@Schema(description = "BIT 그룹 아이디")
	@Column(name = "bit_grop_id", nullable = false)
	private Integer groupId;
	
	@Schema(description = "BIT 프라이머리 유형 코드")
	@Column(name = "bit_prmr_type_cd", nullable = false, columnDefinition = "char(4)")
	private String primaryTypeCode;

	@Schema(description = "선호 시스템 아이디")
	@Column(name = "prfr_syst_id", nullable = false)
	private String prfrSystId;

	@Schema(description = "활성화 여부")
	@Column(name = "enbl_yn", nullable = false, columnDefinition = "char(1)")
	private String enableYn;
	
	@Schema(description = "모듈 유형 코드")
	@Column(name = "mdle_type_cd", nullable = false, columnDefinition = "char(4)")
	private String moduleTypeCode;
	
	@Schema(description = "아이피 주소")
	@Column(name = "ip_addr", nullable = false)
	private String ipAddress;
	
	@Schema(description = "X 좌표")
	@Column(name = "x_crdn", nullable = false, columnDefinition = "number(11, 8)")
	private Float longitude;
	
	@Schema(description = "Y 좌표")
	@Column(name = "y_crdn", nullable = false, columnDefinition = "number(10, 8)")
	private Float latitude;
	
	@Schema(description = "제조사 아이디")
	@Column(name = "mnfc_cmpy_id", nullable = false)
	private Integer manufacturerId;
	
	@Schema(description = "사업 아이디")
	@Column(name = "bsns_id", nullable = false)
	private Integer businessId;

	@Schema(description = "설치 유형 코드")
	@Column(name = "istl_type_cd")
	private String istlTypeCd;

	@Schema(description = "자가 네트워크 유무")
	@Column(name = "self_ntwk_yn", columnDefinition = "char(1)")
	private String selfNtwkYn;

	@Schema(description = "설치 위치명")
	@Column(name = "istl_loc_name")
	private String locationName;

	@Schema(description = "설치 구 이름")
	@Column(name = "istl_goo_name")
	private String istlGooName;

	@Schema(description = "설치 동 위치명")
	@Column(name = "istl_dong_name")
	private String istlDongName;

	@Schema(description = "설치 일시")
	@Column(name = "istl_dt")
	private String istlDt;

	@Schema(description = "비고")
	@Column(name = "rmrk")
	private String remark;
	
	@Schema(description = "삭제 여부 (Y/N)")
	@Column(name = "del_yn", nullable = false, columnDefinition = "char(1)")
	private String deleted;
	
	@Schema(description = "등록 일시")
	@Column(name = "reg_dt", nullable = false)
	private Date registeredDate;
	
	@Schema(description = "수정 일시")
	@Column(name = "upd_dt")
	private Date updatedDate;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "bit_id")
	private BitStatus bitSttsInfo;

//	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
//	@JoinColumn(name = "bit_id")
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
//	private BusstopMapping busstopMapping;

}
