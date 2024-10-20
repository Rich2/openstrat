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
  
  def dateStr: String
  def dateLine: HttpDate = HttpDate(dateStr)
  def headerStr: String
  def headerOneLine: String = headerStr.oneLine

  def serverStr: String
  def serverLine: HttpServer = HttpServer(serverStr)
}

trait HttpRespBodied extends HttpResp
{
  def contentType: HttpContentType
  def body: String
  def conLen: HttpConLen = HttpConLen(body.length)

  final override def out: String = headerStr ---- body
}

/** HTTP OK 200 Response with body. */
class HttpFound(val dateStr: String, val serverStr: String, val contentType: HttpContentType, val body: String) extends HttpRespBodied
{ /** HTTP OK 200 response. */
  override def code: HttpCode = HttpOK
  
  override def headerStr: String = top.out --- dateLine.out --- HttpAlive.out --- serverLine.out --- conLen.out --- contentType.out
}

object HttpFound
{ /** Factory apply method to produce HTTP OK 200 Response with body. */
  def apply(dateStr: String, server: String, contentType: HttpContentType, body: String): HttpFound = new HttpFound(dateStr, server, contentType, body)
}

/** HTTP OK 404 Response with body. */
class HttpPageNotFound(val dateStr: String, val serverStr: String, val contentType: HttpContentType, val body: String) extends HttpRespBodied
{ override def code = Http404
  
  override def headerStr: String = top.out --- dateLine.out --- HttpAlive.out --- serverLine.out --- conLen.out --- contentType.out
}

object HttpPageNotFound
{
  def apply(dateStr: String, server: String, contentType: HttpContentType, body: String): HttpPageNotFound = new HttpPageNotFound(dateStr, server, contentType, body)
}