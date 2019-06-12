package com.yb.bdd

import com.yb.Application
import com.yb.converter.{JsonResultFormatter, TextResultFormatter}
import com.yb.domain.Domain.Arguments
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class ApplicationArgumentParserSpecs  extends FeatureSpec with GivenWhenThen with Matchers{

  Feature("Argument parser validation"){
    Scenario("""some valid arguments """){
      val expectedArguments = Arguments(
        formatter = new TextResultFormatter(),
        directory = "target/scala-2.12/test-classes/data",
        maxResults = 100,
        interactive = true
      )
      Given(""" "-directory data -interactive t -max_results 100 -format txt" """)
        val args = Array(
          "-interactive",
          "-directory", "target/scala-2.12/test-classes/data",
          "-max_results", "100",
          "-format", "txt" )


      When("parsing and defaulting arguments")
        val arguments = Application.parseArguments(args)


      Then("format should be text ")
        arguments.formatter.getClass.getName shouldBe expectedArguments.formatter.getClass.getName
      And("directory should be data ")
        arguments.directory shouldBe expectedArguments.directory
      And("interactrive should be true")
        arguments.interactive shouldBe expectedArguments.interactive
      And("maxResults should be 100")
        arguments.maxResults shouldBe expectedArguments.maxResults
    }

    Scenario("""some incorrect arguments will be properly defaulted"""){
      val expectedArguments = Arguments(
        formatter = new JsonResultFormatter,
        directory = ".",
        maxResults = 10,
        interactive = false
      )
      Given(""" "-directory baddire?/$false -max_results yes -format z" """)
      val args = Array(
        "-directory", "baddire?/$false",
        "-max_results", "yes",
        "-format", "z" )


      When("parsing and defaulting arguments")
      val arguments = Application.parseArguments(args)


      Then("format should be json ")
        arguments.formatter.getClass.getName shouldBe expectedArguments.formatter.getClass.getName
      And("directory should be . ")
        arguments.directory shouldBe expectedArguments.directory
      And("interactrive should be false")
        arguments.interactive shouldBe expectedArguments.interactive
      And("maxResults should be 10")
        arguments.maxResults shouldBe expectedArguments.maxResults
    }

    Scenario("""empty arguments will be properly defaulted"""){
      val expectedArguments = Arguments(
        formatter = new JsonResultFormatter,
        directory = ".",
        maxResults = 10,
        interactive = false
      )
      Given("""no arguments at all """)
      val args = Array("")


      When("parsing and defaulting arguments")
      val arguments = Application.parseArguments(args)


      Then("format should be json ")
        arguments.formatter.getClass.getName shouldBe expectedArguments.formatter.getClass.getName
      And("directory should be . ")
        arguments.directory shouldBe expectedArguments.directory
      And("interactrive should be false")
        arguments.interactive shouldBe expectedArguments.interactive
      And("maxResults should be 10")
        arguments.maxResults shouldBe expectedArguments.maxResults
    }
  }
}
