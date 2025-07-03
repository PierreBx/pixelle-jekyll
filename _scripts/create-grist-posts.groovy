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
  imagesDir: new File("assets/media")
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

  println "⬇ Downloading image: ${fileName} into ${imageDir}"
  def url = "${CONFIG['baseUrl']}/${docId}/attachments/${attachmentId}/download"
  def connection = new URL(url).openConnection()
  connection.setRequestProperty("Authorization", "Bearer ${apiKey}")


  imageFile.withOutputStream { out ->
    connection.inputStream.withStream { input -> out << input }
  }

  return imageFile
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

static def fetchUniqueKeyFromGristTable(String tableId, String key, String value, CONFIG) {
  println("fetching ${tableId} ${key} ${value}  ")
  String baseURL = CONFIG['baseUrl'] as String
  String docId = CONFIG['docId'] as String
  String apiKey = CONFIG['apiKey'] as String

  String filter = "{ \"${key}\": [ \"${value}\" ] } "
  def urlEncodedFilter = URLEncoder.encode(filter, "UTF-8")
  String url = "${baseURL}/${docId}/tables/${tableId}/records?filter=${urlEncodedFilter}"


  println("filter: ${filter}")
  println("url: ${url}")

  def connection = new URL(url).openConnection()
  connection.setRequestProperty("Authorization", "Bearer ${apiKey}")

  def json = new JsonSlurper().parse(connection.inputStream)
  println("json returned: ${json.records}")
  println("json returned class: ${json.records.class}")
  println("json returned object: ${json.records[0].fields}")

  return json.records[0].fields
}

def writeMoviePost(Map record, CONFIG) {

  movie = record.fields

  // ===  FRONT MATTER VARIABLES  ===

  // layout
  String front_layout = "post"

  // date
  String front_date = movie.PostDate ? formatTimestamp(movie.PostDate) : "1972-03-07"

  // slug
  String name = movie.Name ?: "untitled"
  String front_slug = slugify(name)

  // title
  String front_title = "${name} | film"

  // description
  String releaseYear = movie.YearReleased ? getYearFromUnix(movie.YearReleased) : ""
  String directorName = fetchUniqueKeyFromGristTable('People', 'PeopleID', "${movie.Director}", CONFIG).FullName
  String front_description = "${directorName}  (${releaseYear})"

  // tags
  String front_tags = "[ movie ]"

  // category
  String front_category = "[ films ]"

  // ===  POST CONTENT  ===

  String content_text = movie.Text ?: ""

  // ===  POST MARKDOWN FILE CREATION  ===

  String filename = "${front_date}-${front_slug}"
  File postFile = new File(CONFIG['postsDir'] as File, "${filename}.md")

  // ===  POST ASSET DIRECTORY CREATION  ===

  File imageDir = new File(CONFIG['imagesDir'] as File, filename)
  imageDir.mkdirs()

  // === MAIN IMAGE RETRIEVING ===

  int mainAttachmentId = movie.Picture?.get(1)
  String imageName = GetAttachmentNameFromGrist(mainAttachmentId, CONFIG)
  File mainImageFile = downloadAttachmentFromGrist(mainAttachmentId, "main-${imageName}",
    imageDir, CONFIG)
  String front_postimagename = mainImageFile.path

  // === GALLERY IMAGES RETRIEVING ===

  galleryAttachments = movie.Gallery ?: []
  galleryAttachments.eachWithIndex { galleryAttachmentId, idx ->
    if (idx > 0) {
      String galleryImageName = GetAttachmentNameFromGrist(galleryAttachmentId as int, CONFIG)
      downloadAttachmentFromGrist(galleryAttachmentId as int,
        "gallery-${galleryImageName}" as String,
        imageDir,
        CONFIG)
    }
  }

  // === POST GALLERY CONTENT CREATION  ==
  String post_gallery = mylib.addPictures(imageDir.toString(), true)

  // === FRONTMATTER ==

  postFile.text = """---
layout: ${front_layout}
date: ${front_date}
slug: ${front_slug}
title: ${front_title}
description: ${front_description}
image: ${front_postimagename}
tags: ${front_tags}
categories: ${front_category}

---

${content_text}

${post_gallery}

"""

  println "✔ Post written (movie): ${postFile.name}"
}


// ===             ===
// === MAIN SCRIPT ===
// ===             ===

// === LOAD LIBRARY ===

print("   loading library...")

class FunctionLoader {
  static def loadFunctions(String filePath) {
    def script = new GroovyShell().parse(new File(filePath))
    return script
  }
}

mylib = FunctionLoader.loadFunctions("_scripts/Mylibrary.groovy")
mylib.greenText("done!")

// === MAIN EXECUTION ===

def records = fetchGristRecords(CONFIG)

records.each { writeMoviePost(it as Map, CONFIG) }

