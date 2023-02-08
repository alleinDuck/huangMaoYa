package com.cn.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * The type Proxy properties.
 */
@Data
@ConfigurationProperties(prefix = ProxyProperties.PREFIX, ignoreUnknownFields = false)
public class ProxyProperties {
    /**
     * The constant PREFIX.
     */
    public static final String PREFIX = "prefix";
    /**
     * The constant CONDITIONAL.
     */
    public static final String CONDITIONAL = PREFIX + "." + "enabled";

    // 是否开启
    private Boolean enabled = false;

    // 是否打印日志
    private Boolean showLog = false;

    // 代理属性
    private List<ProxyAttribute> proxyAttributes;

    /**
     * The type Proxy attribute.
     */
    @Data
    public static class ProxyAttribute{

        // 路由路径
        private String servletUrl;

        // 实际转发的目的地
        private String targetUrl;

    }
}
