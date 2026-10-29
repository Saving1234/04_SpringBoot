package bear.section02;

import bear.section02.common.EmailNotificationClient;
import bear.section02.common.NotificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
@Configuration
@ComponentScan("bear.section02") // 경로 넣어주기
public class Contextconfiguration {

    @Bean
    public NotificationClient notificationClient(){

        return  new EmailNotificationClient(
                "apiUrl",
                "api-key",
                3
        );
    }


}
