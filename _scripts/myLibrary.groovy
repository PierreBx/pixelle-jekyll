@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

class Mylibrary {
  static void greenText(String msg) {
    println "\u001B[32m${msg}\u001B[0m"
  }

  static String getFullNameFromGrist(Integer recordId) {
    def API_KEY = '0e3eaf66d37894088e6aa62dce22696904775759'
    def DOC_ID = 'rt3QMc825NvK9dQCtjHfZt'
    def TABLE = 'People'
    def BASE_URL = "https://docs.getgrist.com/api/docs/${DOC_ID}"
    println(BASE_URL)
    def client = new RESTClient(BASE_URL)
    client.headers['Authorization'] = "Bearer ${API_KEY}"

    try {
      def response = client.get(path: "/tables/${TABLE}/records/${recordId}")
      return response.data.record.fields?.FullName ?: "(No FullName)"
    } catch (Exception ex) {
      println "Error fetching record $recordId: ${ex.message}"
      return null
    }
  }
}
