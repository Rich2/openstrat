/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait GraphicElem extends TransSimer
{ type ThisT <: GraphicElem
}
/** The base trait for all objects on a canvas / panel. The objects are re-composed for each frame. The Canvas objects must be re-composed
 *  each time there is a change within the application state or the user view of that application state. */
trait GraphicFullElem extends GraphicElem with TranserAll
{ type ThisT <: GraphicFullElem
}

trait PaintElem extends GraphicElem
{ type ThisT <: PaintElem
  /** Renders this functional immutable Graphic PaintElem, using the imperative methods of the abstract [[ostrat.pCanv.CanvasPlatform]] interface. */
  def rendToCanvas(cp: pCanv.CanvasPlatform): Unit
}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBounded extends GraphicFullElem
{ type ThisT <: GraphicBounded
  /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def width: Double = boundingRect.width
}

/** Base trait for all child (non Parent) Graphic elements that output to the display. */
trait PaintFullElem extends PaintElem with GraphicFullElem
{ type ThisT <: PaintFullElem
  //override def fTrans(f: Vec2 => Vec2): PaintFullElem

}