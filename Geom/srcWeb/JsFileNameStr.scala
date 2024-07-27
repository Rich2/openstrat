/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

object JsPathNameStr
{
  def unapply(inp: String): Option[String] = inp match
  { case s if s.length < 4 => None
    case s if s.takeRight(3) != ".js" => None
    case s if s.dropRight(3).last == '\\' => None
    case s => Some(s)
  }
}
