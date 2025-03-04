---
layout: post
slug: twin-peaks
title: Twin Peaks
date: 2025-02-15
description: David Lynch (1990)
creator: David Lynch
releaseYear: 1990
image: assets/media/twin-peaks/image.jpg
text: media/twin-peaks/text.md
pdf: ../../assets/media/twin-peaks/null
tags: [series]
categories: [series]
youtube: null
---

{% include  {{ page.text }} %}

{% if page.youtube %}
  {% youtube page.youtube %}
{% endif %}

{% unless page.pdf contains "null" %}
  {% pdf {{ page.pdf }} no_link %}
{% endunless %}

