/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Am HttP Response. The out method givees the [[String]] to send over TCP. */
trait HttpResp
{
  def code: Int
  def out: String
  def server: String
  def serverLine: String = "server:" + server
  def dateStr: String
  def dateLine: String = "date:" + dateStr
}

trait HttpRespBodied extends HttpResp
{
  def contentType: HttpContentType
  def body: String
  def conLenLine: String = "Content-Length:" + body.length
}

/** HTTP OK 200 Response with body. */
class HttpFound(val dateStr: String, val server: String, val contentType: HttpContentType, val body: String) extends HttpRespBodied
{
  override def code: Int = 200

  def conTypeLine: String = "Content-Type:" + contentType.out
  def connLine = "Connection: Keep-Alive"
  override def out: String = "HTTP/1.1 200 OK" --- dateLine --- connLine --- serverLine --- conLenLine --- conTypeLine ---- body
}

object HttpFound
{
  def apply(dateStr: String, server: String, contentType: HttpContentType, body: String): HttpFound = new HttpFound(dateStr, server, contentType, body)
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

object HttpConTypeCss extends HttpContentTypeText {
  override def str2: String = "css"
}

object HttpConTypePlain extends HttpContentTypeText
{ override def str2: String = "plain"
}

trait HttpContentTypeImage extends HttpContentType
{ override def str1: String = "image"
}

object HttpConTypeSvg extends HttpContentTypeImage
{ override def str2: String = "svg+xml"
}