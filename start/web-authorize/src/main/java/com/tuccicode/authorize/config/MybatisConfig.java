package com.tuccicode.authorize.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.tuccicode.raccoon.id.IdGenerator;
import com.tuccicode.raccoon.id.SnowFlake;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tucci.lee
 */
@Configuration
@MapperScan("com.tuccicode.authorize.**.mapper")
public class MybatisConfig {

    /**
     * 生成主键
     *
     * @return IdentifierGenerator
     */
    @Bean
    public IdGenerator snowFlake() {
        return new SnowFlake();
    }

    /**
     * mybatis plus自定义主键生成
     *
     * @param idGenerator
     * @return IdentifierGenerator
     */
    @Bean
    public IdentifierGenerator identifierGenerator(IdGenerator idGenerator) {
        return entity -> idGenerator.next();
    }

    /**
     * 分页插件
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 插入，修改自动对相应的字段填充
     *
     * @return MetaObjectHandler
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", Long.class, System.currentTimeMillis());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updatedTime", Long.class, System.currentTimeMillis()); // 起始版本 3.3.0(推荐)
            }
        };
    }
}
