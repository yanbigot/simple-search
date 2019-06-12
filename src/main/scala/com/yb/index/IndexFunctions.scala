package com.yb.index

import java.io.File
import com.yb.domain.Domain.Document

object IndexFunctions {

  val currentDirectoryAbsolutePath = new File(System.getProperty("user.dir")).getAbsolutePath.replaceAll("\\\\", "/")

  def mkId(f: File):Int = {
    mkLocation(f.getAbsolutePath).hashCode
  }

  def mkId(path: String):Int  = {
    path.hashCode()
  }

  def mkLocation(f: File): String = {
    mkLocation(f.getAbsolutePath)
  }

  def mkLocation(path: String): String  = {
    import java.nio.file.Paths
    val pathAbsolute = Paths.get(path)
    val pathBase = Paths.get(currentDirectoryAbsolutePath)
    val pathRelative = pathBase.relativize(pathAbsolute)

    pathRelative.toString
  }


  implicit class FileDistinct(val documents: Seq[Document]) {

    def distinctByFileName = {
      documents.groupBy(_.getName).flatten(_._2.take(1)).toSeq
    }
  }

  implicit class TermOccurenceUtils(val termOcc: (String, Long)) {

    def term = {
      termOcc._1
    }

    def occurence = {
      termOcc._2
    }
  }

}
