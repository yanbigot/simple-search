package com.yb.converter

import com.yb.domain.Domain.DocumentScore

trait TResultFormatter{
  def format(fileScores: Seq[DocumentScore]): String
}

class TextResultFormatter extends TResultFormatter{

  def format(fileScores: Seq[DocumentScore]): String ={
    val stringBuffer = new StringBuffer()
    fileScores.foreach{
      file =>
        stringBuffer.append(s""" ${file.file.fileName} : ${file.score}% \n""")
        file.file.terms.foreach(
          term => stringBuffer.append((s"""      - ${term.term} => ${term.occurence} times \n""")
          )
        )
    }
    stringBuffer.toString
  }
}

class JsonResultFormatter extends TResultFormatter{

  def format(fileScores: Seq[DocumentScore]): String ={

    val hits = fileScores.map{
      file =>
        val jsonValidFileName = file.file.fileName.replaceAll("\\\\", "/")
        val fileHeader =
          s"""{
             | "fileName": "${jsonValidFileName}",
             | "score" : "${file.score}%",
             | "docId": "${file.file.docId}"
           """.stripMargin

        val hits = file.file.terms.map(
          term => s"""      { "term": "${term.term}", "occurence": ${term.occurence} }"""
        ).mkString(", \n")

        s"""$fileHeader, "terms": [
           | $hits
           | ]}""".stripMargin
    }
      .mkString("", ",\n", "\n")

    s"""{
       |"hits": [
       | $hits
       |]}""".stripMargin
  }
}
