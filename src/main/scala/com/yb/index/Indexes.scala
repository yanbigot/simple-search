package com.yb.index

import scala.collection.immutable.ListMap
import scala.util.{Failure, Success, Try}

class Indexes(indexer: TIndexEngine) {

  val terms          = indexer.build
  lazy val documents = this.buildDocumentIndex

  def show: String = {
    ListMap(terms.toSeq.sortWith(_._1 < _._1): _*).map {
      word => {
        val result = new StringBuffer()
        result.append(s" ${word._1}\n")
        result.append(s" found in ${word._2.size} files\n")
        result.append(word._2.map(x => s"""\t\t\t ${x.location}: ${x.occurence}""").mkString("\n"))
        result.toString
      }
    }.mkString("\n")
  }

  def buildDocumentIndex = {
    terms.values.flatten.map(t => (t.docId, t.location)).toMap
  }

  def getDocument(docId: String): Either[String, String] = {
    Try(docId.toInt) match {
      case Success(id)        =>
        Left(documents(id))
      case Failure(exception) =>
        Right("invalid document identifier")
    }
  }
}
