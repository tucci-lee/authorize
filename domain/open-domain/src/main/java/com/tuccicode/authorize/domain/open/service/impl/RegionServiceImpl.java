package com.tuccicode.authorize.domain.open.service.impl;

import com.tuccicode.authorize.domain.open.constant.OpenCacheConst;
import com.tuccicode.authorize.domain.open.convertor.RegionConvertor;
import com.tuccicode.authorize.domain.open.db.RegionDO;
import com.tuccicode.authorize.domain.open.entity.region.Region;
import com.tuccicode.authorize.domain.open.mapper.RegionMapper;
import com.tuccicode.authorize.domain.open.service.RegionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionMapper regionMapper;

    public RegionServiceImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Cacheable(value = OpenCacheConst.REGION, key = "#p0")
    @Override
    public List<Region> listByParentId(String parentId) {
        List<RegionDO> regions = regionMapper.selectByParentId(parentId);
        return regions.stream()
                .map(RegionConvertor::toEntity)
                .collect(Collectors.toList());
    }
}
