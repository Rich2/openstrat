package ostrat
package pGrid

final class Roord private(val bLong: Long) extends AnyVal with ProdInt2
{ def y: Int = bLong.>>(32).toInt
  def c: Int = bLong.toInt
  def _1 = y
  def _2 = c
  def canEqual(a: Any) = a.isInstanceOf[Roord]
  override def toString: String = "Roord".appendSemicolons(y.toString, c.toString)
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
}

object Roord
{ def apply(y: Int, c: Int): Roord = new Roord(y.toLong.<<(32) | (c & 0xFFFFFFFFL))
  def fromLong(value: Long): Roord = new Roord(value)
}
