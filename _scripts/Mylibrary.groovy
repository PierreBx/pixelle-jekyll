void greenText(String text) {
    println ("\u001B[32m${text}\u001B[0m")
  }

static void purpleText(String text) {
    print "\u001B[35m${text}\u001B[0m"
  }

static void createFilmPost(String key, Object value) {
    if (value.post.description == null) {
      value.post.description = value.content.director + " (" + value.content.releaseYear + ")"
    }
    def postContent =
      """---
layout: post
slug: ${key}
title: ${value.content.title}
date: ${value.post.date}
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

    def postFile = new File("./_posts/${value.post.date}-${key}.md")
    postFile.text = postContent

  }

static void createSeriesPost(String key, Object value) {
    if (value.post.description == null) {
      value.post.description = value.content.creator + " (" + value.content.releaseYear + ")"
    }
    def postContent =
      """---
layout: post
slug: ${key}
title: ${value.content.title}
date: ${value.post.date}
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

    def postFile = new File("./_posts/${value.post.date}-${key}.md")
    postFile.text = postContent

  }

static void createEventsPost(String key, Object value) {
  if (value.post.description == null) {
    value.post.description = ""
  }

  def list = new StringBuilder()
  list.append("**Programme** \r\r")
  value.content.each { item ->
    item.each { key2, value2 ->
      list.append("| ${value2} |")
    }
    list.append("\r")
  }

  def resultString = list.toString()

  def postContent =
    """---
layout: post
slug: ${key}
title: ${value.post.title}
date: ${value.post.date}
description: ${value.post.description}
image: assets/media/${key}/${value.media.picture}
text: media/${key}/${value.media.text}
pdf: "../../assets/media/${key}/${value.media.pdf}"
tags: ${value.post.tags}
categories: ${value.post.categories}
youtube: ${value.media.youtube}

---

{% include  {{ page.text }} %}

${resultString}

{% if page.youtube %}
  {% youtube page.youtube %}
{% endif %}

{% unless page.pdf contains "null" %}
  {% pdf {{ page.pdf }} no_link %}
{% endunless %}

"""

  def postFile = new File("./_posts/${value.post.date}-${key}.md")
  postFile.text = postContent

}

