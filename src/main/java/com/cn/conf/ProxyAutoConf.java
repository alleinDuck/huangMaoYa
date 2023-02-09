package com.cn.conf;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyAutoConf  {
    @Value("${proxy.showLog}")
    private String showLog;

    @Value("${proxy.servletUrl}")
    private String servletUrl;

    @Value("${proxy.targetUrl}")
    private String targetUrl;

    @Bean
    public ServletRegistrationBean proxyServletRegistration(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new ProxyServlet(), servletUrl);
        servletRegistrationBean.addInitParameter(ProxyServlet.P_TARGET_URI, targetUrl);
        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, showLog);

        return servletRegistrationBean;
    }
}
