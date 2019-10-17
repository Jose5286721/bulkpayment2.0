package py.com.global.spm.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@EnableJms
@SpringBootApplication(scanBasePackages={ "py.com.global.spm"})
public class SpmSpringModelApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpmSpringModelApplication.class, args);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(new RequestContextListener());
    }
}
