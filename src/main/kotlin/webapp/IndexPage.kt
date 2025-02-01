package webapp

import org.http4k.template.ViewModel

data class IndexPage(val name: String) : ViewModel {
  override fun template() = "views/IndexPage.ftl"
}
