/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** The base trait for all objects that can have mouse / touch pad interaction. */
trait DisplayActive extends DisplayBounded
{ type ThisT <: DisplayActive
  /** The Pointer Identity is returned to the GUI applicaton if the user mouse (or other pointing device, clicks within the polygon or shape It is
      purely up to the application to encode, its response if any to this object. */
  def pointerId: Any

  /** The definitive test as to whether the mouse pointer is inside the polygon / shape */
  def ptInside(pt: Vec2): Boolean
  // override def fTrans(f: Vec2 => Vec2): ThisT
}

/** The base trait for all objects that can have mouse / touch pad interaction. */
trait DisplayActiveFull extends DisplayActive with DisplayBoundedFull
{ type ThisT <: DisplayActiveFull
  /** The Pointer Identity is returned to the GUI applicaton if the user mouse (or other pointing device, clicks within the polygon or shape It is
      purely up to the application to encode, its response if any to this object. */
  def pointerId: Any

  /** The definitive test as to whether the mouse pointer is inside the polygon / shape */
  def ptInside(pt: Vec2): Boolean
 // override def fTrans(f: Vec2 => Vec2): ThisT
}

/** An active transparent pointable polygon */
trait PolyActiveFull extends DisplayActiveFull
{ type ThisT <: PolyActiveFull
  def shape: PolygonClass
  override def boundingRect = shape.boundingRect
  override def ptInside(pt: Vec2): Boolean = shape.ptInPolygon(pt)
  //override def fTrans(f: Vec2 => Vec2): ThisT
}

/** A pointable shape */
trait ShapeActive extends DisplayActiveFull
{  type ThisT <: ShapeActive
  def shape: PolyCurve
  def innerPoly: PolygonClass = shape.pMap(_.pEnd)
  override def boundingRect: BoundingRect = innerPoly.boundingRect

  /** This method needs improving. */
  override def ptInside(pt: Vec2): Boolean = innerPoly.ptInPolygon(pt)
  //override def fTrans(f: Vec2 => Vec2): ShapeActive
}