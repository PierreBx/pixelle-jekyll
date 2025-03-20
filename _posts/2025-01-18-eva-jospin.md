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

<br> <br>

---

<link rel="stylesheet" href="https://unpkg.com/leaflet/dist/leaflet.css" />

<script src="https://unpkg.com/leaflet/dist/leaflet.js"></script>

<div id="map" style="height: 500px; width: 100%;"></div>

<script>
   var map = L.map('map').setView([43.60098884509048, 1.4329312111961556], 15); // Zoom levels typically range from 0 (world view) to 18 (street level view),

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

  var marker = L.marker([43.60098884509048, 1.4329312111961556]).addTo(map)
    .bindPopup("Chapelle de la Grave")
    .openPopup();
</script>

