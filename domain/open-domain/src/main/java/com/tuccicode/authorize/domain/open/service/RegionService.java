package com.tuccicode.authorize.domain.open.service;

import com.tuccicode.authorize.domain.open.entity.region.Region;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface RegionService {

    /**
     * 根据parentId查询地区信息
     *
     * @param parentId 上级id，第一级传0
     * @return 地区信息
     */
    List<Region> listByParentId(String parentId);
}
