---
# the default layout is 'page'
icon: fas fa-info-circle
order: 1
---

{% for movie in site.movies %}
<h2>
<a href="{{ movie.url | relative_url }}">
{{ movie.title }} ({{ movie.author }}, {{ movie.releaseYear }})
</a>
</h2>
{% endfor %}
