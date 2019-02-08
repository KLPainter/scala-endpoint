package server

import java.util
import java.util.concurrent.{CompletableFuture, Executor}

import com.nike.riposte.server.Server
import com.nike.riposte.server.config.ServerConfig
import com.nike.riposte.server.http.{Endpoint, RequestInfo, ResponseInfo, StandardEndpoint}
import com.nike.riposte.client.asynchttp.ning.AsyncHttpClientHelper
import com.nike.riposte.client.asynchttp.ning.RequestBuilderWrapper
import com.nike.riposte.server.http.impl.FullResponseInfo
import com.nike.riposte.util.Matcher
import com.ning.http.client.Response
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.HttpMethod

object Main extends App {
  val server = new Server(AppServerConfig)
  server.startup()
}

object AppServerConfig extends ServerConfig {
  val endpoints: java.util.Set[Endpoint[_]] = new util.HashSet[Endpoint[_]]()
  endpoints.add(HelloWorldEndpoint)
  endpoints.add(ApiEndpoint)

  override def appEndpoints(): java.util.Collection[Endpoint[_]] = endpoints

}

object HelloWorldEndpoint extends StandardEndpoint[Void, String] {
  override def requestMatcher(): Matcher = Matcher.`match`("/hello")

  override def execute(
                        request: RequestInfo[Void],
                        longRunningTaskExecutor: Executor,
                        ctx: ChannelHandlerContext): CompletableFuture[ResponseInfo[String]] =
  {
    CompletableFuture.completedFuture(
      ResponseInfo.newBuilder("Hello, Karen!")
        .withDesiredContentWriterMimeType("text/plain")
        .build()
    )
  }
}

object ApiEndpoint extends StandardEndpoint[Void, String] {
  override def requestMatcher(): Matcher = Matcher.`match`("/apiEndpoint")
  val asyncHttpClientHelper = new AsyncHttpClientHelper

  override def execute(
                        request: RequestInfo[Void],
                        longRunningTaskExecutor: Executor,
                        ctx: ChannelHandlerContext): CompletableFuture[ResponseInfo[String]] = {

    def buildRequest(downstreamUrl: String, method: HttpMethod): RequestBuilderWrapper = {
      asyncHttpClientHelper.getRequestBuilder(downstreamUrl, method)
    }
    val reqWrapper = buildRequest("https://ron-swanson-quotes.herokuapp.com/v2/quotes", HttpMethod.GET)

    asyncHttpClientHelper.executeAsyncHttpRequest(reqWrapper, (asyncResponse: Response) => {
      def handleResponse(asyncResponse: Response) :FullResponseInfo[String] = {
        ResponseInfo.newBuilder(asyncResponse.getResponseBody).build
      }
      handleResponse(asyncResponse)
    }, ctx)
  }


  // timer stuff
  //  logger.info("About to make async library call")
  //  val startTime = new ExampleDownstreamHttpAsyncEndpoint.ObjectHolder[Long]
  //  startTime.heldObject = System.currentTimeMillis
  //logger.info("In async response handler. Total time spent millis: {}", System.currentTimeMillis - startTime.heldObject)

  // additional buildRequest stuff
  //    reqWrapper.requestBuilder.addQueryParam("some_query_param", "foo").addHeader("Accept", "application/json")
  //    if (request.getRawContentLengthInBytes > 0) reqWrapper.requestBuilder.setBody(request.getRawContent)

}