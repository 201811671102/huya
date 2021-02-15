package pre.cg;

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


/**
 * Swagger2配置类
 * 在与spring boot 集成时，放在与Application同级的目录下
 * 通过@Configuration注解，让spring来加载该类配置
 * 再通过@EnableSwagger2注解来启动swagger2
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select（）函数返回一个ApiSelectorBuilder实例，用来控制哪些接口暴露给Swagger来展示
     *通过指定扫描包路径来定义指定要建立API的路径
     */

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())// 调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .select()//创建ApiSelectorBuilder对象
                .apis(RequestHandlerSelectors.basePackage("pre.cg.controller"))
                .paths(PathSelectors.any())//过滤的接口
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展示在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     */

    private ApiInfo apiInfo(){
        Contact contact=new Contact();
        contact.setName("CG");
        contact.setUrl("http://baidu.com");
        contact.setEmail("1634337925@qq.com");
        return new ApiInfoBuilder()
                .title("CG API")//大标题
                .description("CG API CG's zone,CG's rule")//详细描述
                .version("2.0")//版本
                .contact(contact.toString())//作者
                .license("The Apache License, Version 2.0")//许可证信息
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")//许可证地址
                .build();

    }
}
