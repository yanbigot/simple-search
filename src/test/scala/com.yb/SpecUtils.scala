package com.yb

object SpecUtils{
  implicit class JsonComparable(val jsonString: String){
    //because no json library are allowed :), very limited as order is relevant in the comparison
    def mkComparable = jsonString.replaceAll("""[\s\r\n]""", "")
  }
  implicit class FilePathUtils(val absolutePath: String){
    //because no json library are allowed :), very limited as order is relevant in the comparison
    def fileName = absolutePath.split("/").last
  }
  implicit class ForEachPrintln(val os: Iterable[Any]){
    //because no json library are allowed :), very limited as order is relevant in the comparison
    def foreachPrintln = os.foreach(println)
  }
}