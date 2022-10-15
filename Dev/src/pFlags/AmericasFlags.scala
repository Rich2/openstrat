/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pFlags
import geom._, Colour._

object UnitedStates extends Flag
{ val name = "United States"
  val ratio = 1.9

  /** Old Glory Red. */
  val oGRed = Colour(0xFFB22234)
  val usBlue = Colour(0xFF3C3B6E)

  /** star horizontal spacing */
  val starX = 1.9 * 2 / 5 / 12

  /** star vertical spacing */
  val starY = 1.0 * 7 / 13 / 10

  /** Diameter of star = four-fifths of the stripe width, Width of stripe = Height/13, Height = Width/1.9) */
  val starScale = 4.0 / 5 / 13 / 1.9

  val star0 = Star5().scale(starScale).fill(White)
  val star = star0.slateXY(-0.95, 0.5)

  def apply(): GraphicElems =
  { val blueFieldOld = Rect.tl(0.76, 7.0/ 13, -0.95 pp 0.5).fill(usBlue)
    val stars = ijToMap(0, 10, 2)(1, 9, 2) { (x, y) => star.slateXY(starX + x * starX, -y * starY) }
    val starsInner = ijToMap(2, 10, 2)(2, 8, 2) { (x, y) => star.slateXY(x * starX, -y * starY) }
    val stripes = topToBottomRepeat(13, oGRed, White)
    stripes +% blueFieldOld ++ stars ++ starsInner
  }
}

object Colombia extends Flag
{ val name = "Colombia"
  val ratio = 1.5
  def apply(): GraphicElems = topToBottom(Colour(0xFFFCD116), Colour(0xFFFCD116), Colour(0xFF003893), Colour(0xFFCE1126))
}

object Chile extends Flag
{ val name = "Chile"
  val ratio = 1.5
  def apply(): GraphicElems = RArr(
    Rect(ratio, 1).fill(White),
    Rect(ratio, 0.5, 0 pp -0.25).fill(Colour(0xFFD52B1E)),
    Rect(0.5, 0.5, -0.5 pp 0.25).fill(Colour(0xFF0039A6)),
    Star5().scale(0.125).slate(-0.5 pp 0.25).fill(White)
  )
}