package com.yb.apps

import java.util.Scanner

import com.yb.domain.Domain.Arguments

class ConsoleSearchApplication(arguments: Arguments) extends SearchApplication(arguments) {

  def start: Unit ={

    val keyboard = new Scanner(System.in)
    println("Welcome to a simple search engine")
    print("search>")
    while(keyboard.hasNext) {
      keyboard.nextLine() match {
        case ":quit"   => System.exit(0)
        case ":help"   => println(this.helpFeature)
        case ":list"   => println(this.listIndexedDocuments)
        case ":index"  => println(this.showIndexFeature)
        case ":dir"    => println(this.rootDirectory)
        case ":doc"    =>
          println("doc_id>")
          println(this.fetchDocumentFeature(keyboard.nextLine()))
        case search@_ => println(this.searchFeature(search))
      }
      print("search>")
    }
  }
}
