package com.yb.ut

import java.io.File

import com.yb.driver.LocalFileSystemDriver
import com.yb.{expectedFileList, rootDirectory}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import com.yb.SpecUtils.ForEachPrintln

class LocalFileSystemDriverSpecs  extends FeatureSpec with GivenWhenThen with Matchers{

  val localFileSystemDriver = new LocalFileSystemDriver

  Feature("List all the files recursively from a root directory"){

    Scenario("check if file is found"){

      Given(s""" $rootDirectory as root directory""")
        val expectedFileName      = "thirdpartynotices.txt"

      When("Listing all files")
        val fileList = localFileSystemDriver.fromRootDirectoryGetFileList(rootDirectory)

      Then(s"""The list contains a file with $expectedFileName filename""")
        fileList.map(_.getName) should contain(expectedFileName)
    }

    Scenario("Check subdirectory"){

      Given(s""" $rootDirectory as root directory""")
        val expectedFileName      = "thirdpartynotices.txt"

      When("Listing all files")
        val fileList = localFileSystemDriver.fromRootDirectoryGetFileList(rootDirectory)

      Then(s"""The list contains a file with $expectedFileName filename""")
        fileList.map(_.getName) should contain(expectedFileName)
    }

    Scenario("Check all files are present"){

      Given(s""" $rootDirectory as root directory""")
      val expectedFileName      = "thirdpartynotices.txt"

      When("Listing all files")
      val fileList = localFileSystemDriver.fromRootDirectoryGetFileList(rootDirectory)

      Then(s"""The list contains a file with $expectedFileName filename""")
      fileList.map(_.getName) should contain(expectedFileName)
    }

    Scenario("Manual test"){

      Given(s""" ? as root directory""")
      val dir      = "D:\\processors"
      new File("D:/").listFiles().toSeq.foreachPrintln

      When("Listing all files")
      val fileList = localFileSystemDriver.fromRootDirectoryGetFileList(dir)

      Then(s"""Nothing just printing""")
      println(s"count: [[ ${fileList.size} ]]")
      fileList.foreachPrintln
    }
  }
}
