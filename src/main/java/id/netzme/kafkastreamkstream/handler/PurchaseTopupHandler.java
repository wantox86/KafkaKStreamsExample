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
            PurchaseTopup purchaseTopupReply = processTopupToServer(value);
            logger.debug("        <-- Reply Server   : " + purchaseTopupReply.toString());

            /*
            BUILD MESSAGE REPLY
             */
            PurchaseTopup purchaseTopupOutgoing = processBuildMsgReply(purchaseTopupReply);
            logger.debug("     <-- Outgoing Message  : " + purchaseTopupOutgoing.toString());

            /*
            INSERT DATABASE
             */
            processInsertDatabase(value,value,purchaseTopupReply,purchaseTopupOutgoing);

            return purchaseTopupOutgoing;
        }catch (Exception e){
            logger.error("SOMETHING WRONG....!!");
            logger.error(CommonHelper.getStackTraceAsString(e));

            value.setRespCode(6);
            PurchaseTopup updated = processBuildMsgReply(value);
            logger.debug("     <-- Outgoing Message  : " + updated.toString());

            processInsertDatabase(value,value,null,updated);
            return updated;
        }
    }

    private PurchaseTopup processTopupToServer(PurchaseTopup value) throws Exception{
        PurchaseImpl purchase = new PurchaseImpl();
        return purchase.processPurchaseTopup(value);
    }

    private PurchaseTopup processBuildMsgReply(PurchaseTopup purchaseTopupReply) {
        PurchaseTopup purchaseTopupOutgoing = PurchaseTopup.builder(purchaseTopupReply).build();
        purchaseTopupOutgoing.setRespDesc(CommonHelper.getResponseDesc(purchaseTopupReply.getRespCode()));
        return purchaseTopupOutgoing;
    }

    private int processInsertDatabase(PurchaseTopup incommingMsg, PurchaseTopup reqServerMsg, PurchaseTopup repServerMsg, PurchaseTopup outgoingMsg) {
        return upd.insertPurchaseTopupLog(CommonHelper.getJsonPurchaseTopup(incommingMsg),
                CommonHelper.getJsonPurchaseTopup(reqServerMsg),
                CommonHelper.getJsonPurchaseTopup(repServerMsg),
                CommonHelper.getJsonPurchaseTopup(outgoingMsg));
    }
}
