package checkreceipt

class PaymentSuccessLog {
    String googlePackageName
    String productId
    String token

    Integer consumptionState
    String developerPayload
    String kind
    Integer purchaseState
    Integer purchaseTimeMillis


    static constraints = {
        googlePackageName nullable: true
        productId nullable: true
        token nullable: true
        consumptionState nullable: true
        developerPayload nullable: true
        kind nullable: true
        purchaseState nullable: true
        purchaseTimeMillis nullable: true
    }
    
    static mapping = {
        version false
    }
}
