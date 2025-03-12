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

// Function to clean special characters from the input text
def cleanText(String text) {
  return text.replaceAll("[^\\x20-\\x7E]", "")
}

// Load the YAML file
print("   loading datafile...")
def yaml = new Yaml() as Object
def inputFile = new File('./_data/vocabulary/vocabulary.yml')
//def cleanedContent = cleanText(inputFile.text)
def content = yaml.load(inputFile.text)
mylib.greenText("done!")

def groupedWords = content.dictionary.groupBy { entry ->
  def word = entry.word.italian.itword

//  println("word: ${word}")
  if (word && word[0]) {
    word[0].toLowerCase()
  } else {
    null
  }
}
// println("size: ${groupedWords.size()}")
// println("Keys the entries are grouped by:")
groupedWords.each { key, value ->
  println("${key}: ${value.size()}")
}
// println("Total number of entries: ${content.dictionary.size()}")


groupedWords.each { letter, entries ->
  print("      creating ${entries.size()} entries for letter: ${letter}")
  def postFile = new File("_vocabulary/", "words-starting-with-${letter}.md")
  postFile.withWriter { writer ->
    writer.writeLine("---")
    writer.writeLine("layout: post")
    writer.writeLine("title: Words starting with '${letter.toUpperCase()}'")
    // writer.writeLine("date: 2025-03-07")
    writer.writeLine("---")

    for (entry in entries) {

      writer.writeLine("| ${entry.word.italian.itword} | ${entry.word.french.word} |")

    }

  }

  mylib.greenText("done!")
}

mylib.greenText("All vocabulary posts created successfully!")
