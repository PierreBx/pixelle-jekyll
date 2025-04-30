---
layout: post
slug: les-abattoirs
title: Ouvrir les yeux - Collection photographique - Les Abattoirs  |  exposition
date: 2025-01-18
description: Exposition
city: Toulouse
image: assets/media/2025-01-18-les-abattoirs/image.webp
text: media/2025-01-18-les-abattoirs/text.md
tags: [exhibition]
categories: [trips]

---

{% include  {{ page.text }} %}



![text](assets/media/2025-01-18-les-abattoirs/pictures/(photo1).jpg)

<br><br>


![text](assets/media/2025-01-18-les-abattoirs/pictures/(photo2).jpg)

<br><br>


![text](assets/media/2025-01-18-les-abattoirs/pictures/(photo3).jpg)

<br><br>


![text](assets/media/2025-01-18-les-abattoirs/pictures/(photo4).webp)

<br><br>


![text](assets/media/2025-01-18-les-abattoirs/pictures/(photo5).webp)

<br><br>


<div id="map" style="height: 400px;"></div>

<script>
    var map = L.map('map').setView([43.601299259601284, 1.4294525993951106], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

      var marker = L.marker([43.601299259601284, 1.4294525993951106]).addTo(map)
    .bindPopup("Les Abattoirs")
    .openPopup();

</script>

