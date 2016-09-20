package views.example

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.test.mixin.integration.Integration
import grails.transaction.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import spock.lang.*

@Integration
@Rollback
class VueRenderingSpec extends Specification {

    @Shared
    @Value('${local.server.port}')
    int serverPort

    @Shared
    def rest = new RestBuilder()

    void "test view rendering"() {
        when:
            RestResponse resp = rest.get("http://localhost:${serverPort}/vue/html")
            MediaType contentType = resp.headers.getContentType()

        then:
            resp.status == HttpStatus.OK.value()
            resp.text.contains("This content should be rendered")
            contentType.toString().startsWith("text/html")

        when:
            resp = rest.get("http://localhost:${serverPort}/vue/renderHTML")
            contentType = resp.headers.getContentType()

        then:
            resp.status == HttpStatus.OK.value()
            resp.text.contains("This content should be rendered")
            contentType.toString().startsWith("text/html")
    }
}
