---
# the default layout is 'page'
icon: fas fa-info-circle
order: 7
---

My vocabulary pages


{% for vocpage in site.vocabulary %}
<h2>

<a href="{{ site.baseurl }}{{ vocpage.url }}">{{ vocpage.title }}
</a>
</h2>
{% endfor %}
