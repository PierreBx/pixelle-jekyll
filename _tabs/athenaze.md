---
# the default layout is 'page'
icon: fas fa-info-circle
order: 2
---

My greek pages


{% for greekpage in site.athenaze %}
<h2>

{{ greekpage.date | date: "%Y-%m-%d"  }} - <a href="{{ site.baseurl }}{{ greekpage.url }}">{{ greekpage.title }}
</a>
</h2>
{% endfor %}
