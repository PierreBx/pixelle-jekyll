@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')
@Grab('org.apache.commons:commons-lang3:3.12.0')
import org.apache.commons.lang3.StringUtils
import groovy.json.*
import java.text.SimpleDateFormat


// === CONFIGURATION ===
CONFIG = [
  apiKey   : '0e3eaf66d37894088e6aa62dce22696904775759',
  docId    : 'rt3QMc825NvK9dQCtjHfZt',
  tableId  : 'Blog',
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
  println(url)
  def connection = new URL(url).openConnection()
  connection.setRequestProperty("Authorization", "Bearer ${apiKey}")
  def json = new JsonSlurper().parse(connection.inputStream)
  return json.records
}


def writePostFile(Map record, CONFIG) {
  def fields = record.fields
  String date = fields.post_date ? formatTimestamp(fields.post_date) : "1972-03-07"
  String name = fields.post_name ?: "untitled"
  String type = fields.post_type ?: "- | -"
  Collection types = type.split("\\|")
  String typePost = types[0] // eg. event => façon avec laquelle le post est décrit
  String typeEvent = types[1] // eg. opéra => 1ère ligne 2de place
  Collection tagCollection = (fields.post_tags ?: "unclassified").split("\\|")
  String tags = "[ " + tagCollection.join(", ") + " ]"
  String category = "[ " + (fields.post_category ?: "uncategorized") + " ]"
  String description = fields.post_description ?: ""
  String content = fields.post_text ?: ""
  println(name)
  def slug = slugify(name)
  def filename = "${date}-${slug}"
  File postFile = new File(CONFIG['postsDir'] as File, "${filename}.md")
  File imageDir = new File(CONFIG['imagesDir'] as File, filename)
  imageDir.mkdirs()



  // === MAIN IMAGE ===

  int mainAttachmentId = fields.post_picture?.get(1)
  String imageName = GetAttachmentNameFromGrist(mainAttachmentId, CONFIG)
  mainImageFile = downloadAttachmentFromGrist(mainAttachmentId as int,
    "main-${imageName}" as String,
    imageDir,
    CONFIG)
  mainImagePath = mainImageFile.path as String


  // === GALLERY IMAGES ===

  galleryAttachments = fields.post_gallery ?: []
  galleryAttachments.eachWithIndex { galleryAttachmentId, idx ->
    if (idx > 0) {
      String galleryImageName = GetAttachmentNameFromGrist(galleryAttachmentId as int, CONFIG)
      downloadAttachmentFromGrist(galleryAttachmentId as int,
        "gallery-${galleryImageName}" as String,
        imageDir,
        CONFIG)
    }

    // === FRONTMATTER ==

    def frontMatter = """---
layout: post
slug: ${slug}
title: "${name} | ${typeEvent}"
date: ${date}
description: "${description}"
image: ${mainImagePath}
tags: ${tags}
categories: ${category}

---


"""


    postFile.text = frontMatter + content + "\n\n"
    println "✔ Post written: ${postFile.name}"
  }

}
// === MAIN EXECUTION ===

def records = fetchGristRecords(CONFIG)
records.each { writePostFile(it as Map, CONFIG) }

