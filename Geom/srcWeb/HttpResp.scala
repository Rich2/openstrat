/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait HttpResp
{
  def code: Int
  def out: String
}

class HttpRespBody(val server: String, val body: String) extends HttpResp
{
  override def code: Int = 200

  override def out: String = "Http/1.1 200 OK"
}

sealed trait HttpContentType
{ def str1: String
  def str2: String
  def out: String = str1 / str2
}

trait HttpContentTypeApp extends HttpContentType
{
  override def str1: String = "application"
}

object HttpConTypeHtml extends HttpContentTypeApp
{ override def str2: String = "html"
}