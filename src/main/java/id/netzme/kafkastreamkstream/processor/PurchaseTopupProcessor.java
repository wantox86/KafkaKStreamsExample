package id.netzme.kafkastreamkstream.processor;

import id.netzme.kafkastreamkstream.model.PurchaseTopup;
import id.netzme.kafkastreamkstream.retrofit2.impl.PurchaseImpl;
import id.netzme.kafkastreamkstream.rxjdbc.RxJDBCImplUPD;
import id.netzme.kafkastreamkstream.util.CommonHelper;
import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.log4j.Logger;

public class PurchaseTopupProcessor extends AbstractProcessor<String, PurchaseTopup> {
    final static Logger logger = Logger.getLogger(PurchaseTopupProcessor.class);
    RxJDBCImplUPD upd = new RxJDBCImplUPD();
    @Override
    public void process(String key, PurchaseTopup value) {
        try {
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
            PurchaseTopup.Builder builder = PurchaseTopup.builder(value);
            builder.respCode(purchaseTopupReply.getRespCode());
            builder.respDesc(CommonHelper.getResponseDesc(purchaseTopupReply.getRespCode()));
            builder.rrn(purchaseTopupReply.getRrn());
            builder.refNumber(purchaseTopupReply.getRefNumber());
            PurchaseTopup updated = builder.build();
            logger.debug("     <-- Outgoing Message  : " + updated.toString());

            /*
            INSERT DATABASE
             */
            upd.insertPurchaseTopupLog(CommonHelper.getJsonPurchaseTopup(value),
                    CommonHelper.getJsonPurchaseTopup(value),
                    CommonHelper.getJsonPurchaseTopup(purchaseTopupReply),
                    CommonHelper.getJsonPurchaseTopup(updated));

            context().forward(key,updated);
            context().commit();
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

            context().forward(key,updated);
            context().commit();
        }
    }
}
