package com.yb.driver

import java.io.File
import java.nio.file.{Files, Paths}
import java.util.stream
import java.util.stream.Collectors

import scala.io.Source
import scala.util.{Failure, Success, Try}

class LocalFileSystemDriver extends TFileDriver {

  val uselessReturn = Seq.empty[File]

  def fromRootDirectoryGetFileList(path: String): Seq[File] = {
    recursiveListFiles(new File(path), Seq())
  }

  def recursiveListFiles(f: File, acc: Seq[File]): Seq[File] = {
    f.isDirectory match {
      case true => Try(f.listFiles().filter(_.canRead)) match{
        case Success(files) => files.flatMap(recursiveListFiles(_, acc))
        case Failure(e) =>  Seq.empty[File]
      }
      case false if authorizedExtension contains getFileExtension(f) =>
        acc :+ f
      case _    => uselessReturn
    }
  }

  def getFileExtension(file: File) = {
    file.getName.split("\\.").last
  }

  def getFileByLocation(location: String): String = {
    Source.fromFile(location).getLines().mkString("\n")
  }
}
