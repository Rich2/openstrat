/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

class RectangleDims(width: Double, height: Double = 1)
class Rectangle(width: Double, height: Double = 1, cen: Vec2 = Vec2Z)

/** This perhaps should be changed to Rectangle. Some methods need renaming or possibly even deleting */
object Rectangle
{
  /** Defaults to a centre of x = 0, y = 0 and then defaults to a height of 1.0. Clockwise, topLeft is vertice 0. */
  def apply(width: Double, height: Double = 1, cen: Vec2 = Vec2Z): Polygon =
  { 
    val x = cen.x; val y = cen.y
    Polygon(
        x - width / 2 vv y + height / 2,
        x + width / 2 vv y + height / 2,
        x + width / 2 vv y - height / 2,
        x - width / 2 vv y - height / 2)
  }
   
  def scale(widthOverHeightRatio: Double, scale: Double, cen: Vec2 = Vec2Z): Polygon = apply(widthOverHeightRatio * scale, scale, cen)
  
  /** A rectangle measured from its top left */
  def fromTL(width: Double, height: Double, tlVec: Vec2 = Vec2Z): Polygon = Polygon(
      tlVec.x         vv tlVec.y,
      tlVec.x + width vv tlVec.y,
      tlVec.x + width vv tlVec.y - height,
      tlVec.x         vv tlVec.y -height)         
   
  def fromBL(width: Double, height: Double, v: Vec2): Polygon = Polygon(
      v.x vv v.y + height,
      v.x + width vv v.y + height,
      v.x + width vv v.y,
      v.x vv v.y)

  /** Measured from bottom centre */      
  def fromBC(width: Double, height: Double, bottomCentre: Vec2 = Vec2Z): Polygon =
  {
    val x = bottomCentre.x; val y = bottomCentre.y
    Polygon(
        x - width / 2 vv y + height ,
        x + width / 2 vv y + height ,
        x + width / 2 vv y,
        x - width / 2 vv y)
  }
 
  val goldenRatio: Double = 1.6180339887498948482
  def gRatio(height: Double): Polygon = apply(goldenRatio * height, height)
  def cross(width: Double, height: Double, barWidth: Double): Arr[Polygon] = Arr(apply(width, barWidth), apply(barWidth, height))
  
  def curvedCorners(width: Double, height: Double, radius: Double, cen: Vec2 = Vec2Z): Shape =
  { val w = width / 2
    val h = height / 2
    val s1 = Shape(
        LineSeg(w - radius,          h), ArcSeg(w - radius vv h - radius, w vv h -radius),
        LineSeg(w,          radius - h), ArcSeg(w - radius vv radius - h, w - radius vv -h),
        LineSeg(radius - w,         -h), ArcSeg(radius - w vv radius - h, -w vv radius -h),
        LineSeg(- w,        h - radius), ArcSeg(radius - w vv h - radius, radius - w vv h))
     s1.slate(cen)     
  }  

  def curvedCornersCentred(width: Double, height: Double, radius: Double, posn: Vec2 = Vec2Z): ShapeCentred =
    ShapeCentred(posn, curvedCorners(width, height, radius).slate(posn))
  def curvedGoldenRatio(height: Double, radius: Double, posn: Vec2 = Vec2Z): Shape =
    curvedCorners(height * goldenRatio, height, radius, posn)  
  def curvedGoldenRatioCentred(height: Double, radius: Double, posn: Vec2 = Vec2Z): ShapeCentred =
    curvedCornersCentred(height * goldenRatio, height, radius, posn)
  def colouredBordered(height: Double, colour: Colour, lineWidth: Double = 1): PolyFillDraw =
    gRatio(height).fillDraw(colour, lineWidth, colour.contrast)
  
  def fromAxis(centreLine: Line2, height: Double): Polygon =
  { val hAngle: Angle = centreLine.angle
    val offset: Vec2 = hAngle.toVec2 * height * 0.5
    Polygon(centreLine.pStart + offset, centreLine.pEnd + offset, centreLine.pEnd - offset, centreLine.pStart - offset)   
  }
}

object Star5
{
  def apply(): Polygon =
  {
    val l2: List[Vec2] = List(Vec2(0, 1), Vec2(0, 0.5).rotate(-deg36))
    (0 to 4).iter2ProdD2[Vec2, Vec2, Polygon](l2, (i, l) => l.rotate(-deg72 * i))
  }
  def ptUpYCentred = apply.slateY(deg36.cos / 2 - 0.5)
}


