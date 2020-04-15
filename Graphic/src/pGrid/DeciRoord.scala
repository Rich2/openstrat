package ostrat
package pGrid

class DeciRoord private(val bLong: Long) extends AnyVal with ProdInt2
{ def yd: Int = bLong.>>(32).toInt
  def cd: Int = bLong.toInt
  def _1 = yd
  def _2 = cd
  def canEqual(a: Any) = a.isInstanceOf[DeciRoord]
  @inline def yStr: String = (yd.toDouble / 10).toString
  @inline def cStr: String = (cd.toDouble / 10).toString
  override def toString: String = "DeciRo".appendSemicolons(yStr, cStr)
  def ycStr: String = yStr.appendCommas(cStr)
}

object DeciRoord
{
  def apply(yIn: Double, cIn: Double): DeciRoord =
  {
    val yd = (yIn * 10).toInt
    val cd = (cIn * 10).toInt
    new DeciRoord(yd.toLong.<<(32) | (cd & 0xFFFFFFFFL))
  }
}