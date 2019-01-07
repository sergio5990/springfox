package springfox.documentation.swagger.common

import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import spock.lang.Ignore
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest


@Ignore
class XForwardPrefixPathAdjusterSpec extends Specification {

  def "should prefix path with x-forwarded-prefix"() {
    given:
    def request = Mock(ServerHttpRequest.class)
    def headers = new HttpHeaders()
    headers.add("/basePath", "/prefix")
    request.getHeaders()>> headers

    when:
    def result = new XForwardPrefixPathAdjuster(request).adjustedPath("/basePath")

    then:
    result == "/prefix"
  }
}
