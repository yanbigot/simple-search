package com.yb.search

import com.yb.domain.Domain.DocumentScore

trait TSearchEngine {
  def search(searchExpression: Seq[String]): Seq[DocumentScore]
}

