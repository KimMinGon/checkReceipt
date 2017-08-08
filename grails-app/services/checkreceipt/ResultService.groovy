package checkreceipt

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.http.HttpServletRequest

@Transactional
class ResultService {

    def getRequestJSON(HttpServletRequest request) {
        def json = JSON.parse(new String(request.inputStream.bytes))

        println "====== request json start"
        println json
        println "====== request json end"

        json
    }

    def error() {

        def result = [result: [success: 0]]

        println "======Error ${new Date()}"
        println result
        println "======Error end"

        result as JSON
    }

    def success() {

        def result = [result: [success: 1]]

        println "======Success ${new Date()}"
        println result
        println "======Success end"

        result as JSON
    }
}
