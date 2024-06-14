/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait HttpReq
{
  def method: HttpMethod
}

object HttpReq
{
  def apply(lines: Sequ[String]): EMon[HttpReq] =
  { val lh0 = lines(0)
    debvar(lh0)
    val rt = lh0.takeWhile(_.isLetter)
    val tail = lh0.drop(rt.length).dropWhile(_.isWhitespace)
    if (rt.toLowerCase == "get") Good(HttpReqGet(tail.takeWhile(c => !c.isWhitespace)))
    else {
      debvar(rt)
      badNone("Not get") }
  }
}

class HttpReqGet(val uri: String) extends HttpReq
{
  override def method: HttpMethod = HttpGet
}

sealed trait HttpMethod
object HttpGet extends HttpMethod