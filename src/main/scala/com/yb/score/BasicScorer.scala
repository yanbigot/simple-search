package com.yb.score

import com.yb.domain.Domain.TermOccurence

class BasicScorer extends TScorer {

  def score(matchedTerms: Seq[TermOccurence], searchExpression: Seq[String]): Float ={
    val searchExpressionCount = searchExpression.size.toFloat
    val foundWordsInDocument = matchedTerms.size.toFloat
    (foundWordsInDocument / searchExpressionCount) * 100.00 toFloat
  }
}

