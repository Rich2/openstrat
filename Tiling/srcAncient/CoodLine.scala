/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import geom._, scala.collection.mutable.ArrayBuffer

/** A 2d line defined by its start and end Tile Cood. */
case class CoodLine(x1: Int, y1: Int, x2: Int, y2: Int) extends ElemInt4
{
  def int1 = x1
  def int2 = y1
  def int3 = x2
  def int4 = y2
   def toLine2(f: Cood => Pt2): LineSeg =
   {  val v1 = f(Cood(x1, y1))
      val v2 = f(Cood(x2, y2))
      LineSeg(v1, v2)
   }
}

object CoodLine
{ def apply(c1: Cood, c2: Cood): CoodLine = CoodLine(c1.xi, c1.yi, c2.xi, c2.yi)
}

/** An Array[Int] based collection for CoodLines. */
class CoodLines(val unsafeArray: Array[Int]) extends AnyVal with Int4Arr[CoodLine]
{ type ThisT = CoodLines
  override def fElemStr: CoodLine => String = _.toString
  override def fromArray(array: Array[Int]): CoodLines = new CoodLines(array)
  override def typeStr: String = "CoodLines"
  override def newElem(i1: Int, i2: Int, i3: Int, i4: Int): CoodLine = CoodLine.apply(i1, i2, i3, i4)
  def toLine2s(f: Cood => Pt2): LineSegArr = dataMap(_.toLine2(f))
  //override def toString: String = CoodLines.PersistImplicit.show(this)
}

class CoodLinesBuff(val unsafeBuffer: ArrayBuffer[Int] = BuffInt()) extends AnyVal with Int4Buff[CoodLine, CoodLines]
{// override def unBuff: CoodLines = new CoodLines(toArray)
  override def typeStr: String = "CoodLinesBuff"
  override def intsToT(i1: Int, i2: Int, i3: Int, i4: Int): CoodLine = new CoodLine(i1, i2, i3, i4)
}

object CoodLines extends Int4ArrCompanion[CoodLine, CoodLines]
{
  override def fromArray(array: Array[Int]): CoodLines = new CoodLines(array)

  override def buff(initialSize: Int): CoodLinesBuff = new CoodLinesBuff(BuffInt(initialSize * 4))

  implicit val PersistImplicit: Int4SeqLikePersist[CoodLine, CoodLines] = new Int4SeqLikePersist[CoodLine, CoodLines]("CoodLines")
  { override def fromArray(value: Array[Int]): CoodLines = new CoodLines(value)

    override def showDecT(obj: CoodLines, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }
}