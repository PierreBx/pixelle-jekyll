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
  def postContent =
"""---
layout: post
title: ${value.post.title}
date: ${value.post.date}
author: ${value.content.author}
releaseYear: ${value.content.releaseYear}
picture: ${value.content.picture}
text: ${value.content.text}
---

by: {{ page.author }} ({{ page.releaseYear }})


![{{ page.title }}]({{ page.picture }})

{{ page.text }}

"""
  def postFile = new File("./_movies/${key}.md")
  postFile.text = postContent
}

println("post files created successfully!")
