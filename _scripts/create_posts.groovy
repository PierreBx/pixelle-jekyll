import org.yaml.snakeyaml.Yaml


def convertToSlug(input) {
  return input.toLowerCase().replaceAll(/\s+/, '-')
}
// Load the YAML file
def yaml = new Yaml() as Object
def inputFile = new File('./_data/data.yml')
def content = yaml.load(inputFile.text)

// Create separate post files for each entry
content.each { key, value ->
  println("creating post file for ${key}...")
  def postContent =
"""---
layout: post
title: ${value.content.title}
date: ${value.post.date}
director: ${value.content.director}
releaseYear: ${value.content.releaseYear}
---

by: {{ page.director }} ({{ page.releaseYear }})

"""

  def postFile = new File("./_movies/${key}.md")
  postFile.text = postContent
  println("done!")
}

println("post files created successfully!")
