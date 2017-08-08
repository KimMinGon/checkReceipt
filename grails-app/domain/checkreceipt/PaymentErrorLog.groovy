package checkreceipt

class PaymentErrorLog {

    String googlePackageName
    String productId
    String token

    static constraints = {
        googlePackageName nullable: true
        productId nullable: true
        token nullable: true
    }

    static mapping = {
        version false
    }
}
