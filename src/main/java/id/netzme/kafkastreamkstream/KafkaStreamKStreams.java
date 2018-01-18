package id.netzme.kafkastreamkstream;

import id.netzme.kafkastreamkstream.handler.PurchaseTopupHandler;
import id.netzme.kafkastreamkstream.model.PurchaseTopup;
import id.netzme.kafkastreamkstream.processor.PurchaseTopupProcessor;
import id.netzme.kafkastreamkstream.serializer.JsonDeserializer;
import id.netzme.kafkastreamkstream.serializer.JsonSerializer;
import id.netzme.kafkastreamkstream.util.CommonHelper;
import id.netzme.kafkastreamkstream.util.NewCompositeConfig;
import org.apache.commons.configuration.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.apache.log4j.Logger;

import static id.netzme.kafkastreamkstream.util.CommonConst.*;

import java.io.File;
import java.util.Properties;

public class KafkaStreamKStreams {
    public static NewCompositeConfig config = new NewCompositeConfig();
    final static Logger logger = Logger.getLogger(KafkaStreamKStreams.class);

    private static void init() throws ConfigurationException
    {
        /*
        INIT CONFIG
         */
        String configFileName = "config.cfg";
        File resourcesDirectory = new File("src/test/resources");
        String configPath = resourcesDirectory.getAbsolutePath() + File.separatorChar + configFileName;
        logger.info("Config PATH : " + configFileName);
        config.addConfiguration(new SystemConfiguration());
        config.addConfiguration(new PropertiesConfiguration(configPath));

        /*
        INIT STREAM
         */
        StreamsConfig streamingConfig = new StreamsConfig(getProperties());
        JsonDeserializer<PurchaseTopup> purchaseJsonDeserializer = new JsonDeserializer<>(PurchaseTopup.class);
        JsonSerializer<PurchaseTopup> purchaseJsonSerializer = new JsonSerializer<>();

        Serde<PurchaseTopup> purchaseSerde = Serdes.serdeFrom(purchaseJsonSerializer,purchaseJsonDeserializer);

        Serde<String> stringSerde = Serdes.String();

        KStreamBuilder kStreamBuilder = new KStreamBuilder();
        PurchaseTopupHandler handler = new PurchaseTopupHandler();

        KStream<String,PurchaseTopup> purchaseKStream = kStreamBuilder.stream(stringSerde,purchaseSerde,config.getPropertyString(kSTREAM_CFG_TOPIC_SOURCE))
                .mapValues(pr -> handler.processHandler(pr));

        purchaseKStream.to(stringSerde,purchaseSerde,config.getPropertyString(kSTREAM_CFG_TOPIC_DESTINATION));

        System.out.println("Starting PurchaseStreams Example");
        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder,streamingConfig);
        kafkaStreams.start();
        logger.info("Now started PurchaseTopup");
    }

    public static void main (String args[])
    {
        try {
            init();
        } catch (ConfigurationException e) {
            CommonHelper.getStackTraceAsString(e);
        }
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, kSTREAM_CLIENT_ID_CONFIG);
        props.put("group.id", kSTREAM_GROUP_ID);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, kSTREAM_APPLICATION_ID_CONFIG);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kSTREAM_CFG_BOOTSTRAP_SERVERS_CONFIG);
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, kSTREAM_CFG_ZOOKEEPER_CONNECT_CONFIG);
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return props;
    }
}


