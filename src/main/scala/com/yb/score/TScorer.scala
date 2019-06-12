package com.yb.score

import com.yb.domain.Domain.TermOccurence

trait TScorer{
  def score(wordIndexes: Seq[TermOccurence], searchExpression: Seq[String]): Float
}
