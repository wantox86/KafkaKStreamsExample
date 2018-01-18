package id.netzme.kafkastreamkstream.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import id.netzme.kafkastreamkstream.KafkaStreamKStreams;
import id.netzme.kafkastreamkstream.model.PurchaseTopup;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;

public class CommonHelper {
    public static String getResponseDesc(Integer resp){
        if(KafkaStreamKStreams.config.getPropertyString("response." + resp+ ".desc") == ""){
            return "Response not yet defined";
        }
        return KafkaStreamKStreams.config.getPropertyString("response." + resp+ ".desc");
    }

    public static String getResponseDesc(Integer resp, String defaultValue){
        if(getResponseDesc(resp) == "" || getResponseDesc(resp) == "Response not yet defined"){
            return defaultValue;
        }
        return getResponseDesc(resp);
    }

    public static String getStackTraceAsString(Throwable thr)
    {
        if (thr != null)
        {
            StringWriter swrt = new StringWriter();
            PrintWriter pwrt = new PrintWriter(swrt);
            thr.printStackTrace(pwrt);
            return swrt.toString();
        }
        else
        {
            return null;
        }
    }

    public static String getJsonPurchaseTopup(PurchaseTopup param){
        Gson gson = new Gson();
        Type type = new TypeToken<PurchaseTopup>() {}.getType();
        return gson.toJson(param,type);
    }
}
