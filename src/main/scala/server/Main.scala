package server

import java.util
import java.util.concurrent.{CompletableFuture, Executor}

import com.nike.riposte.server.Server
import com.nike.riposte.server.config.ServerConfig
import com.nike.riposte.server.http.{Endpoint, RequestInfo, ResponseInfo, StandardEndpoint}
import com.nike.riposte.util.Matcher
import io.netty.channel.ChannelHandlerContext

import scala.collection.immutable.HashSet

object Main extends App {
  val server = new Server(AppServerConfig)
  server.startup()
}

object AppServerConfig extends ServerConfig {
  val endpoints: java.util.Set[Endpoint[_]] = new util.HashSet[Endpoint[_]]()
  endpoints.add(HelloWorldEndpoint)
  endpoints.add(SecondEndpoint)

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

object SecondEndpoint extends StandardEndpoint[Void, String] {
  override def requestMatcher(): Matcher = Matcher.`match`("/secondEndpoint")

  override def execute(
                        request: RequestInfo[Void],
                        longRunningTaskExecutor: Executor,
                        ctx: ChannelHandlerContext): CompletableFuture[ResponseInfo[String]] =
  {
    CompletableFuture.completedFuture(
      ResponseInfo.newBuilder("Hello again, Karen!")
        .withDesiredContentWriterMimeType("text/plain")
        .build()
    )
  }
}


