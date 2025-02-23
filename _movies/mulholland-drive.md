---
layout: post
title: Mulholland Drive
date: 2025-02-05
author: David Lynch
releaseYear: 2001
link: https://en.wikipedia.org/wiki/Mulholland_Drive_(film)
dir: mul
---

by: {{ page.author }} ({{ page.releaseYear }})

{% assign file_dir_name = page.dir %}  {% comment %} Directory name from front matter {% endcomment %}

<br> page.dir = {{ page.dir }}

{% assign full_file_dir = "assets/" | append: file_dir_name %}  {% comment %} Construct full path {% endcomment %}

<br> full_file_dir = {{ full_file_dir }}

{% assign all_files = site.static_files | where_exp: "file", "file.path contains full_file_dir" | sort: "name" %}

<br> all_files = {{ all_files }}

<br> page.path = {{ page.path }}

<ul>
  {% for file in all_files %}
    {% assign relative_path = "../.." | append: file.path %}
    <li>file path : {{ file.path }}  <br> file basename {{ file.basename }} <br> file relative path  {{ relative_path }}
      <br>  file relative_url  {{ file | relative_url }} <br>
    {% if file.extname == ".jpeg" %}
     => extension .jpeg : this is an image <br>
    {% elsif file.extname == ".md" %}
     => extension .md   : this is a markdown <br>
     ==> trying to include {{ relative_path }}:

      {% comment %} {% include_relative ../../assets/blv/markdown.md %}   {% endcomment %}
       {% include_relative markdown.md %}




    {% endif %}
   </li>

  {% endfor %}
</ul>


