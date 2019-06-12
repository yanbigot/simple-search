package com.yb.apps

import java.time.LocalTime
import java.time.temporal.ChronoUnit

import com.yb.domain.Domain.Arguments
import com.yb.index.Normalizer

class SearchApplication(arguments: Arguments) extends ContextLoader{

  val ctx = build(arguments)

  def helpFeature(): String ={
      s"""Quick help
         |:quit  -> exit
         |:list  -> list all files from the directory
         |:index -> show the whole index
         |:doc   -> fetch document
         |:dir   -> the root directory
         |""".stripMargin
  }

  def rootDirectory(): String = {
    arguments.directory
  }

  def listIndexedDocuments(): String ={
    ctx.documentList.mkString("\n")
  }

  def showIndexFeature(): String ={
    ctx.indexes.show.toString
  }

  def fetchDocumentFeature(documentId: String): String ={
    ctx.indexes.getDocument(documentId) match {
      case Left(location)   => ctx.driver.getFileByLocation(location)
      case Right(exception) => exception
    }
  }

  def searchFeature(search: String): String ={
    val startTime = LocalTime.now()
    val searchExpr = Normalizer.toSearchExpression(search)
    val searchResults = ctx.searchEngine.search(searchExpr).take(arguments.maxResults)
    searchResults.isEmpty match {
      case true => s"""no result found for "$search" """
      case false =>
        val elapsed = ChronoUnit.NANOS.between(startTime, LocalTime.now())
        s"""result found in $elapsed nanos
           |${arguments.formatter.format(searchResults)}
         """.stripMargin
    }
  }

}
