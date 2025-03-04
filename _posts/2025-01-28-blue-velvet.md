---
layout: post
slug: blue-velvet
title: Blue Velvet
date: 2025-01-28
description: David Lynch (1986)
director: David Lynch
releaseYear: 1986
image: assets/media/blue-velvet/image.jpeg
text: media/blue-velvet/text.md
pdf: ../../assets/media/blue-velvet/null
tags: [movie]
categories: [films]
youtube: null

---

{% include  {{ page.text }} %}

{% if page.youtube %}
  {% youtube page.youtube %}
{% endif %}

{% unless page.pdf contains "null" %}
  {% pdf {{ page.pdf }} no_link %}
{% endunless %}

