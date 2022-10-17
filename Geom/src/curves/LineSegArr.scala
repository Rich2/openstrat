/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. [[LineSeg]] is the library's term for a mathematical straight line
 *  segment, but what in common parlance is often just referred to as a line. */
class LineSegArr(val unsafeArray: Array[Double]) extends AnyVal with LineSegLikeArr[LineSeg] with Dbl4Arr[LineSeg] with AffinePreserve
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
  implicit val flatBuildEv: ArrFlatBuilder[LineSegArr] = new Dbl4ArrFlatBuilder[LineSeg, LineSegArr]
  { type BuffT = LineSegBuff
    override def fromDblArray(array: Array[Double]): LineSegArr = new LineSegArr(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): LineSegBuff = new LineSegBuff(inp)
  }

  implicit val transImplicit: AffineTrans[LineSegArr] = (obj, f) => obj.map(_.ptsTrans(f))
}

/** Efficient expandable buffer for Line2s. */
class LineSegBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl4Buff[LineSeg]
{ override def typeStr: String = "Line2sBuff"
  override def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
}

class LineSegPair[A2](val startX: Double, val startY: Double, val endX: Double, val endY: Double, val a2: A2) extends LineSegLikePair[Pt2, LineSeg, A2]
{
  override def a1: LineSeg = new LineSeg(startX, startY, endX, endY)
}

final class LineSegPairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends LineSegDblsPairArr[Pt2, LineSeg, LineSegArr, A2, LineSegPair[A2]]
{
  override type ThisT = LineSegPairArr[A2]
  override def typeStr: String = "LineSeqArrPair"
  override def a1Arr: LineSegArr = new LineSegArr(a1ArrayDbl)

  override def apply(index: Int): LineSegPair[A2] =
    new LineSegPair[A2](a1ArrayDbl(index * 4), a1ArrayDbl(index * 4 + 1), a1ArrayDbl(index * 4 + 2), a1ArrayDbl(index * 4 + 3), a2Array(index))

  override def fromArrays(a1Arr: Array[Double], a2Array: Array[A2]): LineSegPairArr[A2] = new LineSegPairArr[A2](a1Arr, a2Array)

  override def unsafeSetElem(i: Int, value: LineSegPair[A2]): Unit = { a1ArrayDbl(i * 4) = value.startX; a1ArrayDbl(i * 4 + 1) = value.startY
    a1ArrayDbl(i * 4 + 2) = value.endX; a1ArrayDbl(i * 4 + 3) = value.endY
    a2Array(i) = value.a2
  }

  override def fElemStr: LineSegPair[A2] => String = _.toString
}

/*
final class LineSegArrPairBuilder[A2] extends LineSegLikePairArrBuilder[Pt2, LineSeg, LineSegArr, A2, LineSegPair[A2], LineSegPairArr[A2]]
{
  override type BuffT = LineSegBuff

  /** Builder for the first element of the pair of type B1, in this case a [[LineSegLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[LineSegLikePairArr]]. */
  override def b1Builder: LineSegLikeBuilder[Pt2, LineSeg] = ???

  /** Builder for an Arr of the first element of the pair. */
  override def b1ArrBuilder: ArrBuilder[LineSeg, LineSegArr] = LineSeg.buildEv

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  override def pairArrBuilder(polygonArr: LineSegArr, a2s: Array[A2]): LineSegPairArr[A2] = ???

  override def newArr(length: Int): LineSegPairArr[A2] = ???

  override def arrSet(arr: LineSegPairArr[A2], index: Int, value: LineSegPair[A2]): Unit = ???

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  override def buffGrow(buff: LineSegBuff, value: LineSegPair[A2]): Unit = ???

  /** A mutable operation that extends the ArrayBuffer with the elements of the Immutable Array operand. */
  override def buffGrowArr(buff: LineSegBuff, arr: LineSegPairArr[A2]): Unit = ???

  override def newBuff(length: Int): LineSegBuff = ???

  /** converts a the buffer type to the target compound class. */
  override def buffToBB(buff: LineSegBuff): LineSegPairArr[A2] = ???
}*/
