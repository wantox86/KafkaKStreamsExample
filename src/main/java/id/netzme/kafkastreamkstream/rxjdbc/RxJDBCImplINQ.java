package id.netzme.kafkastreamkstream.rxjdbc;

import com.github.davidmoten.rx.jdbc.Database;
import id.netzme.kafkastreamkstream.KafkaStreamKStreams;
import static id.netzme.kafkastreamkstream.util.CommonConst.*;

import java.util.List;

public class RxJDBCImplINQ {

    public List<String> getVersionSupport() throws Exception{

        Database db = Database.from(KafkaStreamKStreams.config.getPropertyString(kDATABASE_CFG_URL),
                KafkaStreamKStreams.config.getPropertyString(kDATABASE_CFG_USER),
                KafkaStreamKStreams.config.getPropertyString(kDATABASE_CFG_PASSWORD));

        List<String> names = db
                .select("select minimal_version_supported from version_support")
                .getAs(String.class)
                .toList().toBlocking().single();
        return names;
    }
}
