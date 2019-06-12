package com.yb.search

import com.yb.domain.Domain
import com.yb.index.IndexFunctions
import com.yb.domain.Domain.{DocumentResult, DocumentScore, DocumentTerm, TermOccurence}
import com.yb.index.Indexes
import com.yb.score.TScorer

class SearchEngine(index: Indexes, scorer: TScorer) extends TSearchEngine {//TODO: inject scorer

  /*
  from:
   word ->
      document_1 -> word_a, occurence, location
      document_n -> word_z, occurence, location

  to:
    document_1 ->
      word_a -> occurence
      word_z -> occurence
    document_n ->
      word_a -> occurence
      word_z -> occurence

   */
  def search(searchExpression: Seq[String]): Seq[DocumentScore] ={

    val matchedTerms: Seq[Domain.DocumentTerm] = searchExpression
      .filter(index.terms contains)
      .flatMap(index.terms(_))

    val relevantFiles = matchedTerms
          .groupBy(_.location)
          .map((document: (String, Seq[DocumentTerm])) => DocumentResult(
              docId = IndexFunctions.mkId(document._1),
              fileName = document._1,
              terms = document._2.map(x => TermOccurence(x.term, x.occurence))
            )
          ).toSeq

    val fileScores = relevantFiles.map(file => DocumentScore(file, scorer.score(file.terms, searchExpression)) )

    fileScores.sortBy(_.score).reverse
  }
}