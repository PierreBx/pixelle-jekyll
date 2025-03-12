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
  mylib.purpleText("${fl} ${key} (${value.post.type[1]})...")
  switch(value.post.type[0]) {
    case "movie":
      mylib.createMoviePost(key, value)
      break
    case "series":
      mylib.createSeriesPost(key, value)
      break
    case "event":
      mylib.createEventPost(key, value)
      break
    case "book":
      mylib.createBookPost(key, value)
      break
    case "exhibition":
      mylib.createExpoPost(key, value)
      break
    default :
      mylib.redText("Unknown post type: '${value.post.type[0]}'.\r\r")
      System.exit(1)
  }
  mylib.greenText("done!")

}

println("post files created successfully!")
