package com.durex.ad.search;

import com.durex.ad.search.vo.SearchRequest;
import com.durex.ad.search.vo.SearchResponse;

/**
 * @author gelong
 * @date 2020/1/13 20:56
 */
public interface ISearch {

    /**
     * 检索
     * @param searchRequest 检索请求
     * @return SearchResponse 检索响应
     */
    SearchResponse fetchAds(SearchRequest searchRequest);
}
