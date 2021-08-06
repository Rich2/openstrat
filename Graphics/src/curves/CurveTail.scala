/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A CurveSeg can  be a line segment or an arc segment or a bezier segment without its starting point, which is supplied by the previous curveTail.
 *  It takes its start point from the pEnd of the previous segment. There is no CurveSeg companion object as the LineSeg, ArcSeg and BezierSeg all
 *  have their own factory object apply methods. */
case class CurveTail(val iMatch: Double, val xC1: Double, val yC1: Double, val xUses: Double, val yUses: Double, val xEnd: Double,
                     val yEnd: Double) extends ElemDbl7 /*with CurveTailLike*/ with AffinePreserve
{ override type ThisT = CurveTail
  override def canEqual(other: Any): Boolean = other.isInstanceOf[CurveTail]
  @inline override def dbl1 = iMatch
  @inline override def dbl2 = xC1
  @inline override def dbl3 = yC1
  @inline override def dbl4 = xUses
  @inline override def dbl5 = yUses
  @inline override def dbl6 = xEnd
  @inline override def dbl7 = yEnd

  /** The end point. Often called p2 on a line or p4 on a cubic bezier. */
  def pEnd: Pt2 = xEnd pp yEnd

  /** This is control point 2 in a Bezier segment, the centre point in an arc segment and unused in a straight Line Segment */
  def pUses: Pt2 = Pt2(xUses, yUses)
  /** This is control point 1 in a Bezier segment, it not used an arc segment, but first Double set to NaN, it is not nused in a straight Line Segment
   *  but the first Double is set to Infinity */
  def pC1: Pt2 = Pt2(xC1, yC1)
      
  def fSeg[A](fLineSeg: Pt2 => A, fArcSeg: (Pt2, Pt2) => A, fBezierSeg: (Pt2, Pt2, Pt2) => A): A = iMatch match
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
   
  def segDo(fLineSeg: CurveTail => Unit, fArcSeg: CurveTail => Unit, fBezierSeg: CurveTail => Unit): Unit =  iMatch match
  { case 10 => fLineSeg(this)
    case 11 => fArcSeg(this)
    case 12 => fBezierSeg(this)
    case n => excep("iMatch in LineSeg has value: " + n.toString + " Must be 10, 11 0r 12.")
  }
   
  override def ptsTrans(f: Pt2 => Pt2): CurveTail = iMatch match
  {
    case 10 => { val newPEnd: Pt2 = f(pEnd); new CurveTail(10, 0, 0, 0, 0, newPEnd.x, newPEnd.y) }
    
    case 11 =>
    { val newPCen: Pt2 = f(pUses)
      val newPEnd: Pt2 = f(pEnd)
      new CurveTail(11, 0, 0, newPCen.x, newPCen.y, newPEnd.x, newPEnd.y)
    }
    
    case 12 =>
    { val newPC1: Pt2 = f(pC1)
      val newPCen: Pt2 = f(pUses)
      val newPEnd: Pt2 = f(pEnd)
      new CurveTail(12, newPC1.x, newPC1.y, newPCen.x, newPCen.y, newPEnd.x, newPEnd.y)
    }
    
    case n => excep("iMatch in LineSeg has value: " + n.toString + " Must be 10, 11 0r 12.")
  }
  
  /** Assuming this is Arc Segment tail. */
  def arcCen: Pt2 = Pt2(xUses, yUses)
  
  /** Assuming this is Arc Segment */
  def arcStartAngle(pStart: Pt2): Angle = arcCen.angleTo(pStart)
  
  /** Assuming this is Arc Segment */
  def arcEndAngle: Angle = arcCen.angleTo(pEnd)
  
  /** Assuming this is Arc Segment, not sure about this. */
  def arcRadius: Double = pEnd.distTo(arcCen)
  
  /** Assuming this is Arc Segment */
  def arcControlPt(startPt: Pt2): Pt2 =
  { val sAng: Angle = arcStartAngle(startPt)
    val resultAngle = sAng.bisectNeg(arcEndAngle)
    val alphaAngle =  sAng.deltaNegTo(arcEndAngle) / 2
    arcCen + resultAngle.toVec2(arcRadius) / alphaAngle.cos
  }
  
  /** Assuming this is ArcSeg, calculates ControlPt and then passes controlPt.x, controlPt.y, XENd, yEnd, radius to f */
  def fControlEndRadius(startPt: Pt2, f: (Double, Double, Double, Double, Double) => Unit): Unit =
  { val cp = arcControlPt(startPt)
    f(cp.x, cp.y, pEnd.x, pEnd.y, arcRadius)
  }
}

/** This provides factory methods to create a 2 dimensional headless line segment. There is no independent LineTail class. This is one of 3 factory
 *  objects to CurveTail. */
object LineTail
{ def apply(pEnd: Pt2): CurveTail =  new CurveTail(10, 0, 0, 0, 0, pEnd.x, pEnd.y)
  def apply(xEnd: Double, yEnd: Double): CurveTail = new CurveTail(10, 0, 0, 0, 0, xEnd, yEnd)
}

/** This provides factory methods to create an ArcTail. There is no independent ArcTail class. This is one of 3 factory objects to CurveTail. */
object ArcTail
{ def apply(pCen: Pt2, pEnd: Pt2): CurveTail = new CurveTail(11, 0, 0, pCen.x, pCen.y, pEnd.x, pEnd.y)
  //def apply(xCen: Double, yCen: Double, xEnd: Double, yEnd: Double): CurveSeg = new CurveSeg(PosInf, 0, xCen, yCen, xEnd, yEnd)
}

/** This provides factory methods to create a Bezier tail. There is no independent BezierTail class. This is one of 3 factory objects to CurveTail. */
object BezierTail
{ def apply(pC1: Pt2, pC2: Pt2, pEnd: Pt2): CurveTail = new CurveTail(12, pC1.x, pC1.y, pC2.x, pC2.y, pEnd.x, pEnd.y)
  //def apply(xC1: Double, yC1: Double, xC2: Double, yC2: Double, xEnd: Double, yEnd: Double): CurveSeg = new CurveSeg(xC1, yC1, xC2, yC2, xEnd, yEnd)
}