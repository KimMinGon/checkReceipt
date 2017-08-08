package checkreceipt

class CheckReceiptController {
    def resultService
    def googleApiService
    def appleApiService

    def checkReceipt() {
        def requestJSON = resultService.getRequestJSON(request)

        try {
            googleApiService.checkReceipt(requestJSON.packageName, requestJSON.productId, requestJSON.token)
        } catch (e) {
            e.printStackTrace()
            render resultService.error()
            return
        }

        render resultService.success()

    }
}
