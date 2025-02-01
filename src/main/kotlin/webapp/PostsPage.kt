package webapp

import org.http4k.template.ViewModel

data class PostsPage(val headers: List<String>, val rows: List<List<String>>) : ViewModel {
  override fun template() = "views/PostsPage.ftl"
}
