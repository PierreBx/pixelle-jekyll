@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')
@Grab('org.apache.commons:commons-lang3:3.12.0')
import org.apache.commons.lang3.StringUtils
import groovy.json.*

import java.sql.Struct
import java.text.SimpleDateFormat
import java.net.URLEncoder


// === CONFIGURATION ===
CONFIG = [
  apiKey   : '0e3eaf66d37894088e6aa62dce22696904775759',
  docId    : 'rt3QMc825NvK9dQCtjHfZt',
  tableId  : 'Blog_films',
  baseUrl  : "http://localhost:8484/api/docs",
  postsDir : new File("_posts"),
  imagesDir : new File("assets/media")
]


// === SETUP ===
CONFIG['postsDir'].mkdirs()
CONFIG['imagesDir'].mkdirs()


// === UTILS ===

static String formatTimestamp(long timestamp) {
  Date date = new Date(timestamp * 1000) // Convert seconds to milliseconds
  return new SimpleDateFormat("yyyy-MM-dd").format(date)
}

String getYearFromUnix(long unixTimestamp) {
  def date = new Date(unixTimestamp * 1000)  // Java expects milliseconds
  return date.format("yyyy")  // Return year in YYYY format
}


String slugify(String str) {
  StringUtils.stripAccents(str).toLowerCase()
    .replaceAll(/[^a-z0-9]+/, "-")
    .replaceAll(/^-+|-+$/, "")
}

File downloadAttachmentFromGrist(int attachmentId, String fileName, File imageDir, CONFIG) {
  docId = CONFIG['docId']
  apiKey = CONFIG['apiKey']


  File imageFile = new File(imageDir, fileName)

  println "⬇ Downloading image: ${fileName}"
  def url = "${CONFIG['baseUrl']}/${docId}/attachments/${attachmentId}/download"
  def connection = new URL(url).openConnection()
  connection.setRequestProperty("Authorization", "Bearer ${apiKey}")


  imageFile.withOutputStream { out ->
    connection.inputStream.withStream { input -> out << input }
  }

  return imageFile
}

String GetPeopleNameFromId(int peopleId, CONFIG) {
  docId = CONFIG['docId']
  apiKey = CONFIG['apiKey']


  println "⬇ Retrieving people name for id: ${peopleId}"
  def url = "${CONFIG['baseUrl']}/${docId}/attachments/${attachmentId}"
  def connection = new URL(url).openConnection()
  connection.setRequestProperty("Authorization", "Bearer ${apiKey}")


  // Parse the response from the API
  def jsonResponse = new JsonSlurper().parse(connection.inputStream)
  //println(jsonResponse)

  // Extract the image name
  def imageName = jsonResponse.fileName

  println "✅ Image name retrieved: ${imageName}"
  return imageName
}

String GetAttachmentNameFromGrist(int attachmentId, CONFIG) {
  docId = CONFIG['docId']
  apiKey = CONFIG['apiKey']


  println "⬇ Retrieving image name for attachment id: ${attachmentId}"
  def url = "${CONFIG['baseUrl']}/${docId}/attachments/${attachmentId}"
  def connection = new URL(url).openConnection()
  connection.setRequestProperty("Authorization", "Bearer ${apiKey}")


  // Parse the response from the API
  def jsonResponse = new JsonSlurper().parse(connection.inputStream)
  //println(jsonResponse)

  // Extract the image name
  def imageName = jsonResponse.fileName

  println "✅ Image name retrieved: ${imageName}"
  return imageName
}


static def fetchGristRecords(CONFIG) {
  String baseURL = CONFIG['baseUrl'] as String
  String docId = CONFIG['docId'] as String
  String tableId = CONFIG['tableId'] as String
  String apiKey = CONFIG['apiKey'] as String
  def url = "${baseURL}/${docId}/tables/${tableId}/records"
  //println(url)
  def connection = new URL(url).openConnection()
  connection.setRequestProperty("Authorization", "Bearer ${apiKey}")
  def json = new JsonSlurper().parse(connection.inputStream)
  return json.records
}

