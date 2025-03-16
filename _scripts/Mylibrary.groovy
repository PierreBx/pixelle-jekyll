static String addPictures(String key, Boolean addpictures) {

  def result   = ""

  if (addpictures) {
    def picturesDir = new File("assets/media/${key}/pictures")
    picturesDir.eachFile { file ->
      result += "\n\n![text](${file.path})\n"
      result += "\n<div style=\"text-align: center;\"><i>${file.name.substring(0, file.name.lastIndexOf('.'))}</i></div>\n"
    }

    return result
  }




  return result
}

static String fixLength(String input, int length) {
  input.padRight(length).substring(0, length)
}

static String createGoogleSearchLink(Object... keywords) {
  def baseUrl = "https://www.google.com/search?q="
  def query = keywords.collect { it.toString().replaceAll(" ", "+") }.join("+")
  def url = baseUrl + query
  return "<a href=\"${url}\" target=\"_blank\">Google it</a>"
}

void greenText(String text) {
    println ("\u001B[32m${text}\u001B[0m")
  }

static void purpleText(String text) {
    print "\u001B[35m${text}\u001B[0m"
  }

static void redText(String text) {
  print "\u001B[31m${text}\u001B[0m"
}

static void createMoviePost(String key, Object value, String date, String slug) {

  if (value.post.description == null) {
    value.post.description = value.content.director + " (" + value.content.releaseYear + ")"
  }
  def postContent =
    """---
layout: post
slug: ${slug}
title: ${value.post.title}  |  ${value.post.type[1]}
date: ${date}
description: ${value.post.description}
director: ${value.content.director}
releaseYear: ${value.content.releaseYear}
image: assets/media/${key}/${value.media.picture}
text: media/${key}/${value.media.text}
pdf: ../../assets/media/${key}/${value.media.pdf}
tags: ${value.post.tags}
categories: ${value.post.categories}
youtube: ${value.media.youtube}

---

{% include  {{ page.text }} %}

{% if page.youtube %}
  {% youtube page.youtube %}
{% endif %}

{% unless page.pdf contains "null" %}
  {% pdf {{ page.pdf }} no_link %}
{% endunless %}

"""

    def postFile = new File("./_posts/${key}.md")
    postFile.text = postContent

  }

static void createSeriesPost(String key, Object value, String date, String slug) {

  if (value.post.description == null) {
    value.post.description = value.content.creator + " (" + value.content.releaseYear + ")"
  }

  def postContent =
      """---
layout: post
slug: ${slug}
title: ${value.post.title}  |  ${value.post.type[1]}
date: ${date}
description: ${value.post.description}
creator: ${value.content.creator}
releaseYear: ${value.content.releaseYear}
image: assets/media/${key}/${value.media.picture}
text: media/${key}/${value.media.text}
pdf: ../../assets/media/${key}/${value.media.pdf}
tags: ${value.post.tags}
categories: ${value.post.categories}
youtube: ${value.media.youtube}
---

{% include  {{ page.text }} %}

{% if page.youtube %}
  {% youtube page.youtube %}
{% endif %}

{% unless page.pdf contains "null" %}
  {% pdf {{ page.pdf }} no_link %}
{% endunless %}

"""

    def postFile = new File("./_posts/${key}.md")
    postFile.text = postContent

  }

static void createEventPost(String key, Object value, String date, String slug) {

  if (value.post.description == null) {
    value.post.description = ""
  }

  def program = new StringBuilder()
  program.append("**Programme** \r\r")
  value.content.each { item ->
    item.each { key2, value2 ->
      program.append("| ${value2} |")
    }
    program.append("\r")
  }

  def programString = program.toString()
  def googleSearchString = createGoogleSearchLink(value.post.title,
                                                         value.post.type[1],
                                                         value.post.description,
                                                         date)

  def postContent =
    """---
layout: post
slug: ${slug}
title: ${value.post.title}  |  ${value.post.type[1]}
date: ${date}
description: ${value.post.description}
image: assets/media/${key}/${value.media.picture}
text: media/${key}/${value.media.text}
pdf: "../../assets/media/${key}/${value.media.pdf}"
tags: ${value.post.tags}
categories: ${value.post.categories}
youtube: ${value.media.youtube}

---

${programString}

{% include  {{ page.text }} %}

${addPictures(key, value.media.addpictures ? true : false)}

{% if page.youtube %}
  {% youtube page.youtube %}
{% endif %}

{% unless page.pdf contains "null" %}
  {% pdf {{ page.pdf }} no_link %}
{% endunless %}

---

<div>
    <p style="text-align: left;"> ${googleSearchString} </p>
</div>

"""

  def postFile = new File("./_posts/${key}.md")
  postFile.text = postContent

}

static void createBookPost(String key, Object value, String date, String slug) {

  if (value.post.description == null) {
    value.post.description = "${value.content.author} (${value.content.published})"
  }

  def postContent =
    """---
layout: post
slug: ${slug}
title: ${value.post.title}  |  ${value.post.type[1]}
date: ${date}
description: ${value.post.description}
image: assets/media/${key}/${value.media.picture}
text: media/${key}/${value.media.text}
tags: ${value.post.tags}
categories: ${value.post.categories}

---

{% include  {{ page.text }} %}

"""

  def postFile = new File("./_posts/${key}.md")
  postFile.text = postContent

}

static void createExpoPost(String key, Object value, String date, String slug) {

  if (value.post.description == null) {
    value.post.description = "${value.content.city}"
  }

  def postContent =
    """---
layout: post
slug: ${slug}
title: ${value.post.title}  |  ${value.post.type[1]}
date: ${date}
description: ${value.post.description}
city: ${value.place}
image: assets/media/${key}/${value.media.picture}
text: media/${key}/${value.media.text}
tags: ${value.post.tags}
categories: ${value.post.categories}

---

{% include  {{ page.text }} %}

"""

  postContent += addPictures(key, value.media.addpictures ? true : false)

  def postFile = new File("./_posts/${key}.md")
  postFile.text = postContent

}

