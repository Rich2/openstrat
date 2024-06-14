/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait HttpResp
{
  def code: Int
  def out: String
  def server: String
  def serverLine: String = "server:" + server
}

/** Http Response with body */
class HttpRespBodied(val server: String, val contentType: HttpContentType, val body: String) extends HttpResp
{
  override def code: Int = 200
  def conLenLine: String = "ContentLength:" + body.length
  def conTypeLine: String = "ContentType:" + contentType.out

  override def out: String = "HTTP/1.1 200 OK" --- serverLine --- conLenLine --- conTypeLine ---- body
}

object HttpRespBodied
{
  def apply(server: String, contentType: HttpContentType, body: String): HttpRespBodied = new HttpRespBodied(server, contentType, body)
}

sealed trait HttpContentType
{ def str1: String
  def str2: String
  def out: String = str1 / str2
}

trait HttpContentTypeApp extends HttpContentType
{ override def str1: String = "application"
}

trait HttpContentTypeText extends HttpContentType
{ override def str1: String = "text"
}

object HttpConTypeHtml extends HttpContentTypeText
{ override def str2: String = "html"
}

object HttpConTypePlain extends HttpContentTypeText
{ override def str2: String = "plain"
}