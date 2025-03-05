---
layout: post
slug: sol-invictus
title: Sol Invictus  |  danse
date: 2025-02-21
description: Compagnie Hervé Koubi (Pin Galant)
image: assets/media/sol-invictus/image.jpg
text: media/sol-invictus/text.md
pdf: "../../assets/media/sol-invictus/null"
tags: [dance]
categories: [events]
youtube: https://www.youtube.com/watch?v=6bD6J7SY9lg

---

{% include  {{ page.text }} %}

**Programme** | Sol Invictus || danse contemporaine || Hervé Koubi |

{% if page.youtube %}
  {% youtube page.youtube %}
{% endif %}

{% unless page.pdf contains "null" %}
  {% pdf {{ page.pdf }} no_link %}
{% endunless %}

