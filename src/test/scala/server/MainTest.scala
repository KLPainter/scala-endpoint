package server

import java.util.concurrent.CompletableFuture
import com.nike.riposte.server.http.ResponseInfo
import org.scalatest.AsyncFlatSpec

class MainTest extends AsyncFlatSpec {

  "HelloWorldEndpoint GET" should "return a greeting" in {
    val responseFuture: CompletableFuture[ResponseInfo[String]] = HelloWorldEndpoint.execute(null, null, null)
    val responseInfo: ResponseInfo[String] = responseFuture.get()
    assert(responseInfo.getContentForFullResponse == "Hello, Karen!")
  }
}