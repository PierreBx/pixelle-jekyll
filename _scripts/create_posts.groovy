import org.yaml.snakeyaml.Yaml

def greenText(String text) {
  println "\u001B[32m${text}\u001B[0m"
}

def purpleText(String text) {
  print "\u001B[35m${text}\u001B[0m"
}


// Load the YAML file
def yaml = new Yaml() as Object
def inputFile = new File('./_data/data.yml')
println("   datafile retrieved...")

def content = yaml.load(inputFile.text)

// Create separate post files for each entry
content.each { key, value ->
  print("   creating post file for ")
  purpleText(" ${key}")
  print("...")
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

  def postFile = new File("./_posts/${value.post.date}-${key}.md")
  postFile.text = postContent
  greenText("done!")
}

println("   post files created successfully!")
