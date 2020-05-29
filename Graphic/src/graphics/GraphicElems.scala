/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A GraphicElem is either an element that can be rendered to a display (or printed) or is an active element in display or both. */
sealed trait GraphicElem extends GeomElem
{
  
}

object GraphicElem
{
  implicit def transImplicit: TransAlign[GraphicElem] = new TransAlign[GraphicElem] {
    override def slate(obj: GraphicElem, offset: Vec2): GraphicElem = obj match {
      case ta: TransAligner => ta.slate(offset).asInstanceOf[GraphicElem]
      case gea: GraphicElemNew => ??? // gea.slate(offset)
    }

    override def scale(obj: GraphicElem, operand: Double): GraphicElem = obj match
    { case ta: TransAligner => ta.scale(operand).asInstanceOf[GraphicElem]
      case gea: GraphicElemNew => ??? // gea.scale(operand)
    }
  }
}
/** This trait is slated for removal. */
trait GraphicElemOld extends TransSimer with GraphicElem
{ type AlignT <: GraphicElemOld
}

trait GraphicElemNew extends GraphicElem with GeomElem
{
  //override def fTrans(f: Vec2 => Vec2): GraphicElemNew

  //override def slate(offset: Vec2): GraphicElemNew = fTrans(_ + offset)

 // override def scale(operand: Double): GraphicElemNew = fTrans(_ * operand)
}

/** The base trait for all objects on a canvas / panel. The objects are re-composed for each frame. The Canvas objects must be re-composed
 *  each time there is a change within the application state or the user view of that application state. */
trait GraphicFullElem extends GraphicElemOld with TransAller
{ type AlignT <: GraphicFullElem
}

/** A GraphicElem is either an element that can be rendered to a display or printed. */
trait PaintElem extends GraphicElem
{
  /** Renders this functional immutable Graphic PaintElem, using the imperative methods of the abstract [[ostrat.pCanv.CanvasPlatform]] interface. */
  def rendToCanvas(cp: pCanv.CanvasPlatform): Unit
}

trait PaintElemNew extends PaintElem with GraphicElemNew
{
  //override def slate(xOffset: Double, yOffset: Double): PaintElemNew
}

/** Trait to be removed. */
trait PaintElemOld extends GraphicElemOld with PaintElem
{ //type AlignT <: PaintElemOld

}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBounded extends GraphicElemOld
{ type AlignT <: GraphicBounded
  /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
  def boundingRect: BoundingRect
  def width: Double = boundingRect.width
}

/** This trait is for layout. For placing Graphic elements in rows and columns. It includes polygon and shape graphics but not line and curve
 *  graphics. */
trait GraphicBoundedFull extends GraphicBounded with GraphicFullElem
{ type AlignT <: GraphicBoundedFull
  /** The bounding Rectangle provides an initial exclusion test as to whether the pointer is inside the polygon / shape */
 // def boundingRect: BoundingRect
  //def width: Double = boundingRect.width
}

/** Base trait for all child (non Parent) Graphic elements that output to the display. */
trait PaintFullElem extends PaintElemOld with GraphicFullElem
{ type AlignT <: PaintFullElem
  //override def fTrans(f: Vec2 => Vec2): PaintFullElem

}

trait FilledElem extends PaintElemNew
{ def fillColour: Colour
}

trait FillElem extends FilledElem

trait Fillable extends GeomElem
{ def fill(colour: Colour): FillElem
}