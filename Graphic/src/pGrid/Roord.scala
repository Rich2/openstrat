package ostrat
package pGrid
import geom._

final class Roord private(val bLong: Long) extends AnyVal with ProdInt2
{ def y: Int = bLong.>>(32).toInt
  def c: Int = bLong.toInt
  def _1 = y
  def _2 = c
  def canEqual(a: Any) = a.isInstanceOf[Roord]
  override def toString: String = "Roord".appendSemicolons(y.toString, c.toString)
  def ycStr: String = y.str.appendCommas(c.str)
  def + (operand: Roord): Roord = Roord(y + operand.y, c + operand.c)
  def -(operand: Roord): Roord = Roord(y - operand.y, c - operand.c)
  def *(operand: Int): Roord = Roord(y * operand, c * operand)
  def /(operand: Int): Roord = Roord(y / operand, c / operand)

  def addYC(yOff: Int, cOff: Int): Roord = Roord(y + yOff, c + cOff)
  def subYC(yOff: Int, cOff: Int): Roord = Roord(y - yOff, c - cOff)
  def addY(operand: Int): Roord = Roord(y + operand, c)
  def addC(operand: Int): Roord = Roord(y, c + operand)
  def subY(operand: Int): Roord = Roord(y - operand, c)
  def subC(operand: Int): Roord = Roord(y, c - operand)

  def toHexTile: HexTile = HexTile(y, c)
  def tilePoly(implicit tileGrid: TileGrid): Polygon = tileGrid.roordToPolygon(this)
  def gridVec2(implicit tileGrid: TileGrid): Vec2 = tileGrid.roordToVec2(this)
  def andStep(st: HTStep): HTileAndStep = HTileAndStep(y, c, st)
  def step(st: HTStep): Roord = this + st.roord
  def stepBack(st: HTStep): Roord = this - st.roord
}

object Roord
{ def apply(y: Int, c: Int): Roord = new Roord(y.toLong.<<(32) | (c & 0xFFFFFFFFL))
  def fromLong(value: Long): Roord = new Roord(value)
  def unapply(rd: Roord): Option[(Int, Int)] = Some((rd.y, rd.c))
  implicit object persistImplicit extends PersistInt2[Roord]("Rood", "y", _.y, "c", _.c, apply)

  implicit val roordsBuildImplicit = new ArrProdInt2Build[Roord, Roords]
  { type BuffT = RoordBuff
    override def fromIntArray(array: Array[Int]): Roords = new Roords(array)
    override def fromIntBuffer(inp: Buff[Int]): RoordBuff = new RoordBuff(inp)
  }
}