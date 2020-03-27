/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** The base trait for all objects that can have mouse / touch pad interaction. */
trait GraphicActive extends GraphicBounded
{
  /** The Pointer Identity is returned to the GUI applicaton if the user mouse (or other pointing device, clicks within the polygon or shape It is
      purely up to the application to encode, its response if any to this object. */
  def pointerId: Any

  /** The definitive test as to whether the mouse pointer is inside the polygon / shape */
  def ptInside(pt: Vec2): Boolean
  override def fTrans(f: Vec2 => Vec2): GraphicActive
}

/** An active transparent pointable polygon */
trait PolyActive extends GraphicActive
{ def poly: Polygon
  override def boundingRect = poly.boundingRect
  override def ptInside(pt: Vec2): Boolean = poly.ptInPolygon(pt)
  override def fTrans(f: Vec2 => Vec2): PolyActive
}

/** A pointable shape */
trait ShapeActive extends GraphicActive
{ def shape: Shape
  def innerPoly: Polygon = shape.pMap(_.pEnd)
  override def boundingRect: BoundingRect = innerPoly.boundingRect

  /** This method needs improving. */
  override def ptInside(pt: Vec2): Boolean = innerPoly.ptInPolygon(pt)
  override def fTrans(f: Vec2 => Vec2): ShapeActive
}