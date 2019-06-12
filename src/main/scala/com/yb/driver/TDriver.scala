package com.yb.driver

trait TDriver{
  def getFileByLocation(location: String): String
}

trait TFileDriver extends TDriver {
  val authorizedExtension = Seq("txt")
}