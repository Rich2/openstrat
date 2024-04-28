/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A line segment in 3 dimensional space specified in kilometres. A straight line between two points in 3D. */
class LineSegKm3(val xStartKilometresNum: Double, val yStartKilometresNum: Double, val zStartKilometresNum: Double, val xEndKilometresNum: Double,
  val yEndKilometresNum: Double, val zEndKilometresNum: Double) extends LineSegLength3[PtKm3]
{ def typeStr: String = "LineDist3"
  //def str: String = persist2(pStart, pEnd)
  def startPt: PtKm3 = new PtKm3(xStartKilometresNum, yStartKilometresNum, zStartKilometresNum)
  def endPt: PtKm3 = new PtKm3(xEndKilometresNum, yEndKilometresNum, zEndKilometresNum)
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[LineDist3]
  override def dbl1: Double = xStartKilometresNum
  override def dbl2: Double = yStartKilometresNum
  override def dbl3: Double = zStartKilometresNum
  override def dbl4: Double = xEndKilometresNum
  override def dbl5: Double = yEndKilometresNum
  override def dbl6: Double = zEndKilometresNum
  def xStart: Kilometres = Kilometres(xStartKilometresNum)
  def yStart: Kilometres = Kilometres(yStartKilometresNum)
  def zStart: Kilometres = Kilometres(zStartKilometresNum)
  def xEnd: Kilometres = Kilometres(xEndKilometresNum)
  def yEnd: Kilometres = Kilometres(yEndKilometresNum)
  def zEnd: Kilometres = Kilometres(zEndKilometresNum)
  def zsPos: Boolean = zStart.pos && zEnd.pos

  /** Takes the X and Y components. */
  def xy: LineSegKm2 = new LineSegKm2(xStartKilometresNum, yStartKilometresNum, xEndKilometresNum, yEndKilometresNum)

  /** Scales the X and Y components to a scalar though the operand scaling length. */
  def xyLineSeg(scale: LengthMetric): LineSeg = LineSeg(xStart / scale, yStart / scale, xEnd / scale, yEnd / scale)

  override def xStartMetresNum: Double = ???

  override def yStartMetresNum: Double = ???

  override def zStartMetresNum: Double = ???

  override def xEndMetresNum: Double = ???

  override def yEndMetresNum: Double = ???

  override def zEndMetresNum: Double = ???
}

/** Companion object for [[LineSegKm3]] trait contains apply factory method. */
object LineSegKm3
{
   def apply(pStart: PtKm3, pEnd: PtKm3): LineSegKm3 = new LineSegKm3(pStart.xKilometresNum, pStart.yKilometresNum, pStart.zKilometresNum, pEnd.xKilometresNum,
     pEnd.yKilometresNum, pEnd.zKilometresNum)

  def kilometresNum(xStartKilometresNum: Double, yStartKilometresNum: Double, zStartKilometresNum: Double, xEndKilometresNum: Double, yEndKilometresNum: Double, zEndKilometresNum: Double):
    LineSegKm3 = new LineSegKm3(xStartKilometresNum, yStartKilometresNum, zStartKilometresNum, xEndKilometresNum, yEndKilometresNum, zEndKilometresNum)

  /** Implicit instance / evidence for [[BuilderArrMap]] for [[LineSegKm3]], [[LineSegKm3Arr]] type class. */
  implicit val buildEv: BuilderArrDbl6Map[LineSegKm3, LineSegKm3Arr] = new BuilderArrDbl6Map[LineSegKm3, LineSegKm3Arr]
  { type BuffT = LineSegKm3Buff
    override def fromDblArray(array: Array[Double]): LineSegKm3Arr = new LineSegKm3Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): LineSegKm3Buff = new LineSegKm3Buff(buffer)
  }

  /*implicit val rotateKm3TEv: RotateKm3T[LineSegKm3] = new RotateKm3T[LineSegKm3] {
    /** Rotate around the X axis, viewed from positive X. A positive angle is anti clockwise. */
    override def rotateXT(obj: LineSegKm3, angle: AngleVec): LineSegKm3 = LineSegKm3(obj.startPt.rotateX(angle), obj.endPt.rotateX(angle))

    /** Rotates this vector around the Y axis, viewed form positive Y through the given angle around the origin. */
    override def rotateYT(obj: LineSegKm3, angle: AngleVec): LineSegKm3 = LineSegKm3(obj.startPt.rotateY(angle), obj.endPt.rotateY(angle))

    /** Rotate around the Z axis, viewed from positive Z. A positive angle is anti clockwise. */
    override def rotateZT(obj: LineSegKm3, angle: AngleVec): LineSegKm3 = LineSegKm3(obj.startPt.rotateZ(angle), obj.endPt.rotateZ(angle))

    /** Rotate 180 degrees around the Z axis. */
    override def rotateZ180T(obj: LineSegKm3): LineSegKm3 = LineSegKm3(obj.startPt.rotateZ180, obj.endPt.rotateZ180)
  }*/
}

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. LineSeg is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegKm3Arr(val arrayUnsafe: Array[Double]) extends ArrDbl6[LineSegKm3]
{ type ThisT = LineSegKm3Arr
  def fromArray(array: Array[Double]): LineSegKm3Arr = new LineSegKm3Arr(array)
  override def typeStr: String = "LineSegKm3Arr"
  override def fElemStr: LineSegKm3 => String = _.toString

  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): LineSegKm3 =
    new LineSegKm3(d1, d2, d3, d4, d5, d6)
}

/** Companion object for the LineSegKm3s class. */
object LineSegKm3Arr extends CompanionSqLikeDbl6[LineSegKm3, LineSegKm3Arr]
{
  override def fromArray(array: Array[Double]): LineSegKm3Arr = new LineSegKm3Arr(array)

  /*implicit val persistImplicit: Dbl6SeqDefPersist[LineSegKm3, LineSegKm3Arr] = new Dbl4SeqDefPersist[LineSegKm3, LineSegKm3Arr]("Line2s")
  { override def fromArray(value: Array[Double]): LineSegKm3Arr = new LineSegKm3Arr(value)

    override def showDecT(obj: LineSegKm3Arr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

  }*/

  /** Implicit instance /evidence for [[BuilderArrFlat]] type class instance. */
  implicit val flatBuildEv: BuilderArrFlat[LineSegKm3Arr] = new BuilderArrDbl6Flat[LineSegKm3Arr]
  { type BuffT = LineSegKm3Buff
    override def fromDblArray(array: Array[Double]): LineSegKm3Arr = new LineSegKm3Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegKm3Buff = new LineSegKm3Buff(inp)
  }
}

/** Efficient expandable buffer for [[LineSegKm3]]s. */
class LineSegKm3Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl6[LineSegKm3]
{ override def typeStr: String = "LineSegKm3Buff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): LineSegKm3 =
    new LineSegKm3(d1, d2, d3, d4, d5, d6)
}