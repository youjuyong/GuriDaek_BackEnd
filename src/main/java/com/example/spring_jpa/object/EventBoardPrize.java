package com.example.spring_jpa.object;

import com.example.spring_jpa.IdObject.EventBoardPrizePK;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Schema(description = "bord_prize")
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@Entity(name = "\"TT_EVENT_PRIZE\"")
@Table(name = "\"TT_EVENT_PRIZE\"")
@IdClass(EventBoardPrizePK.class)
public class EventBoardPrize implements Serializable {

    @Id
    @Schema(description = "경품 ID")
    @Column(name = "prize_num", nullable = false)
    private String prizeNum;

    @Id
    @Schema(description = "공지 ID")
    @Column(name = "text_id", nullable = false)
    private String id;

    @Id
    @Schema(description = "이벤트 차수")
    @Column(name = "event_term", nullable = false)
    private Integer eventTerm;

    @Schema(description = "경품 이름")
    @Column(name = "prize_name")
    private String prizeName;

    @Schema(description = "유저 ID")
    @Column(name = "user_id")
    private String userId;

    public EventBoardPrize(String prizeNum, String id, Integer eventTerm, String prizeName, String userId) {
        this.prizeNum = prizeNum;
        this.id = id;
        this.eventTerm = eventTerm;
        this.prizeName = prizeName;
        this.userId = userId;
    }

    public String getTextId() {
        return id;
    }

    public void setTextId(String textId) {
        this.id = textId;
    }

    public String getPrizeNum() {
        return prizeNum;
    }

    public void setPrizeNum(String prizeNum) {
        this.prizeNum = prizeNum;
    }

    public Integer getEventTerm() {
        return eventTerm;
    }

    public void setEventTerm(Integer eventTerm) {
        this.eventTerm = eventTerm;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "EventBoardPrize{" +
                "prizeNum='" + prizeNum + '\'' +
                ", id='" + id + '\'' +
                ", eventTerm=" + eventTerm +
                ", prizeName='" + prizeName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
