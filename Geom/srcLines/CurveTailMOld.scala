/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Needs removing. A base trait for DistCurveSeg and DistCurveTail and their associated GraphicElemsDist (these haven't been implemented or precisely
 *  named yet). */
trait CurveSegMLikeOld
{ /** Set to Double.NaN if LineSegment. Set to Double.Positive Infinity of ArcSegment, otherwise the x component of the the first bezier control
    *  point. */
  def xC1Metres: Double
  final def xC1: Metres = Metres(xC1Metres)
  def yC1Metres: Double
  final def yC1: Metres = Metres(yC1Metres)
  final def pC1: PtM2 = PtM2(xC1, yC1)
   
  def xUsesMetres: Double
  /** the x component of the uses point */
  def xUses: Metres = Metres(xUsesMetres)
  def yUsesMetres: Double
  /** the y component of the uses point */
  def yUses: Metres = Metres(yUsesMetres)
  /** the x component of the end point */
  /** The uses point. The centre point on an arc segment, control point 2 on a cubic bezier. Not used on line segment. */
  final def pUses: PtM2 = PtM2(xUses, yUses)
   
  def xEndMetres: Double
  def yEndMetres: Double
  def xEnd: Metres = Metres(xEndMetres)
  /** the y component of the end point */
  def yEnd: Metres = Metres(yEndMetres)
  /** The end point. Often called p2 on a line or p4 on a cubic bezier. */
  final def pEnd: PtM2 = PtM2(xEnd, yEnd)
}

/** Needs removing. A curve segment tail described in distance units rather than scalars. A DistCurveSeg without its starting point which will
 *  normally be supplied by the preceding DistCurveTail. */
class CurveTailMOld(val iMatch: Double, val xC1Metres: Double, val yC1Metres: Double, val xUsesMetres: Double, val yUsesMetres: Double,
                    val xEndMetres: Double, val yEndMetres: Double) extends Dbl7Elem with CurveSegMLikeOld
{ 
  def toCurveSeg(f: PtM2 => Pt2): CurveTailOld = xC1Metres match
  {
    case 10 =>
    { val endVec = f(pEnd)
      new CurveTailOld(10, 0, 0, 0, 0, endVec.x, endVec.y)
    }
    
    case 11 =>
    { val cenVec = f(pUses)
      val endVec = f(pEnd)
      new CurveTailOld(11, 0, 0, cenVec.x, cenVec.y, endVec.x, endVec.y)
    }
    
    case _ =>
    { val c1Vec = f(pC1)
      val cenVec = f(pUses)
      val endVec = f(pEnd)
      new CurveTailOld(12, c1Vec.x, c1Vec.y, cenVec.x, cenVec.y, endVec.x, endVec.y)
    }
  }
  //override def canEqual(other: Any): Boolean = other.isInstanceOf[CurveTail]
  @inline override def dbl1 = iMatch
  @inline override def dbl2 = xC1Metres
  @inline override def dbl3 = yC1Metres
  @inline override def dbl4 = xUsesMetres
  @inline override def dbl5 = yUsesMetres
  @inline override def dbl6 = xEndMetres
  @inline override def dbl7 = yEndMetres
}

/** To be removed.m */
object LineSegTailMOld
{ def apply(endPt: PtM2): CurveTailMOld = new CurveTailMOld(10, 0, 0, 0, 0, endPt.xMetresNum, endPt.yMetresNum)
//   override def toVec2s(f: Dist2 => Vec2): CurveSeg = LineSeg(f(endPt))   
}