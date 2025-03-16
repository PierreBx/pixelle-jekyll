---
layout: post
slug: annie-hall
title: Annie Hall  |  film
date: 1985-01-17
description: Woody Allen (1977)
director: Woody Allen
releaseYear: 1977
image: assets/media/1985-01-17-annie-hall/image.jpg
text: media/1985-01-17-annie-hall/text.md
pdf: ../../assets/media/1985-01-17-annie-hall/null
tags: [movie]
categories: [films]
youtube: https://youtu.be/5h5zurZsIQY?si=UBAuOLgEwbLJh03q

---

{% include  {{ page.text }} %}

{% if page.youtube %}
  {% youtube page.youtube %}
{% endif %}

{% unless page.pdf contains "null" %}
  {% pdf {{ page.pdf }} no_link %}
{% endunless %}

