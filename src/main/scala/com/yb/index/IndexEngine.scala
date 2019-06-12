package com.yb.index

import java.io.{File, ObjectInputStream, RandomAccessFile}
import java.nio.channels.Channels

import com.yb.domain.Domain.{Document, DocumentTerm}

import scala.io.Source
import scala.util.{Failure, Success, Try}
import com.yb.index.IndexFunctions._
class IndexEngine(documents: Seq[Document]) extends TIndexEngine {

  def build: Map[String, Seq[DocumentTerm]] = {
    documents//.distinctByFileName
      .map(doc => parse(doc)).filterNot(_.isEmpty).flatten.groupBy(_.term)
  }

  def parse(doc: Document): Seq[DocumentTerm] = {
    //if a file is not readable, not throwing error
    Try {
      val tokens = Source.fromFile(doc).getLines()
        .flatMap(_.split(" "))
        .flatMap(Normalizer.normalizeToken)

      val result = tokens
        .foldLeft(Map.empty[String, Long]) {
          (resultMap, term) => putAndIncrement(resultMap, term)}
        .map(termOcc =>
          {
            DocumentTerm(mkLocation(doc), termOcc.term, termOcc.occurence, mkId(doc))
          })
        .toSeq

      result
    } match {
      case Success(r) => r
      case Failure(exception) =>
        println(s"error while parsing ${doc.getAbsolutePath}")
        Seq()
    }
  }

  def putAndIncrement(resultMap: Map[String, Long], term: String): Map[String, Long] =  {
      resultMap + (term -> (resultMap.getOrElse(term, 0.toLong) + 1.toLong))
    }
}