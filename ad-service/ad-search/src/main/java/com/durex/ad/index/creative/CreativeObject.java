package com.durex.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author gelong
 * @date 2019/11/19 1:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeObject {

    private Long creativeId;
    private String name;
    private Integer type;
    private Integer materialType;
    private Integer width;
    private Integer height;
    private Integer auditStatus;
    private String url;

    public void update(CreativeObject newObject) {
        if (newObject.getCreativeId() != null) {
            this.creativeId = newObject.getCreativeId();
        }
        if (StringUtils.isNotEmpty(newObject.getName())) {
            this.name = newObject.getName();
        }
        if (newObject.getType() != null) {
            this.type = newObject.getType();
        }
        if (newObject.getMaterialType() != null) {
            this.materialType = newObject.getMaterialType();
        }
        if (newObject.getWidth() != null) {
            this.width = newObject.getWidth();
        }
        if (newObject.getHeight() != null) {
            this.height = newObject.getHeight();
        }
        if (newObject.getAuditStatus() != null) {
            this.auditStatus = newObject.getAuditStatus();
        }
        if (StringUtils.isNotEmpty(url)) {
            this.url = newObject.getUrl();
        }
    }
}
