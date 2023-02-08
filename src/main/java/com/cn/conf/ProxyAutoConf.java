package com.cn.conf;

import com.cn.model.ProxyProperties;
import com.cn.utils.SpringContextUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(ProxyProperties.class)
@ConditionalOnProperty(value = ProxyProperties.CONDITIONAL, havingValue = "true")
public class ProxyAutoConf implements ServletContainerInitializer {

    public ProxyProperties getProxyProperties() {
        return SpringContextUtil.getBean(ProxyProperties.class);
    }

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        if (!Boolean.TRUE.equals(getProxyProperties().getEnabled()) || CollectionUtils.isEmpty(getProxyProperties().getProxyAttributes())){
            return;
        }

        for (int i = 0; i < getProxyProperties().getProxyAttributes().size(); i++) {
            ProxyProperties.ProxyAttribute proxyAttribute = getProxyProperties().getProxyAttributes().get(i);
            ServletRegistration initServlet = servletContext.addServlet(proxyAttribute.getServletUrl(), ProxyServlet.class);
            initServlet.addMapping(proxyAttribute.getServletUrl());
            initServlet.setInitParameter(ProxyServlet.P_TARGET_URI, proxyAttribute.getTargetUrl());
            initServlet.setInitParameter(ProxyServlet.P_LOG, getProxyProperties().getShowLog().toString());
            initServlet.setInitParameter(ProxyServlet.P_PRESERVECOOKIES, Boolean.TRUE.toString());
        }
    }
}
