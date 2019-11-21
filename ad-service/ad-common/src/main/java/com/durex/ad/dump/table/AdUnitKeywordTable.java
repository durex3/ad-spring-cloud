package com.durex.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gelong
 * @date 2019/11/21 21:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitKeywordTable {

    private Long unitId;
    private String keyword;
}
