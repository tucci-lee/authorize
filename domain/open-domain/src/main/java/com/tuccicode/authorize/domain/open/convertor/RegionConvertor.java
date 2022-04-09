package com.tuccicode.authorize.domain.open.convertor;

import com.tuccicode.authorize.domain.open.db.RegionDO;
import com.tuccicode.authorize.domain.open.entity.region.Region;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class RegionConvertor {

    public static Region toEntity(RegionDO db){
        if(db == null){
            return null;
        }
        Region entity = new Region();
        BeanUtils.copyProperties(db, entity);
        return entity;
    }
}
