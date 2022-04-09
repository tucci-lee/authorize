package com.tuccicode.authorize.domain.open.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
@TableName("region")
public class RegionDO {
    @TableId(type = IdType.NONE)
    private String regionId;

    private String parentId;

    private String name;

    private String enName;
}
