package id.netzme.kafkastreamkstream.handler;

import id.netzme.kafkastreamkstream.model.PurchaseTopup;
import id.netzme.kafkastreamkstream.retrofit2.impl.PurchaseImpl;
import id.netzme.kafkastreamkstream.rxjdbc.RxJDBCImplUPD;
import id.netzme.kafkastreamkstream.util.CommonHelper;
import org.apache.log4j.Logger;

public class PurchaseTopupHandler {
    final static Logger logger = Logger.getLogger(PurchaseTopupHandler.class);
    RxJDBCImplUPD upd = new RxJDBCImplUPD();
    public PurchaseTopup processHandler(PurchaseTopup value){
        try{
            logger.debug("RRN : " + value.getRrn());
            logger.debug("     --> incomming Message : " + value.toString());

            /*
            REQUEST TOPUP TO SERVER
             */
            logger.debug("        --> Request Server : " + value.toString());
            PurchaseImpl purchase = new PurchaseImpl();
            PurchaseTopup purchaseTopupReply = purchase.processPurchaseTopup(value);
            logger.debug("        <-- Reply Server   : " + purchaseTopupReply.toString());

            /*
            BUILD MESSAGE REPLY
             */
            PurchaseTopup purchaseTopupOutgoing = PurchaseTopup.builder(purchaseTopupReply).build();
            purchaseTopupOutgoing.setRespDesc(CommonHelper.getResponseDesc(purchaseTopupReply.getRespCode()));
            logger.debug("     <-- Outgoing Message  : " + purchaseTopupOutgoing.toString());

            /*
            INSERT DATABASE
             */
            upd.insertPurchaseTopupLog(CommonHelper.getJsonPurchaseTopup(value),
                    CommonHelper.getJsonPurchaseTopup(value),
                    CommonHelper.getJsonPurchaseTopup(purchaseTopupReply),
                    CommonHelper.getJsonPurchaseTopup(purchaseTopupOutgoing));
            return purchaseTopupOutgoing;
        }catch (Exception e){
            System.out.println("SOMETHING WRONG....!!");
            System.out.println(CommonHelper.getStackTraceAsString(e));

            PurchaseTopup.Builder builder = PurchaseTopup.builder(value);
            builder.respCode(6);
            builder.respDesc(CommonHelper.getResponseDesc(6));
            PurchaseTopup updated = builder.build();
            logger.debug("     <-- Outgoing Message  : " + updated.toString());

            upd.insertPurchaseTopupLog(CommonHelper.getJsonPurchaseTopup(value),
                    CommonHelper.getJsonPurchaseTopup(value),
                    null,
                    CommonHelper.getJsonPurchaseTopup(updated));
            return updated;
        }


    }
}
