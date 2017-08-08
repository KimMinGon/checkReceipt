package checkreceipt

import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class AppleApiService {
    def resultService

    def checkReceipt(def receipt, boolean sandbox = false) {

        def resp
        def json

        String verifyHost = "buy.itunes.apple.com/verifyReceipt"

        if(!sandbox) {
            verifyHost = "sandbox.itunes.apple.com/verifyReceipt"
        }

        def http = new HTTPBuilder(verifyHost)

        http.request(Method.POST, ContentType.JSON) {
            uri.path = "/verifyReceipt"
            body = ['receipt-data': receipt]

            // response handler for a success response code
            response.success = { res, resultJSON ->
                println "response status: ${res.statusLine}"

                resp = res
                json = resultJSON

                println 'Response data: -----'
                println resultJSON
                println '--------------------'
            }

            if(json.status != 0) {
                resultService.error()
            }
        }

    }
}
