/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCloseOrder
import Colour._, geom._

trait Company
{ var posn: Vec2
  def ranks: Int
  def rankLen: Int
  def colour: Colour
  def rankMiddle = (rankLen + 1) / 2.0
}

case class French(var posn: Vec2)  extends Company
{ val ranks = 3
  val rankLen = 30
  val colour = Blue
}

case class British(var posn: Vec2) extends Company
{ val ranks = 2
  val rankLen = 40
  val colour = Red
}

trait BScen
{ val lunits: Arr[Company]
}

object Nap1 extends BScen
{ val lunits = Arr(French(Vec2(0, - 200)), British(Vec2(0, 200)))
}