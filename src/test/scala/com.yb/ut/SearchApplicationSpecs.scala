package com.yb.ut

import com.yb.Application
import com.yb.apps.SearchApplication
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class SearchApplicationSpecs  extends FeatureSpec with GivenWhenThen with Matchers{

  Feature("search application feature - no coupling to the console"){
    Scenario("Help feature - no time to make others :)"){
      val expected =             s"""Quick help
                                    |:quit  -> exit
                                    |:list  -> list all files from the directory
                                    |:index -> show the whole index
                                    |:doc   -> fetch document
                                    |:dir   -> the root directory
                                    |""".stripMargin

      Given("Defaulted arguments")
        val arguments = Application.parseArguments(Array(""))
        val searchApp = new SearchApplication(arguments)

      When("asking for help")
        val helpMessage = searchApp.helpFeature()

      Then(s"should show $expected")
      helpMessage shouldBe expected
    }
  }
}
