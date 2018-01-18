package id.netzme.kafkastreamkstream.retrofit2.impl;

import id.netzme.kafkastreamkstream.KafkaStreamKStreams;
import id.netzme.kafkastreamkstream.model.PurchaseTopup;
import id.netzme.kafkastreamkstream.retrofit2.service.PurchaseResourceService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

import static id.netzme.kafkastreamkstream.util.CommonConst.*;

public class PurchaseImpl {

    public PurchaseTopup processPurchaseTopup(PurchaseTopup purchaseTopup) throws Exception{
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(KafkaStreamKStreams.config.getPropertyInt(kRETROFIT_CFG_CONNECTION_TIMEOUT), TimeUnit.SECONDS)
                .readTimeout(KafkaStreamKStreams.config.getPropertyInt(kRETROFIT_CFG_READ_TIMEOUT), TimeUnit.SECONDS)
                .writeTimeout(KafkaStreamKStreams.config.getPropertyInt(kRETROFIT_CFG_WRITE_TIMEOUT), TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(KafkaStreamKStreams.config.getPropertyString(kRETROFIT_CFG_URL_PURCHASE_TOPUP_DUMMY))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();
        PurchaseResourceService service = retrofit.create(PurchaseResourceService.class);

        return service.purchaseTopup(purchaseTopup).execute().body();
    }
}
