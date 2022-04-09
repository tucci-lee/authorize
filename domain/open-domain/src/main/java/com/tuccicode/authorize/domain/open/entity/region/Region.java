package com.tuccicode.authorize.domain.open.entity.region;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class Region extends DTO {

    private String regionId;

    private String parentId;

    private String name;

    private String enName;

}
