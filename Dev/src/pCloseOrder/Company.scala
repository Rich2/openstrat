/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pCloseOrder
import geom._, Colour._

trait Company
{ var posn: Pt2
  def ranks: Int
  def rankLen: Int
  def colour: Colour
  def rankMiddle = (rankLen + 1) / 2.0
}

case class French(var posn: Pt2)  extends Company
{ val ranks = 3
  val rankLen = 30
  val colour = Blue
}

case class British(var posn: Pt2) extends Company
{ val ranks = 2
  val rankLen = 40
  val colour = Red
}

trait BScen
{ val lunits: RArr[Company]
}

object Nap1 extends BScen
{ val lunits = RArr(French(Pt2(0, - 200)), British(Pt2(0, 200)))
}