package database

data class Post(val id: Int = 0, val title: String, val body: String) {
  override fun toString(): String = "Post[title='$title', body='$body']"
}
