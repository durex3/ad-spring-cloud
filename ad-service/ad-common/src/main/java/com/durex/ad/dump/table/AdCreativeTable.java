package com.durex.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gelong
 * @date 2019/11/21 21:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdCreativeTable {

    private Long id;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer width;
    private Integer height;
    private Integer auditStatus;
    private String url;
}
