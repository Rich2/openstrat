/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pGrid
import geom._

/** A 2d line defined by its start and end TileGrid Roord. */
case class RoordLine(y1: Int, c1: Int, y2: Int, c2: Int) extends ElemInt4
{
  def int1 = y1
  def int2 = c1
  def int3 = y2
  def int4 = c2
  def toLine2(f: Roord => Pt2): LineSeg =
  { val v1 = f(Roord(y1, c1))
    val v2 = f(Roord(y2, c2))
    LineSeg(v1, v2)
  }
  def gridLine2(implicit grid: TileGridOld): LineSeg = toLine2(grid.roordToPt2)
}

object RoordLine
{ def apply(r1: Roord, r2: Roord): RoordLine = RoordLine(r1.y, r1.c, r2.y, r2.c)
}

/** An Array[Int] based collection for RoordLines. */
class RoordLines(val arrayUnsafe: Array[Int]) extends AnyVal with ArrInt4s[RoordLine]
{ type ThisT = RoordLines
  override def fElemStr: RoordLine => String = _.toString
  override def unsafeFromArray(array: Array[Int]): RoordLines = new RoordLines(array)
  override def typeStr: String = "RoordLines"
  override def newElem(i1: Int, i2: Int, i3: Int, i4: Int): RoordLine = RoordLine.apply(i1, i2, i3, i4)
  def toLine2s(f: Roord => Pt2): LineSegs = dataMap(_.toLine2(f))
  //override def toString: String = RoordLines.PersistImplicit.show(this)
}

class RoordLinesBuff(val unsafeBuff: Buff[Int] = buffInt()) extends AnyVal with BuffInt4s[RoordLine, RoordLines]
{// override def unBuff: RoordLines = new RoordLines(toArray)
  override def typeStr: String = "RoordLinesBuff"
  override def intsToT(i1: Int, i2: Int, i3: Int, i4: Int): RoordLine = new RoordLine(i1, i2, i3, i4)
}

object RoordLines extends ArrInt4sCompanion[RoordLine, RoordLines]
{ implicit val factory: Int => RoordLines = i => new RoordLines(new Array[Int](i * 4))
  override def buff(initialSize: Int): RoordLinesBuff = new RoordLinesBuff(buffInt(initialSize * 4))

  implicit val PersistImplicit: ArrInt4sPersist[RoordLine, RoordLines] = new ArrInt4sPersist[RoordLine, RoordLines]("RoordLines")
  { override def fromArray(value: Array[Int]): RoordLines = new RoordLines(value)

    override def showT(obj: RoordLines, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ???
  }
}