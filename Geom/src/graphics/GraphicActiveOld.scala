/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait GraphicActive extends GraphicBounded
{ /** The Pointer Identity is returned to the GUI application if the user mouse (or other pointing device, clicks within the polygon or shape It is
      purely up to the application to encode, its response if any to this object. */
  def pointerId: AnyRef

  /** The definitive test as to whether the mouse pointer is inside the polygon / shape */
  def ptInside(pt: Pt2): Boolean
}

/** This is the new active trait that will replace GraphicActive. */
trait GraphicClickable extends GraphicActive

/** This trait will be removed. The base trait for all objects that can have mouse / touch pad interaction. */
trait GraphicActiveOld extends GraphicActive
{
}

/** The base trait for all objects that can have mouse / touch pad interaction. */
trait GraphicActiveSim extends GraphicBoundedSimer with GraphicActiveOld
{ type ThisT <: GraphicActiveSim
}

/** A pointable shape */
trait PolyCurveActive extends GraphicActiveSim with GraphicBoundedAffine
{ type ThisT <: PolyCurveActive
  def shape: ShapeGenOld
  def innerPoly: Polygon = shape.mapPolygon(_.pEnd)
  override def boundingRect: BoundingRect = innerPoly.boundingRect

  /** This method needs improving. */
  override def ptInside(pt: Pt2): Boolean = innerPoly.ptInside(pt)
}