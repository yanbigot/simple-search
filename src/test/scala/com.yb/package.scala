package com

import java.io.File

package object yb {
  val rootDirectory = "target/scala-2.12/test-classes/data"
  val currentTestDirectory = new File(System.getProperty("user.dir")).getAbsolutePath
  println(currentTestDirectory)

  val expectedFileList = Seq(
    s"$rootDirectory/kafka/metamorphosis.txt",
    s"$rootDirectory/thirdpartynotices.txt",
    s"$rootDirectory/shakespeare/shakespeare.txt",
    s"$rootDirectory/simple.txt"
  )
}
