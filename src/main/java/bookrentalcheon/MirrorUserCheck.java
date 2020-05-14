package bookrentalcheon;

import bookrentalcheon.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class MirrorUserCheck {

    @Autowired
    private ReservationMirrorRepository reservationMirrorRepository;

    public void UserCheckAndRequest(String userid, int point) {
        reservationMirrorRepository.findByuserid(userid)
                .ifPresent(
                        reservationMirror -> {
                            System.out.println("##### Point Request #####");

                            Alarmed alarmed = new Alarmed();
                            alarmed.setUserid(userid);
                            alarmed.setPoint(point);

                            ObjectMapper objectMapper = new ObjectMapper();
                            String json = null;

                            try {
                                json = objectMapper.writeValueAsString(alarmed);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException("JSON format exception", e);
                            }

                            KafkaProcessor processor = Application.applicationContext.getBean(KafkaProcessor.class);
                            MessageChannel outputChannel = processor.outboundTopic();

                            outputChannel.send(MessageBuilder
                                    .withPayload(json)
                                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                                    .build());
                        }
                );
        ;
    }
}