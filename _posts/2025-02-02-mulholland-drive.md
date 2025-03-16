---
layout: post
slug: mulholland-drive
title: Mulholland Drive  |  film
date: 2025-02-02
description: David Lynch (2001)
director: David Lynch
releaseYear: 2001
image: assets/media/2025-02-02-mulholland-drive/image.webp
text: media/2025-02-02-mulholland-drive/text.md
pdf: ../../assets/media/2025-02-02-mulholland-drive/null
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

