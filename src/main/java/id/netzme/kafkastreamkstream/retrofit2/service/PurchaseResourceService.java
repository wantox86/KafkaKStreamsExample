package id.netzme.kafkastreamkstream.retrofit2.service;

import id.netzme.kafkastreamkstream.model.PurchaseTopup;
import retrofit2.Call;
import retrofit2.http.*;

import static id.netzme.kafkastreamkstream.util.CommonConst.kRETROFIT_URL_PURCHASE_TOPUP_PATH_DUMMY;

public interface PurchaseResourceService {
    @POST(kRETROFIT_URL_PURCHASE_TOPUP_PATH_DUMMY)
    Call<PurchaseTopup> purchaseTopup(@Body PurchaseTopup purchaseTopup);
}
