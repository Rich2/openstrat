/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

/** A rectangular object aligned with the X and Y axes and centred on x = 0, y = 0. Sub traits include [[Rectangle]] and [[pCanv.Panel]]. */
trait RectCenlign extends Rectangularlign
{
  def cenX: Double = 0
  def cenY: Double = 0
  override def cen: Pt2 = Pt2Z
  def left: Double = - width / 2
  def right: Double = width / 2
  def top: Double = height / 2
  def bottom: Double = -height / 2
  def panelCen: Pt2 = Pt2(0, 0)
  def cenLeft: Pt2 = Pt2(left, 0)

  @inline final override def xTopLeft: Double = cenX - width / 2
  @inline final override def yTopLeft: Double = cenY + height / 2
  @inline final override def topLeft: Pt2 = Pt2(xTopLeft, yTopLeft)
  @inline final override def xTopRight: Double = cenX + width / 2
  @inline final override def yTopRight: Double = cenY + height / 2
  @inline final override def topRight: Pt2 = Pt2(xTopRight, yTopRight)
  @inline final override def xBottomRight: Double = cenX + width / 2
  @inline final override def yBottomRight: Double = cenY - height / 2
  @inline final override def bottomRight: Pt2 = Pt2(xBottomRight, yBottomRight)
  @inline final override def xBottomLeft: Double = cenX - width / 2
  @inline final override def yBottomLeft: Double = cenY - height / 2
  @inline final override def bottomLeft: Pt2 = Pt2(xBottomLeft, yBottomLeft)

  def crossHairs(lineWidth: Double = 1, lineColour: Colour = Black): LinesDraw = LineSegArr.fromDbls(
    left, 0, right, 0,
    0, top, 0, bottom).draw(lineWidth, lineColour)

  /** Not sure why spacing has got a minus sign */
  def gridLines(spacing: Double = 100, colour: Colour = Black, lineWidth: Double = 1.0): LinesDraw =
  { //val xl: List[Double] = doubleFromToOld(-spacing, left, - spacing) ::: 0.0.fromToOld(right, spacing)
    //val xlc: Line2s = xl.pMap(x => new Line2(x, bottom, x, top))
    val xlc: LineSegArr = ???
    //val yl: List[Double] = doubleFromToOld(-spacing, bottom, - spacing) ::: 0.0.fromToOld(top, spacing)
    //val ylc: Line2s = yl.pMap(y => new Line2(left, y, right, y))
    val ylc: LineSegArr = ???
    LinesDraw(xlc ++ ylc, lineWidth, colour)
  }
  
  /** Badly named I think, not sure why spacing has got a minus sign */  
  def gridLines2Colours(spacing: Double = 100, cenColour: Colour = Colour.DarkRed, otherColour: Colour = Black, lineWidth: Double = 1.0):
    RArr[LinesDraw] =
  { //val xl = doubleFromToOld(-spacing, left, - spacing) ::: spacing.fromToOld(right, spacing)
    //val xlc: Line2s = xl.pMap(x => new  Line2(x, bottom, x, top))
    val xlc: LineSegArr = ???
    //val yl = doubleFromToOld(-spacing, bottom, - spacing) ::: spacing.fromToOld(top, spacing)
    //val ylc: Line2s = yl.pMap(y => new Line2(left, y, right, y))
    val ylc: LineSegArr = ???
    RArr(LinesDraw(xlc ++ ylc, lineWidth, otherColour), crossHairs(1, cenColour))
  }
}