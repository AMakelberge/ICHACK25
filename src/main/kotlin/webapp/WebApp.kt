package webapp

import database.Post
import database.PostsDatabase
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.body.form
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.http4k.template.FreemarkerTemplates

val app: HttpHandler = routes(

  "/index" bind GET to {
    val renderer = FreemarkerTemplates().HotReload("src/main/resources")
    val viewModel = IndexPage("World")
    Response(OK).body(renderer(viewModel))
  },

  "/assets" bind static(ResourceLoader.Directory("src/main/resources/assets")),

  "/submit" bind POST to { request ->
    val w1 = request.form("word1")?.lowercase()
    val w2 = request.form("word2")?.lowercase()
    val w3 = request.form("word3")?.lowercase()
    val w4 = request.form("word4")?.lowercase()
    val w5 = request.form("word5")?.lowercase()

    val letterMap = mapOf(
      'a' to 1, 'b' to 3, 'c' to 3,
      'd' to 2, 'e' to 1, 'f' to 4,
      'g' to 2, 'h' to 4, 'i' to 1,
      'j' to 8, 'k' to 5, 'l' to 1,
      'm' to 3, 'n' to 1, 'o' to 1,
      'p' to 3, 'q' to 10, 'r' to 1,
      's' to 1, 't' to 1, 'u' to 1,
      'v' to 4, 'w' to 4, 'x' to 8,
      'y' to 4, 'z' to 10,
    )

    val words = listOf(w1, w2, w3, w4, w5)
    val scrabScore = words.map { word -> word!!.sumOf { letterMap[it] ?: 0 } }
    // Only null values have a score of 0 so you can filter using that.
    val scores = words.zip(scrabScore).sortedByDescending { it.second }.filterNot { it.second == 0 }.toMap() as Map<String, Int>
    val tot = scores.values.sum()

    val renderer = FreemarkerTemplates().HotReload("src/main/resources")
    val viewModel = ResponsePage(tot, scores)
    Response(OK).body(renderer(viewModel))
  },

// Generalised functions on PostsDatabase so they work with any database and don't rely on posts
  "/posts" bind GET to { request ->
    val postsDatabase = PostsDatabase()
    val rows = postsDatabase.loadAllRows()
    val headers = postsDatabase.getHeaders()

    val renderer = FreemarkerTemplates().HotReload("src/main/resources")
    val viewModel = PostsPage(headers, rows)
    Response(OK).body(renderer(viewModel))
  },

  "/submitPost" bind POST to { request ->
    val title = request.form("title")!!
    val body = request.form("body")!!

    val postsDatabase = PostsDatabase()
    val lastId = postsDatabase.loadAllPosts().maxBy { it.id }.id
    val post = Post(lastId + 1, title, body)
    postsDatabase.addPost(post)
    Response(FOUND).header("Location", "/posts")
  },

  "/removePost" bind POST to { request ->
    val id = request.form("removeID")!!.toInt()
    val postsDatabase = PostsDatabase()
    val post = postsDatabase.loadAllPosts().find { it.id == id }!!
    postsDatabase.removePost(post)

    Response(FOUND).header("Location", "/posts")
  },

  "" bind POST to { request ->
    Response(FOUND).header("Location", "/index")
  }

)

fun main() {
  val server = app.asServer(Jetty(9000)).start()
  println("Server started on http://localhost:" + server.port())
}
