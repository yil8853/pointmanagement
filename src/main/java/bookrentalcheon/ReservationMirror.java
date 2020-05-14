package bookrentalcheon;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ReservationMirror_table")
public class ReservationMirror {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private String userid;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }



}
