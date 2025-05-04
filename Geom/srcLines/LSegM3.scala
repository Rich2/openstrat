/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A line segment in 3-dimensional space specified in metres. A straight line between two points in 3D. */
class LSegM3(val xStartMetresNum: Double, val yStartMetresNum: Double, val zStartMetresNum: Double, val xEndMetresNum: Double, val yEndMetresNum: Double,
  val zEndMetresNum: Double) extends LineSegLength3[PtM3]
{ def typeStr: String = "LineDist3"  
  def startPt: PtM3 = PtM3.metreNum(xStartMetresNum, yStartMetresNum, zStartMetresNum)
  def endPt: PtM3 = PtM3.metreNum(xEndMetresNum, yEndMetresNum, zEndMetresNum)
  override def dbl1: Double = xStartMetresNum
  override def dbl2: Double = yStartMetresNum
  override def dbl3: Double = zStartMetresNum
  override def dbl4: Double = xEndMetresNum
  override def dbl5: Double = yEndMetresNum
  override def dbl6: Double = zEndMetresNum
  def xStart: Metres = Metres(xStartMetresNum)
  def yStart: Metres = Metres(yStartMetresNum)
  def zStart: Metres = Metres(zStartMetresNum)
  def xEnd: Metres = Metres(xEndMetresNum)
  def yEnd: Metres = Metres(yEndMetresNum)
  def zEnd: Metres = Metres(zEndMetresNum)
  def zsPos: Boolean = zStart.nonNeg && zEnd.nonNeg

  /** Takes the X and Y components. */
  def xy: LSegM2 = new LSegM2(xStartMetresNum, yStartMetresNum, xEndMetresNum, yEndMetresNum)

  /** Scales the X and Y components to a scalar though the operand scaling length. */
  def xyLineSeg(scale: LengthMetric): LSeg2 = LSeg2(xStart / scale, yStart / scale, xEnd / scale, yEnd / scale)
}

/** Companion object for [[LSegM3]] trait contains apply factory method. */
object LSegM3
{
   def apply(pStart: PtM3, pEnd: PtM3): LSegM3 = new LSegM3(pStart.xMetresNum, pStart.yMetresNum, pStart.zMetresNum, pEnd.xMetresNum, pEnd.yMetresNum,
     pEnd.zMetresNum)

  def metresNum(xStartMetresNum: Double, yStartMetresNum: Double, zStartMetresNum: Double, xEndMetresNum: Double, yEndMetresNum: Double, zEndMetresNum: Double):
    LSegM3 = new LSegM3(xStartMetresNum, yStartMetresNum, zStartMetresNum, xEndMetresNum, yEndMetresNum, zEndMetresNum)

  /** Implicit instance / evidence for [[BuilderArrMap]] for [[LSegM3]], [[LineSegM3Arr]] type class. */
  implicit val buildEv: BuilderMapArrDbl6[LSegM3, LineSegM3Arr] = new BuilderMapArrDbl6[LSegM3, LineSegM3Arr]
  { type BuffT = LineSegM3Buff
    override def fromDblArray(array: Array[Double]): LineSegM3Arr = new LineSegM3Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): LineSegM3Buff = new LineSegM3Buff(buffer)
  }

  implicit val rotateM3TEv: RotateM3T[LSegM3] = new RotateM3T[LSegM3] {
    /** Rotate around the X axis, viewed from positive X. A positive angle is anti clockwise. */
    override def rotateXT(obj: LSegM3, angle: AngleVec): LSegM3 = LSegM3(obj.startPt.rotateX(angle), obj.endPt.rotateX(angle))

    /** Rotates this vector around the Y axis, viewed form positive Y through the given angle around the origin. */
    override def rotateYT(obj: LSegM3, angle: AngleVec): LSegM3 = LSegM3(obj.startPt.rotateY(angle), obj.endPt.rotateY(angle))

    /** Rotate around the Z axis, viewed from positive Z. A positive angle is anti clockwise. */
    override def rotateZT(obj: LSegM3, angle: AngleVec): LSegM3 = LSegM3(obj.startPt.rotateZ(angle), obj.endPt.rotateZ(angle))

    /** Rotate 180 degrees around the Z axis. */
    override def rotateZ180T(obj: LSegM3): LSegM3 = LSegM3(obj.startPt.rotateZ180, obj.endPt.rotateZ180)
  }
}

/** Compact immutable Array[Double] based collection class for [[LSeg2]]s. LineSeg is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegM3Arr(val arrayUnsafe: Array[Double]) extends ArrDbl6[LSegM3]
{ type ThisT = LineSegM3Arr
  def fromArray(array: Array[Double]): LineSegM3Arr = new LineSegM3Arr(array)
  override def typeStr: String = "LineSegM3Arr"
  override def fElemStr: LSegM3 => String = _.toString

  override def elemFromDbls(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): LSegM3 =
    new LSegM3(d1, d2, d3, d4, d5, d6)
}

/** Companion object for the LineSegM3s class. */
object LineSegM3Arr extends CompanionSlDbl6[LSegM3, LineSegM3Arr]
{
  override def fromArray(array: Array[Double]): LineSegM3Arr = new LineSegM3Arr(array)

  /*implicit val persistImplicit: Dbl6SeqDefPersist[LineSegM3, LineSegM3Arr] = new Dbl4SeqDefPersist[LineSegM3, LineSegM3Arr]("Line2s")
  { override def fromArray(value: Array[Double]): LineSegM3Arr = new LineSegM3Arr(value)

    override def showDecT(obj: LineSegM3Arr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

  }*/

  /** Implicit instance /evidence for [[BuilderArrFlat]] type class instance. */
  implicit val flatBuildEv: BuilderArrFlat[LineSegM3Arr] = new BuilderFlatArrDbl6[LineSegM3Arr]
  { type BuffT = LineSegM3Buff
    override def fromDblArray(array: Array[Double]): LineSegM3Arr = new LineSegM3Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegM3Buff = new LineSegM3Buff(inp)
  }
}

/** Efficient expandable buffer for [[LSegM3]]s. */
class LineSegM3Buff(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal, BuffDbl6[LSegM3]
{ override def typeStr: String = "LineSegM3Buff"
  override def elemFromDbls(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): LSegM3 =
    new LSegM3(d1, d2, d3, d4, d5, d6)
}