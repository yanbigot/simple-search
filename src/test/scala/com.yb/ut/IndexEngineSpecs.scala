package com.yb.ut

import java.io.File

import com.yb.SpecUtils.FilePathUtils
import com.yb.index.IndexEngine
import com.yb.rootDirectory
import com.yb.expectedFileList
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class IndexEngineSpecs extends FeatureSpec with GivenWhenThen  with Matchers{

  Feature("Index all the words in a set of documents with their number of occurences in each document") {

    Scenario("Index a simple document") {

      val fileName = "target\\scala-2.12\\test-classes\\data\\simple.txt"
      val love = "love"
      val expectedLoveCount = 3

      Given(s"""a file ${fileName}""")
        val file = new File(s"$rootDirectory/simple.txt")


      When(s"""indexing them""")
        val index = new IndexEngine(Seq(file)).build


      Then(s""" word $love should appear 2 times in $fileName document """)
        val wordIndex = index(love)
        val documentIndex = wordIndex.find(_.location.fileName == fileName)
        documentIndex.get.occurence shouldBe expectedLoveCount
    }

    Scenario("Index thirdpartynotices document") {

      val fileName = "target\\scala-2.12\\test-classes\\data\\thirdpartynotices.txt"
      val licensed = "licensed"
      val expectedLicensedCount = 2

      Given(s"""a file ${fileName}""")
        val file = new File(s"$rootDirectory/thirdpartynotices.txt")


      When(s"""indexing them""")
      val index = new IndexEngine(Seq(file)).build


      Then(s""" word $licensed should appear $expectedLicensedCount times in $fileName document """)
        val wordIndex = index(licensed)
        val documentIndex = wordIndex.find(_.location.fileName == fileName)
        documentIndex.get.occurence shouldBe expectedLicensedCount

    }

    Scenario("Find love & hate & ordnance count in shakespeare file ") {

      val shakespeareFileName = "target\\scala-2.12\\test-classes\\data\\shakespeare\\shakespeare.txt"
      val love = "love"
      val expectedLoveCount = 2198
      val hate = "hate"
      val expectedHateCount = 179
      val ordnance = "ordnance"
      val expectedOrdnanceCount = 7

      Given(s"""a file ${shakespeareFileName}""")
        val files = expectedFileList.map(new File(_))


      When(s"""indexing them""")
        val index = new IndexEngine(files).build


      Then(s"""word $ordnance should appear $expectedOrdnanceCount times in $shakespeareFileName document """)
        val ordnanceIndex = index(ordnance)
        val ordnanceDocumentIndex = ordnanceIndex.find(_.location.fileName == shakespeareFileName)
        ordnanceDocumentIndex.get.occurence shouldBe expectedOrdnanceCount

      And(s"""word $love should appear $expectedLoveCount times in $shakespeareFileName document """)
        val loveIndex = index(love)
        val loveDocumentIndex = loveIndex.find(_.location.fileName == shakespeareFileName)
        loveDocumentIndex.get.occurence shouldBe expectedLoveCount

      And(s"""word $hate should appear $expectedHateCount times in $shakespeareFileName document """)
        val hateIndex = index(hate)
        val hateDocumentIndex = hateIndex.find(_.location.fileName == shakespeareFileName)
        hateDocumentIndex.get.occurence shouldBe expectedHateCount
    }
  }
}