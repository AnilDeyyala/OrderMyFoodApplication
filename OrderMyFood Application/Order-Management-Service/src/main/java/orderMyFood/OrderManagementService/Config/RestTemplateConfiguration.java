package orderMyFood.OrderManagementService.Config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

	@Bean
	@LoadBalanced
	public RestTemplate RestTemplateBuilder() {
		return new RestTemplate();
	}
}
