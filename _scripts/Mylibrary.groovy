

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
tags: ${value.post.tags}
categories: ${value.post.categories}
---

by: {{ page.director }} ({{ page.releaseYear }})

{% include  {{ page.text }} %}


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
tags: ${value.post.tags}
categories: ${value.post.categories}
---

by: {{ page.creator }} ({{ page.releaseYear }})

{% include  {{ page.text }} %}


"""

    def postFile = new File("./_posts/${value.post.date}-${key}.md")
    postFile.text = postContent

  }

