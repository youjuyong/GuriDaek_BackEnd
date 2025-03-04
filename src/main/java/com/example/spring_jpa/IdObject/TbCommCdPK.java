package com.example.spring_jpa.IdObject;

import lombok.Data;

import java.io.Serializable;

@Data
public class TbCommCdPK implements Serializable {
    private String commCd;
    private String id;
}
