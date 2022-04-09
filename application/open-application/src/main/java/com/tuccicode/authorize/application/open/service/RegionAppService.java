package com.tuccicode.authorize.application.open.service;

import com.tuccicode.authorize.application.open.dto.query.RegionQuery;
import com.tuccicode.authorize.domain.open.service.RegionService;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.dto.SingletonResponse;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class RegionAppService {

    private final RegionService regionService;

    public RegionAppService(RegionService regionService) {
        this.regionService = regionService;
    }

    public Response list(RegionQuery query) {
        return SingletonResponse.success(regionService.listByParentId(query.getParentId()));
    }
}
