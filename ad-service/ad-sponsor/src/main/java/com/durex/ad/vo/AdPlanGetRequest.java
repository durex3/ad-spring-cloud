package com.durex.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author gelong
 * @date 2019/11/8 22:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanGetRequest {

    private Long userId;
    private List<Long> ids;

    public boolean validate() {
        return  userId != null && CollectionUtils.isNotEmpty(ids);
    }
}
