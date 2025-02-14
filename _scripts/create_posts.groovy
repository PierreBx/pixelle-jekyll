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
title: ${value.post.title}
date: ${value.post.date}
tags:  ${value.post.tags}
categories:  ${value.post.categories}
author: ${value.content.author}
releaseYear: ${value.content.releaseYear}
picture: ${value.content.picture}
text: ${value.content.text}
---

by: {{ page.author }} ({{ page.releaseYear }})


![{{ page.title }}]({{ page.picture }})

{{ page.text }}

"""
  def postFile = new File("./_posts/${value.post.date}-${key}.md")
  postFile.text = postContent
}

println("post files created successfully!")
