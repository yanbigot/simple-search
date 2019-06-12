package com.yb.ut

import com.yb.index.{IndexEngine, Normalizer}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class IndexEngineFunctionsSpecs extends FeatureSpec with GivenWhenThen  with Matchers{

  val indexer = new IndexEngine(null)

  markup  {
    """
      Index engine basic test specifications
    """
  }

  Feature("Indexer functions tests"){

    Scenario("putAndIncrement test") {
      Given("""a map with key "k" """)
        val map = Map("k" -> 1.toLong, "j" -> 10.toLong)


      When("""adding key "k"""")
        val result = indexer.putAndIncrement(map, "k")


      Then("""result map key "k" should contains 2 """)
        result("k") shouldBe 2
      And("""result map key "j" should contains 10 """)
        result("j") shouldBe 10
    }

    Scenario("normalize test") {
      Given("""a list of string  """)
        val strings = Seq("word_1#-a", "stuff.")


      When(""" cleaning them """)
        val result = strings.flatMap(Normalizer.normalizeToken)


      Then("""should contains "word" "a" and "stuff """)
        result should contain allElementsOf Seq("word", "a", "stuff")
    }
  }
}
