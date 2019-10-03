package ostrat
package pGrid
import geom. _

trait NCood
{ type CoodT <: NCood
  @inline def nCood(x: Int, y: Int): CoodT
  def x: Int
  def y : Int
  @inline final def + (operand: CoodT): CoodT = nCood(x + operand.x, y + operand.y)
  @inline final def - (operand: CoodT): CoodT = nCood(x - operand.x, y - operand.y)
  @inline final def * (operand: Int): CoodT = nCood(x * operand, y * operand)
}

/** Hexagonal grid coordinate, assigning values for the tile sides and vertices. */
final case class HCood(x: Int, y: Int) extends NCood
{ type CoodT = HCood
  @inline override def nCood(x: Int, y: Int): CoodT = HCood.apply(x, y)

}

/** Square Grid coordinate, assigning values for the tile sides and vertices. */
final case class SCood(x: Int, y: Int) extends NCood
{ type CoodT = SCood
  @inline override def nCood(x: Int, y: Int): CoodT = SCood.apply(x, y)
}

/** Simple Square Grid Coordinate. */
final case class SSCood(x: Int, y: Int) extends NCood
{ type CoodT = SSCood
  @inline override def nCood(x: Int, y: Int): CoodT = SSCood.apply(x, y)
  def oddSum: Boolean = (x + y).isOdd
  def evenSum: Boolean = (x + y).isEven
  def fOddSum[A](ifOdd: A, ifEven: A): A = ife(oddSum, ifOdd, ifEven)
  def fEvenSum[A](ifEven: A, ifOdd: A): A = ife(evenSum, ifEven, ifOdd)
}

trait FSSCood[R] extends Function1[SSCood, R]
{
  def xy(x: Int, y: Int): R = apply(SSCood(x, y))
}

case class SSGridRange(xMax: Int, yMax: Int)
{
  def fDisp(f: FSSCood[Colour], scale: Double): Arr[PaintElem] = ijToMap(1, xMax)(1, yMax) { (x, y) =>
    val c: Colour = f.xy(x, y)
    Square.fillXY(scale, c, x * scale, y * scale)

  }
}