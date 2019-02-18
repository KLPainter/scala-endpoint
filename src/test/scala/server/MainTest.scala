package server

import java.util.concurrent.CompletableFuture
import com.nike.riposte.server.http.ResponseInfo
import org.scalatest.AsyncFlatSpec
import org.scalamock.scalatest.MockFactory
import com.nike.riposte.client.asynchttp.ning.AsyncHttpClientHelper

class MainTest extends AsyncFlatSpec {

  "HelloWorldEndpoint GET" should "return a greeting" in {
    val responseFuture: CompletableFuture[ResponseInfo[String]] = HelloWorldEndpoint.execute(null, null, null)
    val responseInfo: ResponseInfo[String] = responseFuture.get()
    assert(responseInfo.getContentForFullResponse == "Hello, Karen!")
  }

//  "ApiEndpoint GET" should "return a string" in {
//    val asyncHttpClientHelperMock = mock[AsyncHttpClientHelper]
//    (asyncHttpClientHelperMock.executeAsyncHttpRequest _).returns("A quote")
//    val responseFuture: CompletableFuture[ResponseInfo[String]] = ApiEndpoint.execute(null, null, null)
//    val responseInfo: ResponseInfo[String] = responseFuture.get()
//    assert(responseInfo.getContentForFullResponse == "A quote")
//  }

}