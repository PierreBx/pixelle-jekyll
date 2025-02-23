# Pixelle structure

## data folder (_data)

### structure
```shell
├─ _data/
├───  data.yml
├───  media/
├─────  slug1
├───────  image1.jpg
├───────  image2.webp
├───────  text.md
├─ _scripts
├─────  create_posts.groovy
```

### data.yml
```yml
blue-velvet:  
  post:
    type: film
    date: "2023-03-02"
    tags :  [movie]
    categories: [films]
  content:
    title: Blue Velvet
    director: David Lynch
    releaseYear: 1986
  links: 
    - https://en.wikipedia.org/wiki/Blue_Velvet_(film)
    - https://www.imdb.com/fr/title/tt0090756/
  ```

### create_posts.groovy

It will create the posts by:

- iterating through data.yml
- for each post: 
  - if it's a film:
    - copy its images into _assets/_media_
    - copy its texts into  _includes/_media_
    - create a post: _posts/_date_-_slug_.md containing
      - the 'front matter'
      - the content
      - the pictures
      - the texts
