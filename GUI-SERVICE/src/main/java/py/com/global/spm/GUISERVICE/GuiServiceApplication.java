package py.com.global.spm.GUISERVICE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@ComponentScan(basePackages = {"py.com.global.spm.domain","py.com.global.spm.GUISERVICE"})
@EntityScan("py.com.global.spm.domain.entity")
@EnableRedisRepositories(basePackages = "py.com.global.spm.GUISERVICE.dao.redis")
@SpringBootApplication
public class GuiServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GuiServiceApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GuiServiceApplication.class);

	}
}

