package webapp

import org.http4k.template.ViewModel

data class ResponsePage(
  val totScore: Int,
  val scores: Map<String, Int>
) : ViewModel {
  override fun template() = "views/ResponsePage.ftl"
}
