package com.durex.ad.dao;

import com.durex.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author gelong
 * @date 2019/11/6 0:26
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {

    /**
     * 根据UserId Name查询创意是否存在
     * @param userId 用户id
     * @param name 创意名称
     * @return Creative
     */
    Creative findByUserIdAndName(Long userId, String name);
}
