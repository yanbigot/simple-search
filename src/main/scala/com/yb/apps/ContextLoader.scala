package com.yb.apps

import com.yb.domain.Domain.{Arguments, Context}
import com.yb.driver.LocalFileSystemDriver
import com.yb.index.{IndexEngine, Indexes}
import com.yb.score.BasicScorer
import com.yb.search.SearchEngine

class ContextLoader {

  def build(arguments: Arguments): Context = {
    val driver       = new LocalFileSystemDriver()
    val documents    = driver.fromRootDirectoryGetFileList(arguments.directory)
    val indexEngine  = new IndexEngine(documents)
    val scorer       = new BasicScorer
    val index        = new Indexes(indexEngine)
    val searchEngine = new SearchEngine(index, scorer)

    println(s"""${documents.size} files read from ${arguments.directory}""")

    Context(searchEngine, indexEngine, index, documents, driver)
  }

}
