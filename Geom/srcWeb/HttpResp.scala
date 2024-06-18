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

/** HTTP OK 404 Response with body. */
class HttpNotFound(val dateStr: String, val server: String, val contentType: HttpContentType, val body: String) extends HttpRespBodied
{
  override def code: Int = 404

  def conTypeLine: String = "Content-Type:" + contentType.out
  def connLine = "Connection: Keep-Alive"
  override def out: String = "HTTP/1.1 404 Not Found" --- dateLine --- connLine --- serverLine --- conLenLine --- conTypeLine ---- body
}

object HttpNotFound
{
  def apply(dateStr: String, server: String, contentType: HttpContentType, body: String): HttpNotFound = new HttpNotFound(dateStr, server, contentType, body)
}