package com.yb.index

import com.yb.domain.Domain.DocumentTerm

trait TIndexEngine {
  def build: Map[String, Seq[DocumentTerm]]
}

