package pre.cg.ftp;

import io.swagger.models.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
 //       docket.host("129.204.145.82:8181");
        docket.host("localhost:8181");
     //  docket.host("39.96.68.53:8181");
        docket.apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pre.cg.ftp.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo apiInfo(){
        Contact contact=new Contact();
        contact.setName("CG");
        contact.setUrl("http://baidu.com");
        contact.setEmail("1634337925@qq.com");
        return new ApiInfoBuilder()
                .title("CG API")
                .description("CG API CG's zone,CG's rule")
                .version("2.0")
                .contact(contact.toString())
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();

    }
}
