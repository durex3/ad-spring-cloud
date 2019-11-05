package com.durex.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author gelong
 * @date 2019/11/6 0:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String username;

    public boolean validate() {
        return StringUtils.isNotEmpty(username);
    }
}
