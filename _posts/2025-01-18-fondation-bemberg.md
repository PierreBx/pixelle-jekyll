---
layout: post
slug: fondation-bemberg
title: Fondation Bemberg - Hôtel d'Assézat  |  exposition
date: 2025-01-18
description: Fondation Bemberg
city: Toulouse
image: assets/media/2025-01-18-fondation-bemberg/image.jpg
text: media/2025-01-18-fondation-bemberg/text.md
tags: [exhibition]
categories: [trips]

---

{% include  {{ page.text }} %}



![text](assets/media/2025-01-18-fondation-bemberg/pictures/Renoir - La Loge - 1879.jpg)

<div style="text-align: center;"><i>Renoir - La Loge - 1879</i></div>


![text](assets/media/2025-01-18-fondation-bemberg/pictures/Fantin-Latour - Portait de Fantin - 1860.jpg)

<div style="text-align: center;"><i>Fantin-Latour - Portait de Fantin - 1860</i></div>


![text](assets/media/2025-01-18-fondation-bemberg/pictures/Caillebotte - Le Petit Bras de la Seine en Automne - 1890.webp)

<div style="text-align: center;"><i>Caillebotte - Le Petit Bras de la Seine en Automne - 1890</i></div>

<br> <br>

---

<link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />

<script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>

<div id="map" style="height: 500px; width: 100%;"></div>

<script>
   var map = L.map('map').setView([43.6002806652585, 1.4421788072331159], 15); // Zoom levels typically range from 0 (world view) to 18 (street level view),

  // default openstreetmap
   L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
     }).addTo(map);

    // stamen design
   // L.tileLayer('https://{s}.tile.stamen.com/toner/{z}/{x}/{y}.png', {
    //attribution: '&copy; <a href="https://stamen.com">Stamen Design</a>'
   // }).addTo(map);

      // Watercolor
   // L.tileLayer('https://{s}.tile.stamen.com/watercolor/{z}/{x}/{y}.jpg', {
   //  attribution: '&copy; <a href="https://stamen.com">Stamen Design</a>'
   // }).addTo(map);

   // dark openstreetmap
  //  L.tileLayer('https://{s}.tiles.wmflabs.org/bw-mapnik/{z}/{x}/{y}.png', {
  // attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
 // }).addTo(map);

  var marker = L.marker([43.6002806652585, 1.4421788072331159]).addTo(map)
    .bindPopup("Hôtel d'Assézat")
    .openPopup();
</script>

