/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A line segment in 3 dimensional space specified in metres. A straight line between two points in 3D. */
class LineSegM3(xStartMs: Double, yStartMs: Double, zStartMs: Double, xEndMs: Double, yEndMs: Double, zEndMs: Double) extends LineSegLike[PtM3] with
  Dbl6Elem
{ def typeStr: String = "LineDist3"
  //def str: String = persist2(pStart, pEnd)
  def startPt: PtM3 = new PtM3(xStartMs, yStartMs, zStartMs)
  def endPt: PtM3 = new PtM3(xEndMs, yEndMs, zEndMs)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[LineDist3]
  override def dbl1 = xStartMs
  override def dbl2 = yStartMs
  override def dbl3 = zStartMs
  override def dbl4 = xEndMs
  override def dbl5 = yEndMs
  override def dbl6 = zEndMs
  def xStart: Length = Length(xStartMs)
  def yStart: Length = Length(yStartMs)
  def zStart: Length = Length(zStartMs)
  def xEnd: Length = Length(xEndMs)
  def yEnd: Length = Length(yEndMs)
  def zEnd: Length = Length(zEndMs)
  def zsPos: Boolean = zStart.pos && zEnd.pos

  /** Takes the X and Y components. */
  def xy: LineSegM2 = new LineSegM2(xStartMs, yStartMs, xEndMs, yEndMs)

  /** Scales the X and Y components to a scalar though the operand scaling length. */
  def xyLineSeg(scale: Length): LineSeg = LineSeg(xStart / scale, yStart / scale, xEnd / scale, yEnd / scale)
}

/** Companion object for [[LineSegM3]] trait contains apply factory method. */
object LineSegM3
{
   def apply(pStart: PtM3, pEnd: PtM3): LineSegM3 = new LineSegM3(pStart.xMetres, pStart.yMetres, pStart.zMetres,
       pEnd.xMetres, pEnd.yMetres, pEnd.zMetres)

  /** Implicit instance / evidence for [[BuilderArrMap]] for [[LineSegM3]], [[LineSegM3Arr]] type class. */
  implicit val buildEv: Dbl6ArrMapBuilder[LineSegM3, LineSegM3Arr] = new Dbl6ArrMapBuilder[LineSegM3, LineSegM3Arr]
  { type BuffT = LineSegM3Buff
    override def fromDblArray(array: Array[Double]): LineSegM3Arr = new LineSegM3Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): LineSegM3Buff = new LineSegM3Buff(buffer)
  }

  implicit val rotateM3TEv: RotateM3T[LineSegM3] = new RotateM3T[LineSegM3] {
    /** Rotate around the X axis, viewed from positive X. A positive angle is anti clockwise. */
    override def rotateXT(obj: LineSegM3, angle: AngleVec): LineSegM3 = LineSegM3(obj.startPt.rotateX(angle), obj.endPt.rotateX(angle))

    /** Rotates this vector around the Y axis, viewed form positive Y through the given angle around the origin. */
    override def rotateYT(obj: LineSegM3, angle: AngleVec): LineSegM3 = LineSegM3(obj.startPt.rotateY(angle), obj.endPt.rotateY(angle))

    /** Rotate around the Z axis, viewed from positive Z. A positive angle is anti clockwise. */
    override def rotateZT(obj: LineSegM3, angle: AngleVec): LineSegM3 = LineSegM3(obj.startPt.rotateZ(angle), obj.endPt.rotateZ(angle))

    /** Rotate 180 degrees around the Z axis. */
    override def rotateZ180T(obj: LineSegM3): LineSegM3 = LineSegM3(obj.startPt.rotateZ180, obj.endPt.rotateZ180)
  }
}

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. LineSeg is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegM3Arr(val unsafeArray: Array[Double]) extends Dbl6Arr[LineSegM3]
{ type ThisT = LineSegM3Arr
  def fromArray(array: Array[Double]): LineSegM3Arr = new LineSegM3Arr(array)
  override def typeStr: String = "LineSegM3Arr"
  override def fElemStr: LineSegM3 => String = _.toString

  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): LineSegM3 =
    new LineSegM3(d1, d2, d3, d4, d5, d6)
}

/** Companion object for the LineSegM3s class. */
object LineSegM3Arr extends Dbl6SeqLikeCompanion[LineSegM3, LineSegM3Arr]
{
  override def fromArray(array: Array[Double]): LineSegM3Arr = new LineSegM3Arr(array)

  /*implicit val persistImplicit: Dbl6SeqDefPersist[LineSegM3, LineSegM3Arr] = new Dbl4SeqDefPersist[LineSegM3, LineSegM3Arr]("Line2s")
  { override def fromArray(value: Array[Double]): LineSegM3Arr = new LineSegM3Arr(value)

    override def showDecT(obj: LineSegM3Arr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

  }*/

  /** Implicit instance /evidence for [[BuilderArrFlat]] type class instance. */
  implicit val flatBuildEv: BuilderArrFlat[LineSegM3Arr] = new Dbl6ArrFlatBuilder[LineSegM3Arr]
  { type BuffT = LineSegM3Buff
    override def fromDblArray(array: Array[Double]): LineSegM3Arr = new LineSegM3Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegM3Buff = new LineSegM3Buff(inp)
  }
}

/** Efficient expandable buffer for [[LineSegM3]]s. */
class LineSegM3Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl6Buff[LineSegM3]
{ override def typeStr: String = "LineSegM3Buff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): LineSegM3 =
    new LineSegM3(d1, d2, d3, d4, d5, d6)
}