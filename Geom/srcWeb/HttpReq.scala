/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait HttpReq
{
  def method: HttpMethod
}

object HttpReq
{

}

class HttpReqGet(val uri: String) extends HttpReq
{
  override def method: HttpMethod = HttpGet
}

sealed trait HttpMethod
object HttpGet extends HttpMethod