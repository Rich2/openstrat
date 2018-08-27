/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** This perhaps should be changed to Rectangle. Some methods need renaming or possibly even deleting */
object Rectangle
{
   /** Defaults to a centre of x = 0, y = 0 and then defaults to a height of 1.0 */
   def apply(width: Double, height: Double, cen: Vec2 = Vec2Z): Vec2s =
   {
      val x = cen.x; val y = cen.y
      Vec2s(
         x - width / 2 vv y + height / 2,
         x + width / 2 vv y + height / 2,
         x + width / 2 vv y - height / 2,
         x - width / 2 vv y - height / 2)
      }
  
  /** A rectangle measured from its top left */
  def fromTL(width: Double, height: Double, tlVec: Vec2 = Vec2Z): Vec2s = Vec2s(
         tlVec.x         vv tlVec.y,
         tlVec.x + width vv tlVec.y,
         tlVec.x + width vv tlVec.y - height,
         tlVec.x         vv tlVec.y -height)         
   
//   def tR(x: Double, y: Double, width: Double, height: Double): Vec2s = Vec2s.xy(x - width, y, x, y, x, y - height, x - width, y -height)
//   def bR(x: Double, y: Double, width: Double, height: Double): Vec2s = Vec2s.xy(x - width, y + height, x, y + height, x, y, x - width, y)
   def fromBL(width: Double, height: Double, v: Vec2): Vec2s = Vec2s(v.x vv v.y + height,
         v.x + width vv v.y + height,
         v.x + width vv v.y,
         v.x vv v.y)
//   def bL(x: Double, y: Double, width: Double, height: Double): Vec2s = Vec2s.xy(x, y + height, x + width, y + height, x + width, y, x, y)
//
   /** Measured from bottom centre */      
   def fromBC(width: Double, height: Double, bottomCentre: Vec2 = Vec2Z): Vec2s =
   {
      val x = bottomCentre.x; val y = bottomCentre.y
      Vec2s(
         x - width / 2 vv y + height ,
         x + width / 2 vv y + height ,
         x + width / 2 vv y,
         x - width / 2 vv y)
   }
//   def fromBottomCentre(width: Double, height: Double, bottomCentre: Vec2 = Vec2Z): Vec2s = Vec2s.xy(
//         - width / 2, + height,
//         + width / 2, + height ,
//         + width / 2, 0,
//         - width / 2, 0)   
 
   val goldenRatio: Double = 1.6180339887498948482
   def gRatio(height: Double): Vec2s = apply(goldenRatio * height, height)
   def cross(width: Double, height: Double, barWidth: Double): List[Vec2s] = List(apply(width, barWidth), apply(barWidth, height))
   def curvedSegs(width: Double, height: Double, radius: Double): List[CurveSeg] =
   {
      val w = width / 2
      val h = height / 2
      List(LineSeg(w - radius,          h), ArcSeg(w - radius, h - radius, w,          h -radius),
           LineSeg(w,          radius - h), ArcSeg(w - radius, radius - h,    w - radius, -h       ),
           LineSeg(radius - w,         -h), ArcSeg(radius - w, radius - h,      -w,         radius -h),
           LineSeg(- w,        h - radius), ArcSeg(radius - w, h - radius,   radius - w, h         ))            
   }   
   def curved(width: Double, height: Double, radius: Double, posn: Vec2 = Vec2Z): Shape = Shape(posn, curvedSegs(width, height, radius).slate(posn))   
   def curvedgGoldenRatio(height: Double, radius: Double): Shape = curved(height * goldenRatio, height, radius)
   def colouredBordered(height: Double, colour: Colour, lineWidth: Double = 1): PolyFillDraw =
      gRatio(height).fillDraw(colour, lineWidth, colour.contrast)
   def fromAxis(centreLine: Line2, height: Double): Vec2s =
   {
      val hAngle: Angle = centreLine.lineAngle
      val offset: Vec2 = hAngle.toVec2 * height * 0.5
      Vec2s(centreLine.pStart + offset, centreLine.pEnd + offset, centreLine.pEnd - offset, centreLine.pStart - offset)   
   }
}

object Star5
{
   def apply(): Vec2s =
   {
      val l2: List[Vec2] = List(Vec2(0, 1), Vec2(0, 0.5).rotate(-deg36))
      (0 to 4).trav2ProdD2[Vec2, Vec2, Vec2s](l2, (i, l) => l.rotate(-deg72 * i))
   }
   def ptUpYCentred = apply.slateY(deg36.cos / 2 - 0.5)
}


