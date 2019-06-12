package com.yb.index

object Normalizer {

  def normalizeToken(token: String):Seq[String] =
    token.toLowerCase.replaceAll("""[^A-Za-z]+""", " ").trim.split(" ")

  def toSearchExpression(input: String): Seq[String]= {
    input.split(" ")
      .flatMap(Normalizer.normalizeToken)
      .filter(_.size > 0 )
  }
}
