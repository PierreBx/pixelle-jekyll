---
# the default layout is 'page'
icon: fas fa-info-circle
order: 2
---

My jazz pages


{% for jazzpage in site.jazz %}
<h2>

{{ jazzpage.date | date: "%Y-%m-%d"  }} - <a href="{{ site.baseurl }}{{ jazzpage.url }}">{{ jazzpage.title }}
</a>
</h2>
{% endfor %}
