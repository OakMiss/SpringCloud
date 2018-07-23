package com.oak.acron.filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 处理请求跨域过滤器
 *
 */
@Component
@ConfigurationProperties(prefix = "crossDomainFilter")
public class CrossDomainFilter implements Filter {
	//配置允许访问的域名
	private String origin;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE,PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, Accept-Language");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}

}