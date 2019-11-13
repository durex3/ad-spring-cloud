package com.durex.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gelong
 * @date 2019/11/10 14:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitKeywordResponse {

    private List<Long> ids;
}
