package com.durex.ad.service;

import com.alibaba.fastjson.JSON;
import com.durex.ad.constant.CommonStatus;
import com.durex.ad.dao.AdPlanRepository;
import com.durex.ad.dao.AdUnitRepository;
import com.durex.ad.dao.CreativeRepository;
import com.durex.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.durex.ad.dao.unit_condition.AdUnitItRepository;
import com.durex.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.durex.ad.dao.unit_condition.CreativeUnitRepository;
import com.durex.ad.dump.table.*;
import com.durex.ad.entity.AdPlan;
import com.durex.ad.entity.AdUnit;
import com.durex.ad.entity.Creative;
import com.durex.ad.entity.unit_condition.AdUnitDistrict;
import com.durex.ad.entity.unit_condition.AdUnitIt;
import com.durex.ad.entity.unit_condition.AdUnitKeyword;
import com.durex.ad.entity.unit_condition.CreativeUnit;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author gelong
 * @date 2019/11/21 22:20
 */
@Slf4j
@Service
public class DumpDataService {

    @Resource
    private AdPlanRepository adPlanRepository;
    @Resource
    private AdUnitRepository adUnitRepository;
    @Resource
    private CreativeRepository creativeRepository;
    @Resource
    private CreativeUnitRepository creativeUnitRepository;
    @Resource
    private AdUnitKeywordRepository adUnitKeywordRepository;
    @Resource
    private AdUnitItRepository adUnitItRepository;
    @Resource
    private AdUnitDistrictRepository adUnitDistrictRepository;

    public void dumpAdPlanTable(String filename) {
        List<AdPlan> adPlans = adPlanRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }
        List<AdPlanTable> adPlanTables = Lists.newArrayList();
        adPlans.forEach(adPlan -> adPlanTables.add(new AdPlanTable(
                adPlan.getId(),
                adPlan.getUserId(),
                adPlan.getPlanStatus(),
                adPlan.getStartDate(),
                adPlan.getEndDate()
        )));
        Path path = Paths.get(filename);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = Files.newBufferedWriter(path);
            for (AdPlanTable adPlanTable : adPlanTables) {
                bufferedWriter.write(JSON.toJSONString(adPlanTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("dump AdPlanTable error");
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                log.error("dump AdPlanTable bufferedWriter close error");
            }
        }
    }

    public void dumpAdUnitTable(String filename) {
        List<AdUnit> adUnits = adUnitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }
        List<AdUnitTable> adUnitTables = Lists.newArrayList();
        adUnits.forEach(adUnit -> adUnitTables.add(new AdUnitTable(
                adUnit.getId(),
                adUnit.getUnitStatus(),
                adUnit.getPositionType(),
                adUnit.getPlanId()
        )));
        Path path = Paths.get(filename);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = Files.newBufferedWriter(path);
            for (AdUnitTable adUnitTable : adUnitTables) {
                bufferedWriter.write(JSON.toJSONString(adUnitTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("dump AdUnitTable error");
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                log.error("dump AdUnitTable bufferedWriter close error");
            }
        }
    }

    public void dumpAdCreativeTable(String filename) {
        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }
        List<AdCreativeTable> adCreativeTables = Lists.newArrayList();
        creatives.forEach(creative -> adCreativeTables.add(new AdCreativeTable(
                creative.getId(),
                creative.getName(),
                creative.getType(),
                creative.getMaterialType(),
                creative.getWidth(),
                creative.getHeight(),
                creative.getAuditStatus(),
                creative.getUrl()
        )));
        Path path = Paths.get(filename);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = Files.newBufferedWriter(path);
            for (AdCreativeTable adCreativeTable : adCreativeTables) {
                bufferedWriter.write(JSON.toJSONString(adCreativeTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("dump AdCreativeTable error");
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                log.error("dump AdCreativeTable bufferedWriter close error");
            }
        }
    }

    public void dumpAdCreativeUnitTable(String filename) {
        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }
        List<AdCreativeUnitTable> adCreativeUnitTables = Lists.newArrayList();
        creativeUnits.forEach(creativeUnit -> adCreativeUnitTables.add(new AdCreativeUnitTable(
                creativeUnit.getCreativeId(),
                creativeUnit.getUnitId()
        )));
        Path path = Paths.get(filename);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = Files.newBufferedWriter(path);
            for (AdCreativeUnitTable adCreativeUnitTable : adCreativeUnitTables) {
                bufferedWriter.write(JSON.toJSONString(adCreativeUnitTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("dump AdCreativeUnitTable error");
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                log.error("dump AdCreativeUnitTable bufferedWriter close error");
            }
        }
    }

    public void dumpAdUnitKeywordTable(String filename) {
        List<AdUnitKeyword> adUnitKeywords = adUnitKeywordRepository.findAll();
        if (CollectionUtils.isEmpty(adUnitKeywords)) {
            return;
        }
        List<AdUnitKeywordTable> adUnitKeywordTables = Lists.newArrayList();
        adUnitKeywords.forEach(adUnitKeyword -> adUnitKeywordTables.add(new AdUnitKeywordTable(
                adUnitKeyword.getUnitId(),
                adUnitKeyword.getKeyword()
        )));
        Path path = Paths.get(filename);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = Files.newBufferedWriter(path);
            for (AdUnitKeywordTable adUnitKeywordTable : adUnitKeywordTables) {
                bufferedWriter.write(JSON.toJSONString(adUnitKeywordTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("dump AdUnitKeywordTable error");
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                log.error("dump AdUnitKeywordTable bufferedWriter close error");
            }
        }
    }

    public void dumpAdUnitItTable(String filename) {
        List<AdUnitIt> adUnitIts = adUnitItRepository.findAll();
        if (CollectionUtils.isEmpty(adUnitIts)) {
            return;
        }
        List<AdUnitItTable> adUnitItTables = Lists.newArrayList();
        adUnitIts.forEach(adUnitIt -> adUnitItTables.add(new AdUnitItTable(
                adUnitIt.getUnitId(),
                adUnitIt.getItTag()
        )));
        Path path = Paths.get(filename);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = Files.newBufferedWriter(path);
            for (AdUnitItTable adUnitItTable : adUnitItTables) {
                bufferedWriter.write(JSON.toJSONString(adUnitItTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("dump AdUnitItTable error");
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                log.error("dump AdUnitItTable bufferedWriter close error");
            }
        }
    }

    public void dumpAdUnitDistrictTable(String filename) {
        List<AdUnitDistrict> adUnitDistricts = adUnitDistrictRepository.findAll();
        if (CollectionUtils.isEmpty(adUnitDistricts)) {
            return;
        }
        List<AdUnitDistrictTable> adUnitDistrictTables = Lists.newArrayList();
        adUnitDistricts.forEach(adUnitDistrict -> adUnitDistrictTables.add(new AdUnitDistrictTable(
                adUnitDistrict.getUnitId(),
                adUnitDistrict.getProvince(),
                adUnitDistrict.getCity()
        )));
        Path path = Paths.get(filename);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = Files.newBufferedWriter(path);
            for (AdUnitDistrictTable adUnitDistrictTable : adUnitDistrictTables) {
                bufferedWriter.write(JSON.toJSONString(adUnitDistrictTable));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("dump AdUnitDistrictTable error");
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                log.error("dump AdUnitDistrictTable bufferedWriter close error");
            }
        }
    }
}
