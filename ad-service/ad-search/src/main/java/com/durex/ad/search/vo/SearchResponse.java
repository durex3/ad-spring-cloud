package com.durex.ad.search.vo;

import com.durex.ad.index.creative.CreativeObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author gelong
 * @date 2020/1/13 22:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    public Map<String, List<Creative>> adSlot2Ads = Maps.newHashMap();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {

        private Long creativeId;
        private String url;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        /**
         * 展示监测 url
         */
        private List<String> showMonitorUrl = Lists.newArrayList("www.baidu.com", "www.baidu.com");

        /**
         * 点击监测 url
         */
        private List<String> clickMonitorUrl = Lists.newArrayList("www.baidu.com", "www.baidu.com");
    }

    public static Creative convert(CreativeObject object) {

        Creative creative = new Creative();
        creative.setCreativeId(object.getCreativeId());
        creative.setUrl(object.getUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());
        return creative;
    }

}
