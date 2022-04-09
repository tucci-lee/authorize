package com.tuccicode.authorize.application.open.dto.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tucci.lee
 */
@Data
public class RegionQuery {

    @NotBlank
    private String parentId;
}
