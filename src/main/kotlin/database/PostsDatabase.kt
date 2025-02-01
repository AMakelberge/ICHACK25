package database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class PostsDatabase {

  private val url = "jdbc:sqlite:./src/main/resources/database/posts.db"
  private val connection = connectTo(url)

  fun createTable() {
    val query = connection.createStatement()
    query.executeUpdate("CREATE TABLE posts (Id integer primary key, Title varchar(255), Body varchar(255))")
    println("Created table")
  }

  fun insertSomeData() {
    addPost(Post(title = "Ten ways to attract more clicks...", body = "First of all, write some decent content..."))
    addPost(Post(title = "Which programming language should you learn next?", body = "Is it Kotlin? Is it Rust?..."))
    addPost(Post(title = "Top ten gifts for Christmas", body = "Number one, a new book on Kotlin..."))
    println("Inserted three records")
  }

  fun addPost(post: Post) {
    val query = connection.createStatement()
    query.executeUpdate("INSERT INTO posts (title, body) VALUES ('${post.title}', '${post.body}');")
    println("Inserted... ${post.title}")
  }

  fun loadAllPosts(): List<Post> {
    val query = connection.prepareStatement("SELECT * FROM posts")
    return query.executeQuery().asListOfPosts()
  }

  fun loadAllRows(): List<List<String>> {
    val query = connection.prepareStatement("SELECT * FROM posts")
    return query.executeQuery().asRows()
  }

  fun removePost(post: Post) {
    val query = connection.createStatement()
    query.executeUpdate("DELETE FROM posts WHERE Id = '${post.id}'")
    println("Removed... ${post.title}")
  }

  fun getHeaders(): List<String> {
    val query = connection.prepareStatement("SELECT * FROM PRAGMA_TABLE_INFO('posts')")
    return query.executeQuery().asListOfStrings()
  }

  private fun ResultSet.asRows(): List<List<String>> {
    val rows = mutableListOf<List<String>>()
    val headers = getHeaders()

    while (next()) {
      rows.add(headers.map { getString(it) })
    }
    return rows
  }

  private fun ResultSet.asListOfStrings(): List<String> {
    val strings = mutableListOf<String>()

    while (next()) {
      strings.add(getString("name"))
    }
    return strings
  }

  private fun ResultSet.asListOfPosts(): List<Post> {
    val posts = mutableListOf<Post>()

    while (next()) {
      posts.add(Post(getInt("id"), getString("title"), getString("body")))
    }
    return posts
  }

  private fun connectTo(url: String): Connection = DriverManager.getConnection(url)
}

fun main() {
  val postsDatabase = PostsDatabase()

  // Initialise the database
  // postsDatabase.createTable()
  // postsDatabase.insertSomeData()

  // Query some data
  val posts = postsDatabase.loadAllPosts()
  posts.forEach(::println)
  println(postsDatabase.getHeaders())
}
