package py.com.global.spm.GUISERVICE.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter implements Filter {

    @Value("Authorization")
    private String tokenHeader;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Expose-Headers", tokenHeader+", Content-Disposition, CaptchaEncode ");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, CaptchaEncode, " + tokenHeader);
        chain.doFilter(req, res);
    }
    @Override
    public void init(FilterConfig filterConfig) {
        // Do nothing
    }
    @Override
    public void destroy() {
        // Do nothing
    }
}
