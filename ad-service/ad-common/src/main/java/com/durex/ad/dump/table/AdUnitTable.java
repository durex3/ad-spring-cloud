package com.durex.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gelong
 * @date 2019/11/21 21:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitTable {

    private Long id;
    private Integer unitStatus;
    private Integer positionType;
    private Long planId;
}