static def  fetchUniqueKeyFromGristTable(String tableId, String key, String value,  CONFIG) {
  println("fetching ${tableId} ${key} ${value}  ")
  String baseURL = CONFIG['baseUrl'] as String
  String docId = CONFIG['docId'] as String
  String apiKey = CONFIG['apiKey'] as String
  def String url = ""
  String filter = ""


  filter = "{ \"${key}\": [ \"${value}\" ] } "
  def urlEncodedFilter = URLEncoder.encode(filter, "UTF-8")
  url = "${baseURL}/${docId}/tables/${tableId}/records?filter=${urlEncodedFilter}"


  println("filter: ${filter}")
  println("url: ${url}")

  def connection = new URL(url).openConnection()
  connection.setRequestProperty("Authorization", "Bearer ${apiKey}")
  def json = new JsonSlurper().parse(connection.inputStream)
  println("json returned: ${json.records}")
  println("json returned class: ${json.records.class}")
  return json.records
}

def writeMoviePost(Map record, CONFIG) {
  movie = record.fields
  String date = movie.PostDate ? formatTimestamp(movie.PostDate) : "1972-03-07"
  String name = movie.Name ?: "untitled"
  String slug = slugify(name)
  String director = movie.GetDirector ?: ""
  //String actors = movie.GetActorss ?: ""
  String releaseYear = movie.YearReleased ? getYearFromUnix(movie.YearReleased) : ""
  //String directorJson = fetchUniqueKeyFromGristTable('People', 'FirstName', 'Woody',  CONFIG)
  //println("DirectorJson: ${directorJson}")
  //println("movie.Director: ${movie.Director}")
  directorJson2 = fetchUniqueKeyFromGristTable('People', 'PeopleID', "${movie.Director}",  CONFIG)
  println("DirectorJson2: ${directorJson2}")
  println("DirectorJson2 class: ${directorJson2.class}")

  directorName = directorJson2[0].fields.LastName
  println("DirectorName: ${directorName}")
  String tags = "[ movie ]"
  String category = "[ films ]"
  String title = "${name} | film"
  String description = "${director}  (${releaseYear})"
  String text = movie.Text ?: ""

  def filename = "${date}-${slug}"
  File postFile = new File(CONFIG['postsDir'] as File, "${filename}.md")
  File imageDir = new File(CONFIG['imagesDir'] as File, filename)
  imageDir.mkdirs()



  // === MAIN IMAGE ===

  int mainAttachmentId = movie.Picture?.get(1)
  String imageName = GetAttachmentNameFromGrist(mainAttachmentId, CONFIG)
  mainImageFile = downloadAttachmentFromGrist(mainAttachmentId as int,
    "main-${imageName}" as String,
    imageDir,
    CONFIG)
  mainImagePath = mainImageFile.path as String


  // === GALLERY IMAGES ===

  galleryAttachments = movie.Gallery ?: []
  galleryAttachments.eachWithIndex { galleryAttachmentId, idx ->
    if (idx > 0) {
      String galleryImageName = GetAttachmentNameFromGrist(galleryAttachmentId as int, CONFIG)
      downloadAttachmentFromGrist(galleryAttachmentId as int,
        "gallery-${galleryImageName}" as String,
        imageDir,
        CONFIG)
    }

    // === FRONTMATTER ==

    postFile.text= """---
layout: post
date: ${date}
slug: ${slug}
title: "${title}"
description: ${description}
image: ${mainImagePath}
tags: ${tags}
categories: ${category}

---

"${text}"


"""

    println "✔ Post written (movie): ${postFile.name}"
  }

}


// === MAIN EXECUTION ===

def records = fetchGristRecords(CONFIG)

records.each { writeMoviePost(it as Map, CONFIG) }

