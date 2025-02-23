---
# the default layout is 'page'
icon: fas fa-info-circle
order: 1
---

{% for movie in site.movies %}
<h2>
<br>
movie.url : {{ movie.url }}
<br>
movie.url | relative_url  : {{ movie.url | relative_url }}
</h2>

<h2>
<a href="{{ movie.url | relative_url }}">
{{ movie.title }} ({{ movie.director }}, {{ movie.releaseYear }})
</a>
</h2>
{% endfor %}
