package com.tuccicode.authorize.domain.user.db;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
@TableName("signin_log")
public class SigninLogDO {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private String os;

    private String browser;

    private String ip;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    private Boolean status;

    private String message;
}
