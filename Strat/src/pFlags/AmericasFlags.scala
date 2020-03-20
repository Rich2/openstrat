package ostrat
package pFlags
import geom._, Colour._

object UnitedStates extends Flag
{ val name = "United States"
  val ratio = 1.9

  /** Old Glory Red */
  val oGRed = Colour(0xFFB22234)
  val usBlue = Colour(0xFF3C3B6E)

  /** star horizontal spacing */
  val starX = 1.9 * 2 / 5 / 12

  /** star vertical spacing */
  val starY = 1.0 * 7 / 13 / 10

  /** Diameter of star = four-fifths of the stripe width, Width of stripe = Height/13, Height = Width/1.9) */
  val starScaleOld = 4.0 / 5 / 13 / 1.9

  val star0 = Star5().scale(starScaleOld).fill(White)
  val star = star0.slate(-0.95, 0.5)
  val dimC = 7.0 / 13
  val dimD = ratio * 0.4
  val urRect = Rectangle(dimD / dimC).fill(usBlue)
  import pGrid._
  val g = RectAltGrid(2, 22, 2, 18, dimD / dimC)
  val starScale = 0.8 / 13
  val whiteStar = Star5().scale(starScale).fill(White)
  val ss = g.tilesAllVecMap(dimC ){v => whiteStar.slate(v)}
  debvar(ss.length)
  val inset = urRect +: ss

  val apply =
  { val blueFieldOld = Rectangle.fromTL(0.76, 7.0/ 13, -0.95 vv 0.5).fill(usBlue)
    val stars = xyToMap(0, 10, 2)(1, 9, 2) { (x, y) => star.slate(starX + x * starX, -y * starY) }
    val starsInner = xyToMap(2, 10, 2)(2, 8, 2) { (x, y) => star.slate(x * starX, -y * starY) }
    val stripes = topToBottomRepeat(13, oGRed, White)
    stripes +- blueFieldOld ++ stars ++ starsInner
  }
  import pGrid._
  def starsNow = RectAltGrid(2, 12, 2, 18, 0.2)
}