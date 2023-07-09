/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305

case class Legion(polity: Polity, num: Int)
{ val colour = polity.colour
  override def toString = "Legion" + (polity.toString -- num.toString).enParenth
}
