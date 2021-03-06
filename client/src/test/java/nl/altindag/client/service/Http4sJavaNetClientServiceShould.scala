package nl.altindag.client.service

import nl.altindag.client.TestConstants
import nl.altindag.client.util.{MockServerTestHelper, SSLFactoryTestHelper}
import org.assertj.core.api.Assertions.assertThat
import org.mockito.scalatest.MockitoSugar
import org.scalatest.funspec.AnyFunSpec

class Http4sJavaNetClientServiceShould extends AnyFunSpec with MockitoSugar {

  describe("execute request") {
    val mockServerTestHelper = new MockServerTestHelper("http4s java net client")

    val client = new JavaNetClientConfiguration().createJavaNetClient(null)
    val victim = new Http4sJavaNetClientService(client)

    val clientResponse = victim.executeRequest(TestConstants.HTTP_URL)

    assertThat(clientResponse.getStatusCode).isEqualTo(200)
    assertThat(clientResponse.getResponseBody).isEqualTo("Hello")

    mockServerTestHelper.stop();
  }

  describe("create java net client with ssl material") {
    val sslFactory = SSLFactoryTestHelper.createSSLFactory(true, true)
    val client = new JavaNetClientConfiguration().createJavaNetClient(sslFactory)

    assertThat(client).isNotNull
    verify(sslFactory, times(1)).getSslContext
    verify(sslFactory, times(1)).getHostnameVerifier
  }

  describe("java net io app run function should do nothing") {
    val any = new JavaNetClientConfiguration().run(null)
    assertThat(any).isNull()
  }

}
