package com.tuccicode.authorize.domain.open.external;

import java.io.InputStream;

/**
 * 文件存储服务
 *
 * @author tucci.lee
 */
public interface FsService {

    /**
     * 获取文件存储路径
     *
     * @return 文件存储路径
     */
    String getDomain();

    /**
     * 上传文件
     *
     * @param key key
     * @param is  流
     */
    void upload(String key, InputStream is);

    /**
     * 删除文件
     *
     * @param key key
     */
    void delete(String key);
}
