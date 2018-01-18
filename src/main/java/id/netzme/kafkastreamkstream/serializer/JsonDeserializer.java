package id.netzme.kafkastreamkstream.serializer;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JsonDeserializer<T> implements Deserializer<T> {
    private Gson gson = new Gson();
    private Class<T> deserializedClass;

    public JsonDeserializer(Class<T> deserializedClass) {
        this.deserializedClass = deserializedClass;
    }

    public JsonDeserializer() {
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        if(deserializedClass == null) {
            deserializedClass = (Class<T>) configs.get("serializedClass");
        }
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if(data == null){
            return null;
        }

        return gson.fromJson(new String(data),deserializedClass);
    }

    @Override
    public void close() {

    }
}