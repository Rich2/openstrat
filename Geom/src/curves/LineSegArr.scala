/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. [[LineSeg]] is the library's term for a mathematical straight line
 *  segment, but what in common parlance is often just referred to as a line. */
class LineSegArr(val unsafeArray: Array[Double]) extends AnyVal with LineSegLikeDbl4Arr[Pt2, LineSeg] with Dbl4Arr[LineSeg] with AffinePreserve
{ type ThisT = LineSegArr
  def fromArray(array: Array[Double]): LineSegArr = new LineSegArr(array)
  override def typeStr: String = "LineSegArr"
  override def fElemStr: LineSeg => String = _.str
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
  override def ptsTrans(f: Pt2 => Pt2): LineSegArr = map(orig => LineSeg(f(orig.pStart), f(orig.pEnd)))

  /** Draws the sides with the given width and colour. */
  def draw(lineWidth: Double = 2, colour: Colour = Colour.Black): LinesDraw = LinesDraw(this, lineWidth, colour)
}

/** Companion object for the LineSegs class. */
object LineSegArr extends Dbl4SeqLikeCompanion[LineSeg, LineSegArr]
{
  override def fromArray(array: Array[Double]): LineSegArr = new LineSegArr(array)

  implicit val persistImplicit: Dbl4SeqLikePersist[LineSeg, LineSegArr] = new Dbl4SeqLikePersist[LineSeg, LineSegArr]("Line2s")
  { override def fromArray(value: Array[Double]): LineSegArr = new LineSegArr(value)

    override def showDecT(obj: LineSegArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit instance /evidence for [[ArrFlatBuilder]] type class instance. */
  implicit val flatBuildEv: ArrFlatBuilder[LineSegArr] = new LineSegArrFlatBuilder

  implicit val transImplicit: AffineTrans[LineSegArr] = (obj, f) => obj.map(_.ptsTrans(f))
}

/** Efficient expandable buffer for Line2s. */
class LineSegBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl4Buff[LineSeg]
{ override def typeStr: String = "Line2sBuff"
  override def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
}

trait LineSegArrCommonBuilder extends Dbl4ArrCommonBuilder[LineSegArr]
{ type BuffT = LineSegBuff
  override def fromDblArray(array: Array[Double]): LineSegArr = new LineSegArr(array)
  def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegBuff = new LineSegBuff(inp)
}

class LineSegArrMapBuilder extends LineSegArrCommonBuilder with Dbl4ArrMapBuilder[LineSeg, LineSegArr]
class LineSegArrFlatBuilder extends LineSegArrCommonBuilder with Dbl4ArrFlatBuilder[LineSegArr]

class LineSegPair[A2](val a1Dbl1: Double, val a1Dbl2: Double, val a1Dbl3: Double, val a1Dbl4: Double, val a2: A2) extends LineSegLikeDbl4Pair[Pt2, LineSeg, A2]
{ inline def startX: Double = a1Dbl1
  inline def startY: Double = a1Dbl2
  inline def endX: Double = a1Dbl3
  inline def endY: Double = a1Dbl4
  override def a1: LineSeg = new LineSeg(startX, startY, endX, endY)
}

final class LineSegPairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends LineSegLikeDbl4PairArr[Pt2, LineSeg, LineSegArr, A2, LineSegPair[A2]]
{ override type ThisT = LineSegPairArr[A2]
  override def typeStr: String = "LineSeqArrPair"
  override def a1Arr: LineSegArr = new LineSegArr(a1ArrayDbl)
  override def newPair(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double, a2: A2): LineSegPair[A2] = new LineSegPair[A2](dbl1, dbl2, dbl3, dbl4, a2)
  override def newA1(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double): LineSeg = new LineSeg(dbl1, dbl2, dbl3, dbl4)
  override def fElemStr: LineSegPair[A2] => String = _.toString
  override def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): LineSegPairArr[A2] = new LineSegPairArr[A2](a1Array, a2Array)
}