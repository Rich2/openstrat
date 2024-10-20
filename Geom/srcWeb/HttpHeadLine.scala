/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTTP Header line. Either the [[HttpStatusLine]] or an [[HttpHeader]] field. */
trait HttpHeadLine
{ /** The HTTP output [[String]]. */
  def out: String
}

/** |n HTTP response stus line including the HTTP version and the status code. */
class HttpStatusLine(val code: HttpCode, val version: HttpVersion = Http1p1) extends HttpHeadLine
{ override def out: String = version.out -- code.out
}

/** The HTTP version eg 1.1 or 2. */
class HttpVersion(val out: String)

/** HTTP version 1.1. */
object Http1p1 extends HttpVersion("HTTP/1.1")

/** HTTP version 2. */
object Http2 extends HttpVersion("HTTP/2")

/** HTTP version 3. */
object Http3 extends HttpVersion("HTTP/3")

/** HTTP header of header field. */
trait HttpHeader extends HttpHeadLine
{ /** The field name */
  def name: String
  
  /** The field value */
  def valueStr: String
  
  override def out: String = name + ": " + valueStr
}

class HttpConn(val valueStr: String) extends HttpHeader
{ override def name: String = "Connection"
}

object HttpAlive extends HttpConn("Keep-Alive")

case class HttpServer(valueStr: String) extends HttpHeader
{ override def name: String = "Server"
}

/** HTTP Response Content-Length header field. */
case class HttpConLen(length: Int) extends HttpHeader
{ override def name: String = "Content-Length"
  override def valueStr: String = length.toString
}

case class HttpDate(valueStr: String) extends HttpHeader
{ override def name: String = "Date"
}