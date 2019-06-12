package com.yb

import java.io.File

import com.yb.apps.ConsoleSearchApplication
import com.yb.converter._
import com.yb.domain.Domain.Arguments

import scala.util.{Failure, Success, Try}


object Application{

  val DEFAULT_MAX_RESULTS = 10

  def main(args: Array[String]): Unit = {
    new ConsoleSearchApplication(parseArguments(args)).start
  }

  def parseArguments(arguments: Array[String]): Arguments = {

    val interactive = arguments.indexOf("-interactive") match{
      case idx: Int if idx == -1 => false
      case idx: Int if idx > -1  => true
    }

    val directory = arguments.indexOf("-directory") match{
      case idx: Int if idx == -1 =>
        println(s""" no "-directory" argument, defaulting to current directory """)
        "."
      case idx: Int if idx > -1  =>
        val argDir = arguments(idx + 1)
        new java.io.File(argDir).exists match {
          case true  => argDir
          case false => println(s""" $argDir directory does not exists, defaulting to current directory ${new File(".").getAbsolutePath}""")
            "."
        }
    }

    val formatter = arguments.indexOf("-format") match{
      case idx: Int if idx == -1 => new JsonResultFormatter
      case idx: Int if idx > -1  => arguments(idx + 1) match {
        case "json" => new JsonResultFormatter
        case "txt" => new TextResultFormatter
        case _ => println(s"format should be either json or txt, defaulting to json result format")
          new JsonResultFormatter
      }
    }

    val maxResults = arguments.indexOf("-max_results") match{
      case idx: Int if idx == -1 => DEFAULT_MAX_RESULTS
      case idx: Int if idx > -1  =>
        Try(arguments(idx + 1).toInt) match {
          case Success(value) => value
          case Failure(exception) =>
            println(s"max_result should be a valid integer, defaulting to $DEFAULT_MAX_RESULTS")
            DEFAULT_MAX_RESULTS
        }
    }

    Arguments(formatter, interactive, directory, maxResults)
  }
}

