package bookrentalcheon;

import bookrentalcheon.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationMirrorViewHandler {


    @Autowired
    private ReservationMirrorRepository reservationMirrorRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserved_then_CREATE_1 (@Payload Reserved reserved) {
        try {
            if (reserved.isMe()) {
                ReservationMirror reservationMirror = new ReservationMirror();
                reservationMirror.setUserid(reserved.getUserid());
                reservationMirrorRepository.save(reservationMirror);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}