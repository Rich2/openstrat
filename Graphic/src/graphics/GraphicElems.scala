/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv.CanvasPlatform

/** The base trait for all objects on a canvas / panel. The objects are re-composed for each frame. The Canvas objects must be re-composed
 *  each time there is a change within the application state or the user view of that application state. */
trait GraphicElem extends Transer

trait GraphicBounded extends GraphicElem
{ /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def width: Double = boundingRect.width
}

/* Base trait for all passive objects on a canvas / panel */
trait PaintElem extends GraphicElem
{ def rendElem(cp: CanvasPlatform): Unit
}