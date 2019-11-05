package com.durex.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gelong
 * @date 2019/11/6 0:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {

    private Long id;
    private String username;
    private String token;
    private Date createTime;
    private Date updateTime;
}
