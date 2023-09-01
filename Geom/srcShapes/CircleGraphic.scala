/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui._, Colour.Black, pWeb._

/** A circle based Graphic, may be simple or compound. */
trait CircleGraphic extends EllipseGraphic
{ override def shape: Circle

  /** The radius of this circle graphic. */
  @inline final def radius: Double = shape.radius

  /** The diameter of this circle graphic. */
  @inline final def diameter: Double = shape.diameter
}

/** A Simple circle based graphic. */
trait CircleGraphicSimple extends CircleGraphic with EllipseGraphicSimple
{ type ThisT <: CircleGraphicSimple
  final override def svgElem: SvgCircle = SvgCircle(attribs)
}

/** A simple single colour fill of a circle graphic. */
final case class CircleFill(shape: Circle, fill: FillFacet) extends CircleGraphicSimple with EllipseFill with CanvElem
{ override type ThisT = CircleFill
  override def ptsTrans(f: Pt2 => Pt2): ThisT = CircleFill(shape.fTrans(f), fill)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)
  override def toDraw(lineWidth: Double = 2, newColour: Colour = Black): CircleDraw = shape.draw(lineWidth, newColour)
}

/** A simple draw of a circle graphic. */
final case class CircleDraw(shape: Circle, lineWidth: Double = 2.0, lineColour: Colour = Black) extends CircleGraphicSimple with EllipseDraw
{ type ThisT = CircleDraw
  override def ptsTrans(f: Pt2 => Pt2): CircleDraw = CircleDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDraw(this)
}

/** A pointable polygon without visual. */
case class CircleActive(shape: Circle, pointerId: Any) extends EllipseActive with CircleGraphicSimple
{ override type ThisT = CircleActive
  override def ptsTrans(f: Pt2 => Pt2): CircleActive = CircleActive(shape.fTrans(f), pointerId)

  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.") }

  override def ptInside(pt: Pt2): Boolean = shape.ptInside(pt)
}

case class CircleFillIcon(fillColour: Colour) extends ShapeFillIcon
{ override def reify(scale: Double, cen: Pt2): CircleFill = CircleFill(Circle(scale, cen), fillColour)
  override def reify(scale: Double, xCen: Double, yCen: Double): CircleFill = CircleFill(Circle(scale, xCen, yCen), fillColour)
}