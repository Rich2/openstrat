/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** The base trait for CurveSeg and Curve and their associated GraphicElems */
trait CurveSegLike extends AffineElem
{ /** the x component of the end point */
  def xEnd: Double
  /** the y component of the end point */
  def yEnd: Double
  /** The end point. Often called p2 on a line or p4 on a cubic bezier. */
  final def pEnd: Vec2 = xEnd vv yEnd
}

/** A CurveSeg can  be a line segment or an arc segment or a bezier segment. It takes its start point from the pEnd of the previous segment. There is
  * no CurveSeg companion object as the LineSeg, ArcSeg and BezierSeg all have their own factory object apply methods. */
case class CurveSeg(val iMatch: Double, val xC1: Double, val yC1: Double, val xUses: Double, val yUses: Double, val xEnd: Double,
  val yEnd: Double) extends ProdDbl7 with CurveSegLike
{ override type ThisT = CurveSeg
  override def canEqual(other: Any): Boolean = other.isInstanceOf[CurveSeg]
  @inline override def _1 = iMatch
  @inline override def _2 = xC1
  @inline override def _3 = yC1
  @inline override def _4 = xUses
  @inline override def _5 = yUses
  @inline override def _6 = xEnd
  @inline override def _7 = yEnd

  /** This is control point 2 in a Bezier segment, the centre point in an arc segment and unused in a straight Line Segment */
  def pUses: Vec2 = Vec2(xUses, yUses)
  /** This is control point 1 in a Bezier segment, it not used an arc segment, but first Double set to NaN, it is not nused in a straight Line Segment
   *  but the first Double is set to Infinity */
  def pC1: Vec2 = Vec2(xC1, yC1)
      
  def fSeg[A](fLineSeg: Vec2 => A, fArcSeg: (Vec2, Vec2) => A, fBezierSeg: (Vec2, Vec2, Vec2) => A): A = iMatch match
  { case 10 => fLineSeg(pEnd)
    case 11 => fArcSeg(pUses, pEnd)
    case 12 => fBezierSeg(pC1, pUses, pEnd)
    case n => excep("iMatch in LineSeg has value: " + n.toString + " Must be 10, 11 0r 12.")
  }
   
  def fSeg[A](fLineSeg: (Double, Double) => A, fArcSeg: (Double, Double, Double, Double) => A,
      fBezierSeg: (Double, Double, Double, Double, Double, Double) => A): A = iMatch match
   { case 10 => fLineSeg(xEnd, yEnd)
     case 11 => fArcSeg(xUses, yUses, xEnd, yEnd)
     case 12 => fBezierSeg(xC1, yC1, xUses, yUses, xEnd, yEnd)
     case n => excep("iMatch in LineSeg has value: " + n.toString + " Must be 10, 11 0r 12.")
   }
   
  def segDo(fLineSeg: CurveSeg => Unit, fArcSeg: CurveSeg => Unit, fBezierSeg: CurveSeg => Unit): Unit =  iMatch match
  { case 10 => fLineSeg(this)
    case 11 => fArcSeg(this)
    case 12 => fBezierSeg(this)
    case n => excep("iMatch in LineSeg has value: " + n.toString + " Must be 10, 11 0r 12.")
  }
   
  override def fTrans(f: Vec2 => Vec2): CurveSeg = iMatch match
  {
    case 10 => { val newPEnd: Vec2 = f(pEnd); new CurveSeg(10, 0, 0, 0, 0, newPEnd.x, newPEnd.y) }
    
    case 11 =>
    { val newPCen: Vec2 = f(pUses)
      val newPEnd: Vec2 = f(pEnd)
      new CurveSeg(11, 0, 0, newPCen.x, newPCen.y, newPEnd.x, newPEnd.y)
    }
    
    case 12 =>
    { val newPC1: Vec2 = f(pC1)
      val newPCen: Vec2 = f(pUses)
      val newPEnd: Vec2 = f(pEnd)
      new CurveSeg(12, newPC1.x, newPC1.y, newPCen.x, newPCen.y, newPEnd.x, newPEnd.y)
    }
    
    case n => excep("iMatch in LineSeg has value: " + n.toString + " Must be 10, 11 0r 12.")
  }
  /** Assuming this is Arc Segment */
  def arcCen: Vec2 = Vec2(xUses, yUses)
  /** Assuming this is Arc Segment */
  def arcStartAngle(pStart: Vec2): Angle = (pStart - arcCen).angle
  /** Assuming this is Arc Segment */
  def arcEndAngle: Angle = (pEnd - arcCen).angle
  /** Assuming this is Arc Segment */
  def arcRadius: Double = (pEnd - arcCen).magnitude
  /** Assuming this is Arc Segment */
  def arcControlPt(startPt: Vec2): Vec2 =
  { val sAng: Angle = arcStartAngle(startPt)
    val resultAngle = sAng.bisect(arcEndAngle)
    val alphaAngle =  sAng.angleTo(arcEndAngle) / 2
    arcCen + resultAngle.toVec2(arcRadius) / alphaAngle.cos
  }
  /** Assuming this is ArcSeg, calculates ControlPt and then passes controlPt.x, controlPt.y, XENd, yEnd, radius to f */
  def fControlEndRadius(startPt: Vec2, f: (Double, Double, Double, Double, Double) => Unit): Unit =
  { val cp = arcControlPt(startPt)
    f(cp.x, cp.y, pEnd.x, pEnd.y, arcRadius)
  }
}

/** This provides factory methods to create a LineSeg. There is no independent LineSeg class. This is one of 3 factory objects to CurveSeg. */
object LineSeg
{ def apply(pEnd: Vec2): CurveSeg =  new CurveSeg(10, 0, 0, 0, 0, pEnd.x, pEnd.y)
  def apply(xEnd: Double, yEnd: Double): CurveSeg = new CurveSeg(10, 0, 0, 0, 0, xEnd, yEnd)
}

/** This provides factory methods to create an ArcSeg. There is no independent ArcSeg class. This is one of 3 factory objects to CurveSeg. */
object ArcSeg
{ def apply(pCen: Vec2, pEnd: Vec2): CurveSeg = new CurveSeg(11, 0, 0, pCen.x, pCen.y, pEnd.x, pEnd.y)
  //def apply(xCen: Double, yCen: Double, xEnd: Double, yEnd: Double): CurveSeg = new CurveSeg(PosInf, 0, xCen, yCen, xEnd, yEnd)
}

/** This provides factory methods to create a BezierSeg. There is no independent BezierSeg class. This is one of 3 factory objects to CurveSeg. */
object BezierSeg
{ def apply(pC1: Vec2, pC2: Vec2, pEnd: Vec2): CurveSeg = new CurveSeg(12, pC1.x, pC1.y, pC2.x, pC2.y, pEnd.x, pEnd.y)
  //def apply(xC1: Double, yC1: Double, xC2: Double, yC2: Double, xEnd: Double, yEnd: Double): CurveSeg = new CurveSeg(xC1, yC1, xC2, yC2, xEnd, yEnd)
}
