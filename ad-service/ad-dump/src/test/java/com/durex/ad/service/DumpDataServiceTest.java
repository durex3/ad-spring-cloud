package com.durex.ad.service;

import com.durex.ad.Application;
import com.durex.ad.dump.DumpConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

/**
 * @author gelong
 * @date 2019/11/22 0:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataServiceTest {

    @Resource
    private DumpDataService dumpDataService;

    @Test
    public void dumpAdTableData() {
        dumpDataService.dumpAdPlanTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_PLAN));
        dumpDataService.dumpAdUnitTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT));
        dumpDataService.dumpAdCreativeTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_CREATIVE));
        dumpDataService.dumpAdCreativeUnitTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_CREATIVE_UNIT));
        dumpDataService.dumpAdUnitKeywordTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT_KEYWORD));
        dumpDataService.dumpAdUnitItTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT_IT));
        dumpDataService.dumpAdUnitDistrictTable(String.format("%s%s", DumpConstant.DATA_ROOT_DIR, DumpConstant.AD_UNIT_DISTRICT));

    }

}
