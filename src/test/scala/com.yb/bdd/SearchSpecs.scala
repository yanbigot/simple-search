package com.yb.bdd

import com.yb.SpecUtils.JsonComparable
import com.yb.driver.LocalFileSystemDriver
import com.yb.index.{IndexEngine, Indexes, Normalizer}
import com.yb.score.BasicScorer
import com.yb.search.SearchEngine
import com.yb.converter.JsonResultFormatter
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import com.yb.rootDirectory


class SearchSpecs extends FeatureSpec with GivenWhenThen with Matchers{

    Feature("Search") {

      val documents    = new LocalFileSystemDriver().fromRootDirectoryGetFileList(rootDirectory)
      val indexEngine  = new IndexEngine(documents)
      val scorer       = new BasicScorer
      val index        = new Indexes(indexEngine)
      val searchEngine = new SearchEngine(index, scorer)

      Scenario("Searching for the term love in all documents"){

        val expectedJsonString = """{
                                   |  "hits": [
                                   |    {
                                   |      "fileName": "target/scala-2.12/test-classes/data/kafka/metamorphosis.txt",
                                   |      "score": "100.0%",
                                   |      "docId": "667382191",
                                   |      "terms": [
                                   |        {
                                   |          "term": "love",
                                   |          "occurence": 2
                                   |        }
                                   |      ]
                                   |    },
                                   |    {
                                   |      "fileName": "target/scala-2.12/test-classes/data/simple.txt",
                                   |      "score": "100.0%",
                                   |      "docId": "-421273662",
                                   |      "terms": [
                                   |        {
                                   |          "term": "love",
                                   |          "occurence": 3
                                   |        }
                                   |      ]
                                   |    },
                                   |    {
                                   |      "fileName": "target/scala-2.12/test-classes/data/shakespeare/shakespeare.txt",
                                   |      "score": "100.0%",
                                   |      "docId": "96459068",
                                   |      "terms": [
                                   |        {
                                   |          "term": "love",
                                   |          "occurence": 2198
                                   |        }
                                   |      ]
                                   |    }
                                   |  ]
                                   |}""".stripMargin

        Given("data as root directory")
          val searchExpression = Normalizer.toSearchExpression( "love" )
          val resultFormatter = new JsonResultFormatter

        When("searching for love and hate")
          val fileScores = searchEngine.search(searchExpression)

        Then(s"should be \n $expectedJsonString")
          val result = resultFormatter.format(fileScores)
          result.mkComparable shouldBe expectedJsonString.mkComparable
      }

      Scenario("Searching for love and hate terms in all documents"){

        val expectedJsonString = """{
                                   |  "hits": [
                                   |    {
                                   |      "fileName": "target/scala-2.12/test-classes/data/simple.txt",
                                   |      "score": "100.0%",
                                   |      "docId": "-421273662",
                                   |      "terms": [
                                   |        {
                                   |          "term": "love",
                                   |          "occurence": 3
                                   |        },
                                   |        {
                                   |          "term": "hate",
                                   |          "occurence": 2
                                   |        }
                                   |      ]
                                   |    },
                                   |    {
                                   |      "fileName": "target/scala-2.12/test-classes/data/shakespeare/shakespeare.txt",
                                   |      "score": "100.0%",
                                   |      "docId": "96459068",
                                   |      "terms": [
                                   |        {
                                   |          "term": "love",
                                   |          "occurence": 2198
                                   |        },
                                   |        {
                                   |          "term": "hate",
                                   |          "occurence": 179
                                   |        }
                                   |      ]
                                   |    },
                                   |    {
                                   |      "fileName": "target/scala-2.12/test-classes/data/kafka/metamorphosis.txt",
                                   |      "score": "50.0%",
                                   |      "docId": "667382191",
                                   |      "terms": [
                                   |        {
                                   |          "term": "love",
                                   |          "occurence": 2
                                   |        }
                                   |      ]
                                   |    }
                                   |  ]
                                   |}""".stripMargin

        Given("data as root directory")
          val searchExpression = Normalizer.toSearchExpression( "love hate" )
          val resultFormatter = new JsonResultFormatter

        When("searching for love and hate")
          val fileScores = searchEngine.search(searchExpression)

        Then(s"should be \n $expectedJsonString")
          val result = resultFormatter.format(fileScores)
          result.mkComparable shouldBe expectedJsonString.mkComparable
      }

      Scenario("Search results for 'love-cheers # and 4_real' should be equal to 'love cheers real'"){

        Given("data as root directory")
          val searchExprWithSpecialChars = Normalizer.toSearchExpression( "love-cheers # and 4_real" )
          val searchExprReference        = Normalizer.toSearchExpression( "love cheers and real" )
          val resultFormatter = new JsonResultFormatter

        When("searching for the 2 search expression 'love-cheers # and 4_real' , 'and love cheers real'")
          val fileScoresWithSpecialChars = searchEngine.search(searchExprWithSpecialChars)
          val fileScoresReference        = searchEngine.search(searchExprReference)

        Then("both result should be equals")
          val resultWithSpecialChars  = resultFormatter.format(fileScoresWithSpecialChars)
          val resultReference         = resultFormatter.format(fileScoresReference)
          resultWithSpecialChars.mkComparable shouldBe resultReference.mkComparable
      }

    }
}
