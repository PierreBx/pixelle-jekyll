---
layout: post
slug: eva-jospin
title: Eva Jospin - Chapelle de la Grave  |  exposition
date: 2025-01-18
description: Exposition
city: Toulouse
image: assets/media/2025-01-18-eva-jospin/image.jpg
text: media/2025-01-18-eva-jospin/text.md
tags: [exhibition]
categories: [trips]

---

{% include  {{ page.text }} %}



![text](assets/media/2025-01-18-eva-jospin/pictures/image2.jpg)

<div style="text-align: center;"><i>image2</i></div>


![text](assets/media/2025-01-18-eva-jospin/pictures/image3.jpg)

<div style="text-align: center;"><i>image3</i></div>


![text](assets/media/2025-01-18-eva-jospin/pictures/image1.jpg)

<div style="text-align: center;"><i>image1</i></div>


<div id="map" style="height: 400px;"></div>

<script>
    var map = L.map('map').setView([43.60098884509048, 1.4329312111961556], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

      var marker = L.marker([43.60098884509048, 1.4329312111961556]).addTo(map)
    .bindPopup("Chapelle de la Grave")
    .openPopup();

</script>

