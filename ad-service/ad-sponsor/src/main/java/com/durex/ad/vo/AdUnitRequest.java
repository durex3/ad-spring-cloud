package com.durex.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author gelong
 * @date 2019/11/8 23:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitRequest {

    private Long planId;
    private String unitName;
    private Integer positionType;
    private Long budget;

    public boolean createValidate() {
        return planId != null
                && StringUtils.isNotEmpty(unitName)
                && positionType != null && budget != null;
    }
}
