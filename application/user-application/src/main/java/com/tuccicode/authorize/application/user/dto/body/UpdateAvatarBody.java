package com.tuccicode.authorize.application.user.dto.body;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class UpdateAvatarBody {

    @NotNull
    private MultipartFile file;
}
