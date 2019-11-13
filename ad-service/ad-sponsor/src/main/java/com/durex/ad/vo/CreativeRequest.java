package com.durex.ad.vo;

import com.durex.ad.constant.CommonStatus;
import com.durex.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author gelong
 * @date 2019/11/13 23:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeRequest {

    private String name;
    private Integer type;
    private Integer materialType;
    private Integer width;
    private Integer height;
    private Long size;
    private Integer duration;
    private Long userId;
    private String url;

    public Creative convertToEntity() {
        Creative creative = new Creative();
        creative.setName(name);
        creative.setType(type);
        creative.setMaterialType(materialType);
        creative.setWidth(width);
        creative.setHeight(height);
        creative.setSize(size);
        creative.setDuration(duration);
        creative.setAuditStatus(CommonStatus.VALID.getStatus());
        creative.setUserId(userId);
        creative.setUrl(url);
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());
        return creative;
    }

    public boolean validate() {
        return StringUtils.isNotEmpty(name) && userId != null;
    }
}
