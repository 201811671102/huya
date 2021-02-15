package pre.cg.websocket;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/*websocket配置*/
@Configuration
public class WebSocketStompConfig {
    @Bean
    public ServerEndpointExporter  serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
