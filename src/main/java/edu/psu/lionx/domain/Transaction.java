package edu.psu.lionx.domain;


import org.stellar.sdk.Network;
import org.stellar.sdk.responses.operations.OperationResponse;
import org.stellar.sdk.xdr.OperationType;

public class Transaction {

    private String assetName;

    private String amount;

    private Long ledger;

    private String date;

    private String memo;

    private String sourceAccount;

    private String destAccount;

    private String transactionHash;

    private Boolean wasSuccess;

    private String operationType;

    private Integer operationCount;

    private Network network;

    private OperationResponse response;

    public Transaction(String sourceAccount, String memo, Integer operationCount, Network network){
        this.sourceAccount = sourceAccount;
        this.memo = memo;
        this.operationCount = operationCount;
        this.network = network;
    }

    public Transaction(String assetName, String amount, String date, String memo,
                       String sourceAccount, String destAccount, String transactionHash,
                       Boolean wasSuccess, OperationResponse response, String operationType) {
        this.assetName = assetName;
        this.amount = amount;
        this.date = date;
        this.memo = memo;
        this.sourceAccount = sourceAccount;
        this.destAccount = destAccount;
        this.wasSuccess = wasSuccess;
        this.response = response;
        this.transactionHash = transactionHash;
        this.operationType = operationType;
    }

    public Transaction(String assetName, String amount, String date, String memo,
                       String sourceAccount, String transactionHash, Boolean wasSuccess,
                       String operationType, OperationResponse opResponse) {
        this.assetName = assetName;
        this.amount = amount;
        this.date = date;
        this.memo = memo;
        this.sourceAccount = sourceAccount;
        this.transactionHash = transactionHash;
        this.wasSuccess = wasSuccess;
        this.operationType = operationType;
        this.response = opResponse;
    }

    public Boolean getWasSuccess() {
        return wasSuccess;
    }

    public void setWasSuccess(Boolean wasSuccess) {
        this.wasSuccess = wasSuccess;
    }

    public Integer getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(Integer operationCount) {
        this.operationCount = operationCount;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public OperationResponse getResponse() {
        return response;
    }

    public void setResponse(OperationResponse response) {
        this.response = response;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Long getLedger() {
        return ledger;
    }

    public void setLedger(Long ledger) {
        this.ledger = ledger;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestAccount() {
        return destAccount;
    }

    public void setDestAccount(String destAccount) {
        this.destAccount = destAccount;
    }

    public Boolean isSuccess() {
        return wasSuccess;
    }

    public void setSuccess(Boolean wasSuccess) {
        this.wasSuccess = wasSuccess;
    }

    public static class RecentTx {
        private String amount;

        public RecentTx( String operationType, String amount ) {
            switch (operationType.toUpperCase()) {
                case "SENT":
                    this.amount = "-" + amount;
                    break;
                case "RECEIVED":
                    this.amount = "+" + amount;
                    break;
                case "ACCOUNT CREATED":
                    this.amount = operationType;
                    break;
                default:
                    break;
            }
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
