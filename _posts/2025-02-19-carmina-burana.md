---
layout: post
slug: carmina-burana
title: Carmina Burana (Choeur)
date: 2025-02-19
description: Choeur de l'Opéra de Bordeaux (Auditorium)
image: assets/media/carmina-burana/image.webp
text: media/carmina-burana/text.md
pdf: "../../assets/media/carmina-burana/livret.pdf"
tags: [opera]
categories: [events]
youtube: null

---

{% include  {{ page.text }} %}

**Programme** | Carmina Burana || cantate scénique || Carl Orff || 1935 |

{% if page.youtube %}
  {% youtube page.youtube %}
{% endif %}

{% unless page.pdf contains "null" %}
  {% pdf {{ page.pdf }} no_link %}
{% endunless %}

