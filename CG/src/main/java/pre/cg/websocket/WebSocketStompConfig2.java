package pre.cg.websocket;



import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.util.WebAppRootListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/*websocket配置*/
/*@Configuration
@Component
@EnableAutoConfiguration
public class WebSocketStompConfig2 implements ServletContextInitializer {
    @Bean
    public ServerEndpointExporter  serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    *//*配置websocket文件接受的文件最大容量*//*
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(WebAppRootListener.class);
        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","51200000");
        servletContext.setInitParameter("org.apache.tomcat.websocket.binaryBufferSize","51200000");
    }
}*/
