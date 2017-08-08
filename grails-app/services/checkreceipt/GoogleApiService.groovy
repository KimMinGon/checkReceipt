package checkreceipt

import grails.plugin.redis.RedisService
import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Transactional
class GoogleApiService {
    RedisService redisService

    final String refreshToken = "1/q27LhtTsM15GKutKUxTAwGE9CtkVWCueoI9RqS2cb7k"
    final String clientId = "490298314880-q4jg3ndak8s6a083aefhlqhp5f0vmcu2.apps.googleusercontent.com"
    final String clientSecret = "O5WB-NByP1tsEu_NLzHvZZ4C"

    def checkReceipt(String googlePackageName, String productId, def token) {

        println googlePackageName
        println productId
        println token

        def googleApiTokens = redisService.memoizeHash("googleApiTokens", 3600) {
            def json = refeshToken()

            return [accessToken : json.access_token, tokenType : json.token_type, expireIn : json.expires_in as String]
        }

        def rest = new RestBuilder()

        def url = "https://www.googleapis.com/androidpublisher/v2/applications/${googlePackageName}/purchases/products/${productId}/tokens/${token}?access_token=${googleApiTokens.accessToken}"

        println url

        def resp = rest.get(url)

        def resultJSON = resp.json

        def status = resp.status

        if(status == 200) {
            if(resultJSON.purchaseState != 0) {
                throw new Exception()
            }
        } else {
            throw new Exception()
        }

        new PaymentSuccessLog(googlePackageName: googlePackageName,
                productId: productId,
                token: token,
                consumptionState: resultJSON.consumptionState,
                developerPayload: resultJSON.developerPayload,
                kind: resultJSON.kind,
                purchaseState: resultJSON.purchaseState,
                purchaseTimeMillis: resultJSON.purchaseTimeMillis).save(flush: true)
    }

    def refeshToken() {

        def rest = new RestBuilder()
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()
        form.add("client_id", clientId)
        form.add("client_secret", clientSecret)
        form.add("refresh_token", refreshToken)
        form.add("grant_type", "refresh_token")

        def resp = rest.post("https://www.googleapis.com/oauth2/v4/token") {
            body(form)
        }

        resp.json
    }
}
