package id.netzme.kafkastreamkstream.util;

public class CommonConst {
    /*
    STREAM GROUP
     */
    final public static String kSTREAM_CLIENT_ID_CONFIG = "Example-Processor-Job";
    final public static String kSTREAM_GROUP_ID =  "test-consumer-group";
    final public static String kSTREAM_APPLICATION_ID_CONFIG =  "testing-processor-api";

    final public static String kSTREAM_CFG_TOPIC_SOURCE = "stream.topic.source";
    final public static String kSTREAM_CFG_TOPIC_DESTINATION = "stream.topic.destination";
    final public static String kSTREAM_CFG_BOOTSTRAP_SERVERS_CONFIG = "192.168.10.101:9092";
    final public static String kSTREAM_CFG_ZOOKEEPER_CONNECT_CONFIG = "192.168.10.101:2181";


    /*
    DATABASE GROUP
     */
    final public static String kDATABASE_CFG_URL = "database.url";
    final public static String kDATABASE_CFG_USER = "database.user";
    final public static String kDATABASE_CFG_PASSWORD = "database.pwd";

    /*
    RETROFIT2 GROUP
     */
    final public static String kRETROFIT_URL_PURCHASE_TOPUP_PATH_DUMMY = "v2/5a5c5ebd2e0000aa039f8263";
    final public static String kRETROFIT_CFG_URL_PURCHASE_TOPUP_DUMMY = "http.server.url";
    final public static String kRETROFIT_CFG_URL_PURCHASE_TOPUP_PATH_DUMMY = "http.server.url.path";
    final public static String kRETROFIT_CFG_CONNECTION_TIMEOUT = "http.server.connection.timeout";
    final public static String kRETROFIT_CFG_READ_TIMEOUT = "http.server.read.timeout";
    final public static String kRETROFIT_CFG_WRITE_TIMEOUT = "http.server.write.timeout";
}
