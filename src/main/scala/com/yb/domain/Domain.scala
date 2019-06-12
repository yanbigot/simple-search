package com.yb.domain

import java.io.File

import com.yb.index.{IndexEngine, Indexes}
import com.yb.search.SearchEngine
import com.yb.converter.TResultFormatter
import com.yb.driver.TDriver

object Domain {

  type Document = File

  //params and dependencies
  case class Context(searchEngine: SearchEngine, indexEngine: IndexEngine, indexes: Indexes, documentList: Seq[File], driver: TDriver)
  case class Arguments(formatter: TResultFormatter, interactive: Boolean, directory: String, maxResults: Int)

  //data structures
  case class DocumentTerm(location: String, term: String, occurence: Long, docId: Int)
  case class DocumentScore(file: DocumentResult, score: Float)
  case class DocumentResult(fileName: String, terms: Seq[TermOccurence], docId: Int)
  case class TermOccurence(term: String, occurence: Long)
  case class TermPosition(term: String, position: Long)
}
