package nl.altindag.client.service

import nl.altindag.client.TestConstants
import nl.altindag.client.util.{MockServerTestHelper, SSLFactoryTestHelper}
import org.assertj.core.api.Assertions.assertThat
import org.mockito.scalatest.MockitoSugar
import org.scalatest.funspec.AnyFunSpec

class Http4sBlazeClientServiceShould extends AnyFunSpec with MockitoSugar {

  describe("execute request") {
    val mockServerTestHelper = new MockServerTestHelper("http4s blaze client")

    val client = new BlazeClientConfiguration().createBlazeClient(null)
    val victim = new Http4sBlazeClientService(client)

    val clientResponse = victim.executeRequest(TestConstants.HTTP_URL)

    assertThat(clientResponse.getStatusCode).isEqualTo(200)
    assertThat(clientResponse.getResponseBody).isEqualTo("Hello")

    mockServerTestHelper.stop();
  }

  describe("create blaze client with ssl material") {
    val sslFactory = SSLFactoryTestHelper.createSSLFactory(true, true)
    val client = new BlazeClientConfiguration().createBlazeClient(sslFactory)

    assertThat(client).isNotNull
    verify(sslFactory, times(1)).getSslContext
  }

  describe("blaze io app run function should do nothing") {
    val any = new BlazeClientConfiguration().run(null)
    assertThat(any).isNull()
  }

}
