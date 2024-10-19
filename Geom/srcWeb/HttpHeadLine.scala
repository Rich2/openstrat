/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTTP Heade */
trait HttpHeadLine
{
  def out: String
}

/** |n HTTP response stus line including the HTTP version and the status code. */
class HttpStatusLine(val code: HttpCode, val version: HttpVersion = Http1p1) extends HttpHeadLine
{ override def out: String = version.out -- code.out
}


class HttpVersion(val out: String)

/** HTTP version 1.1. */
object Http1p1 extends HttpVersion("HTTP/1.1")

/** HTTP version 2. */
object Http2 extends HttpVersion("HTTP/2")

/** HTTP version 3. */
object Http3 extends HttpVersion("HTTP/3")

/** HTTP header of header field. */
trait HttpHeader
{
  def name: String
  def valueStr: String
  
  def out: String = name + ": " + valueStr
}

class HttpConn(val valueStr: String) extends HttpHeader
{
  override def name: String = "Connection"
}

object HttpAlive extends HttpConn("Keep-Alive")