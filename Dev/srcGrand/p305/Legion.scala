/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305

class Legion(val polity: Polity, num: Int)
{ val colour = polity.colour
  override def toString = "Legion" + (polity.toString -- num.toString).enParenth
  override def equals(other: Any): Boolean = other match
  { case that: Legion => polity == that.polity
    case _ => false
  }
}

object Legion
{ def apply(polity: Polity, num: Int): Legion = new Legion(polity, num)
}