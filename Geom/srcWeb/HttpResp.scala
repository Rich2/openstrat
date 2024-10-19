/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTTP Response. The out method gives the [[String]] to send over TCP. */
trait HttpResp
{ /** The HTTP version. */
  def version: HttpVersion = Http1p1
  
  /** HTTP response code. */
  def code: HttpCode
  
  def top: HttpStatusLine = HttpStatusLine(code, version)
  
  /** The [[String]] output of this HTTP response. */
  def out: String
  
  def server: String
  
  /** The server line [[String]] of this HTTP response. */
  def serverLine: String = "server:" + server
  
  def dateStr: String
  def dateLine: String = "date:" + dateStr
  def headerStr: String
  def headerOneLine: String = headerStr.oneLine
}

trait HttpRespBodied extends HttpResp
{
  def contentType: HttpContentType
  def body: String
  def conLenLine: String = "Content-Length:" + body.length

  final override def out: String = headerStr ---- body
}

/** HTTP OK 200 Response with body. */
class HttpFound(val dateStr: String, val server: String, val contentType: HttpContentType, val body: String) extends HttpRespBodied
{ /** HTTP OK 200 response. */
  override def code: HttpCode = HttpOK

//  def conTypeLine: String = "Content-Type:" + contentType.out
  def connLine = "Connection: Keep-Alive"
  override def headerStr: String = top.out --- dateLine --- connLine --- serverLine --- conLenLine --- contentType.out
}

object HttpFound
{ /** Factory apply method to produce HTTP OK 200 Response with body. */
  def apply(dateStr: String, server: String, contentType: HttpContentType, body: String): HttpFound = new HttpFound(dateStr, server, contentType, body)
}

/** HTTP OK 404 Response with body. */
class HttpPageNotFound(val dateStr: String, val server: String, val contentType: HttpContentType, val body: String) extends HttpRespBodied
{ override def code = Http404
  
  def connectionLine: String = "Connection: Keep-Alive"
  override def headerStr: String = "HTTP/1.1" -- Http404.out --- dateLine --- connectionLine --- serverLine --- conLenLine --- contentType.out
}

object HttpPageNotFound
{
  def apply(dateStr: String, server: String, contentType: HttpContentType, body: String): HttpPageNotFound = new HttpPageNotFound(dateStr, server, contentType, body)
}