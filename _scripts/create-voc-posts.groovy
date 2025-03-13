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
def inputFile = new File('./_data/vocabulary/vocabulary.yml')
def content = yaml.load(inputFile.text)
mylib.greenText("done!")

def groupedWords = content.dictionary.groupBy { entry ->
  def word = entry.word.italian.itword

  if (word && word[0]) {
    word[0].toLowerCase()
  } else {
    null
  }
}

groupedWords.each { letter, entries ->
  print("      creating ${entries.size()} entries for letter: ${letter}...")
  def postFile = new File("_vocabulary/", "words-starting-with-${letter}.md")
  postFile.withWriter { writer ->
    writer.writeLine("---")
    writer.writeLine("layout: post")
    writer.writeLine("title: Words starting with '${letter.toUpperCase()}'")
    writer.writeLine("---")

    for (entry in entries) {

      writer.writeLine("| ${entry.word.italian.itword} | ${entry.word.french.word} |")

    }

  }

  mylib.greenText("done!")
}

mylib.greenText("All vocabulary posts created successfully!")
