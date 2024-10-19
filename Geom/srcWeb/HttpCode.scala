/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

class HttpCode(val num: Int, val text: String)
{
  def out: String = num.toString -- text
}

object HttpOK extends HttpCode(200, "OK")

object Http404 extends HttpCode(404, "Not Found")