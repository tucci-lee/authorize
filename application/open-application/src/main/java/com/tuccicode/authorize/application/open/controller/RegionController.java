package com.tuccicode.authorize.application.open.controller;

import com.tuccicode.authorize.application.open.dto.query.RegionQuery;
import com.tuccicode.authorize.application.open.service.RegionAppService;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("open/region")
public class RegionController {

    private final RegionAppService regionAppService;

    public RegionController(RegionAppService regionAppService) {
        this.regionAppService = regionAppService;
    }

    @GetMapping
    public Response list(@Valid RegionQuery query) {
        return regionAppService.list(query);
    }
}
