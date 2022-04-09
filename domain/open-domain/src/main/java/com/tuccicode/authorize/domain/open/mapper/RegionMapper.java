package com.tuccicode.authorize.domain.open.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.authorize.domain.open.db.RegionDO;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface RegionMapper extends BaseMapper<RegionDO> {

    List<RegionDO> selectByParentId(String parentId);
}
