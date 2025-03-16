import org.yaml.snakeyaml.Yaml

// Load the function library file
print("   loading library...")
class FunctionLoader {
  static def loadFunctions(String filePath) {
    def script = new GroovyShell().parse(new File(filePath))
    return script
  }
}
def mylib = FunctionLoader.loadFunctions("_scripts/Mylibrary.groovy")
mylib.greenText("done!")

// Load the YAML file
print("   loading datafile...")
def yaml = new Yaml() as Object
def inputFile = new File('./_data/data.yml')
def content = yaml.load(inputFile.text)
mylib.greenText("done!")

// Create separate post files for each entry
content.each { key, value ->
  print("   creating post file for ")
  def fl = mylib.fixLength(value.post.type[0], 6)

  def date = key.substring(0, 10)
  def slug = key.substring(11)
  mylib.purpleText("${fl} ${date} ${slug} (${value.post.type[1]})...")
  switch(value.post.type[0]) {
    case "movie":
      mylib.createMoviePost(key, value, date, slug)
      break
    case "series":
      mylib.createSeriesPost(key, value, date, slug)
      break
    case "event":
      mylib.createEventPost(key, value, date, slug)
      break
    case "book":
      mylib.createBookPost(key, value, date, slug)
      break
    case "exhibition":
      mylib.createExpoPost(key, value, date, slug)
      break
    default :
      mylib.redText("Unknown post type: '${value.post.type[0]}'.\r\r")
      System.exit(1)
  }
  mylib.greenText("done!")

}

println("post files created successfully!")
