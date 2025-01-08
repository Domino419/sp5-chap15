package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import controller.RegisterRequestValidator;
import interceptor.AuthCheckInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * class         : MvcConfig
 * date          : 2024-12-26
 * description   : Spring MVC 설정을 구성하는 클래스.
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {


	/**
	 * method        : getValidator
	 * date          : 2024-12-26
	 * return        : Validator
	 * description   : RegisterRequestValidator를 Validator로 등록. 스프링 MVC에서 폼 데이터 검증에 사용.
	 */
	@Override
	public Validator getValidator() {
		return new RegisterRequestValidator();
	}

	/**
	 * method        : configureDefaultServletHandling
	 * date          : 24-12-17
	 * param         : DefaultServletHandlerConfigurer configurer - 기본 서블릿 핸들링을 설정하는 객체
	 * return        : void
	 * description   : 기본 서블릿을 처리하도록 설정, 웹 애플리케이션에서 정적 자원을 처리할 수 있도록 함.
	 */
	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * method        : configureViewResolvers
	 * date          : 24-12-17
	 * param         : ViewResolverRegistry registry - 뷰 리졸버를 설정하는 객체
	 * return        : void
	 * description   : JSP 파일을 뷰로 사용하기 위한 설정. 뷰의 경로와 확장자를 지정.
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}


	/**
	 * method        : addViewControllers
	 * date          : 24-12-17
	 * param         : ViewControllerRegistry registry - 뷰 컨트롤러를 등록하는 객체
	 * return        : void
	 * description   : 요청 URL에 대한 뷰 이름을 간단히 매핑하기 위한 설정. "/main" 요청에 대해 "main" 뷰를 반환.
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/main").setViewName("main");
	}

	/**
	 * method        : addInterceptors
	 * date          : 25-01-06
	 * return        : void
	 * description   : 인터셉터를 등록하는 메서드로, "/edit/**" 경로로 들어오는 요청에 대해 `AuthCheckInterceptor`를 적용.
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authCheckInterceptor()).addPathPatterns("/edit/**");
	}

	/**
	 * method        : authCheckInterceptor
	 * date          : 25-01-06
	 * return        : AuthCheckInterceptor - 인증 체크를 위한 인터셉터 객체
	 */
	@Bean
	public AuthCheckInterceptor authCheckInterceptor() {
		return new AuthCheckInterceptor();
	}

	/**
	 * method        : messageSource
	 * date          : 24-12-26
	 * param         : None
	 * return        : MessageSource - 메시지 소스를 설정한 ResourceBundleMessageSource 객체
	 * description   : 다국어 메시지 처리를 위한 메시지 소스 설정 메서드.
	 *                 message.label 파일을 기준으로 메시지를 로드하며 UTF-8 인코딩을 사용.
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
		ms.setBasenames("message.label");  		// 메시지 파일의 기본 이름 설정
		ms.setDefaultEncoding("UTF-8");    		// 메시지 파일의 문자 인코딩 설정
		return ms;   							// MessageSource 객체 반환
	}

	/**
	 * method        : extendMessageConverters
	 * date          : 25-01-08
	 * description   : Jackson2 기반 메시지 컨버터를 설정
	 * param         : converters - HttpMessageConverter 리스트
	 * >>MappingJackson2HttpMessageConverter에서 사용할 타입을 메소드에서 지정하더라도 개별 속성에 @JsonFormat을 사용했으면 해당 설정이 우선된다.
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// Jackson2 ObjectMapper를 생성하며 타임스탬프 대신 ISO 8601 형식을 사용하도록 설정
		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
				.json() // JSON 포맷으로 ObjectMapper를 빌드
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)   //   "2024-12-19T23:56:34"
				//.simpleDateFormat("yyyyMMddHHmmss")                                 //   "20241219235634"
				.build();

		// Jackson2 ObjectMapper를 기반으로 한 메시지 컨버터를 생성하고 리스트의 가장 앞에 추가
		converters.add(0, new MappingJackson2HttpMessageConverter(objectMapper));
	}


}
