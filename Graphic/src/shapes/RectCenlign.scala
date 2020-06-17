/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** A rectangle aligned with the X and Y axes and centred on x = 0, y = 0. */
trait RectCenlign extends Rectlign
{ def xCen: Double = 0
  def yCen: Double = 0
  //override def cen: Vec2 = Vec2Z
  def left = - width / 2
  def right = width / 2
  def top = height / 2
  def bottom = - height / 2
  def panelCen: Vec2 = Vec2(0, 0)
  def cenLeft = Vec2(left, 0)
  def crossHairs(lineWidth: Double = 1, lineColour: Colour = Black): LinesDraw = Line2s.doubles(
    left, 0, right, 0,
    0, top, 0, bottom).draw(lineWidth, lineColour)

  /** Not sure why spacing has got a minus sign */
  def gridLines(spacing: Double = 100, colour: Colour = Black, lineWidth: Double = 1.0): LinesDraw =
  { //val xl: List[Double] = doubleFromToOld(-spacing, left, - spacing) ::: 0.0.fromToOld(right, spacing)
    //val xlc: Line2s = xl.pMap(x => new Line2(x, bottom, x, top))
    val xlc: Line2s = ???
    //val yl: List[Double] = doubleFromToOld(-spacing, bottom, - spacing) ::: 0.0.fromToOld(top, spacing)
    //val ylc: Line2s = yl.pMap(y => new Line2(left, y, right, y))
    val ylc: Line2s = ???
    LinesDraw(xlc ++ ylc, lineWidth, colour)
  }
  
  /** Badly named I think, not sure why spacing has got a minus sign */  
  def gridLines2Colours(spacing: Double = 100, cenColour: Colour = Colour.DarkRed, otherColour: Colour = Black, lineWidth: Double = 1.0):
    Arr[LinesDraw] =
  { //val xl = doubleFromToOld(-spacing, left, - spacing) ::: spacing.fromToOld(right, spacing)
    //val xlc: Line2s = xl.pMap(x => new  Line2(x, bottom, x, top))
    val xlc: Line2s = ???
    //val yl = doubleFromToOld(-spacing, bottom, - spacing) ::: spacing.fromToOld(top, spacing)
    //val ylc: Line2s = yl.pMap(y => new Line2(left, y, right, y))
    val ylc: Line2s = ???
    Arr(LinesDraw(xlc ++ ylc, lineWidth, otherColour), crossHairs(1, cenColour))
  }
}