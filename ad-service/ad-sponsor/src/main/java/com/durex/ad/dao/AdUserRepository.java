package com.durex.ad.dao;

import com.durex.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author gelong
 * @date 2019/11/6 0:12
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return AdUser
     */
    AdUser findByUsername(String username);
}
