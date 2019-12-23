package com.durex.ad.index;

import com.alibaba.fastjson.JSON;
import com.durex.ad.dump.DumpConstant;
import com.durex.ad.dump.table.*;
import com.durex.ad.handler.AdLevelDataHandler;
import com.durex.ad.mysql.constant.OpType;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gelong
 * @date 2019/12/4 19:50
 */
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    @PostConstruct
    private void init() {
        // 全量索引加载推广单元
        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_PLAN)
        );
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(JSON.parseObject(p, AdPlanTable.class), OpType.ADD));

        // 全量索引加载创意
        List<String> adCreativeStrings = loadDumpData(
                String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_CREATIVE)
        );
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handleLevel2(JSON.parseObject(c, AdCreativeTable.class), OpType.ADD));

        // 全量索引加载推广单元
        List<String> adUnitStrings = loadDumpData(
                String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT)
        );
        adUnitStrings.forEach(u -> AdLevelDataHandler.handleLevel3(JSON.parseObject(u, AdUnitTable.class), OpType.ADD));

        // 全量索引加载创意-推广单元
        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_CREATIVE_UNIT)
        );
        adCreativeUnitStrings.forEach(cu -> AdLevelDataHandler.handleLevel3(JSON.parseObject(cu, AdCreativeUnitTable.class), OpType.ADD));

        // 全量索引加载推广单元-地域
        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(ud -> AdLevelDataHandler.handleLevel4(JSON.parseObject(ud, AdUnitDistrictTable.class), OpType.ADD));

        // 全量索引加载推广单元-兴趣
        List<String> adUnitItStrings = loadDumpData(
                String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT_IT)
        );
        adUnitItStrings.forEach(ui -> AdLevelDataHandler.handleLevel4(JSON.parseObject(ui, AdUnitItTable.class), OpType.ADD));

        // 全量索引加载推广单元-关键词
        List<String> adUnitKeywordStrings = loadDumpData(
                String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT_KEYWORD)
        );
        adUnitKeywordStrings.forEach(uk -> AdLevelDataHandler.handleLevel4(JSON.parseObject(uk, AdUnitKeywordTable.class),OpType.ADD));
    }

    /**
     * 读取数据文件
     * @param filename 文件名
     * @return List<String>
     */
    private List<String> loadDumpData(String filename) {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filename));
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
