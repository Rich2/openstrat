/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A 2d line defined by its start and end Tile Cood. */
case class CoodLine(x1: Int, y1: Int, x2: Int, y2: Int) extends ProdI4
{
  def _1 = x1
  def _2 = y1
  def _3 = x2
  def _4 = y2
   def toLine2(f: Cood => Vec2): Line2 =
   {  val v1 = f(Cood(x1, y1))
      val v2 = f(Cood(x2, y2))
      Line2(v1, v2)
   }
}

object CoodLine
{ def apply(c1: Cood, c2: Cood): CoodLine = CoodLine(c1.x, c1.y, c2.x, c2.y)
}

/** An Array[Int] based collection for CoodLines. */
class CoodLines(val array: Array[Int]) extends AnyVal with ProductI4s[CoodLine]
{ override def typeStr: String = "CoodLines"
  override def newElem(i1: Int, i2: Int, i3: Int, i4: Int): CoodLine = CoodLine.apply(i1, i2, i3, i4)
  def toLine2s(f: Cood => Vec2): Line2s = pMap(_.toLine2(f))
  //override def toString: String = CoodLines.PersistImplicit.show(this)
}

class CoodLinesBuff(val buffer: Buff[Int] = buffInt()) extends AnyVal with ProductI4sBuff[CoodLine, CoodLines]
{ override def toProductInts: CoodLines = new CoodLines(toArray)
}

object CoodLines extends ProductI4sCompanion[CoodLine, CoodLines]
{ implicit val factory: Int => CoodLines = i => new CoodLines(new Array[Int](i * 4))
  override def buff(initialSize: Int): CoodLinesBuff = new CoodLinesBuff(buffInt(initialSize * 4))

  implicit val PersistImplicit = new ProductI4sBuilder[CoodLine, CoodLines]("CoodLines")
  { override def fromArray(value: Array[Int]): CoodLines = new CoodLines(value)
  }
}