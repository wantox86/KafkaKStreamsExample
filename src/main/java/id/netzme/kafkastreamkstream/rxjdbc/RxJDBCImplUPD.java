package id.netzme.kafkastreamkstream.rxjdbc;

import com.github.davidmoten.rx.jdbc.Database;
import id.netzme.kafkastreamkstream.KafkaStreamKStreams;
import id.netzme.kafkastreamkstream.util.CommonHelper;
import org.apache.log4j.Logger;

import static id.netzme.kafkastreamkstream.util.CommonConst.*;

public class RxJDBCImplUPD {
    final static Logger logger = Logger.getLogger(RxJDBCImplUPD.class);
    public int insertPurchaseTopupLog(String incommingMsg, String reqServer, String repServer, String outgoingServer){
        int ret = -1;
        try{
            Database db = Database.from(KafkaStreamKStreams.config.getPropertyString(kDATABASE_CFG_URL),
                    KafkaStreamKStreams.config.getPropertyString(kDATABASE_CFG_USER),
                    KafkaStreamKStreams.config.getPropertyString(kDATABASE_CFG_PASSWORD));

            ret = db
                    .update("insert into purchase_topup_log(incomming_message,server_request_message,server_reply_message,outgoing_message) " +
                            "values(cast(? as json),cast(? as json),cast(? as json),cast(? as json))")
                    .parameters(incommingMsg, reqServer, repServer, outgoingServer)
                    .execute();
        }catch (Exception e){
            logger.error(CommonHelper.getStackTraceAsString(e));
            logger.error("Fail save to database");
        }

        return ret;
    }
}
