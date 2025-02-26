package com.example.spring_jpa.IdObject;

import lombok.Data;

import java.io.Serializable;
@Data
public class EventBoardPrizePK implements Serializable {
    private String prizeNum;
    private String id;
    private Integer eventTerm;
}
