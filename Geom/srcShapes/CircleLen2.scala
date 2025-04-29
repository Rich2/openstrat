/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

/** A circle defined in [[Length]] units. */
final class CircleLen2 protected[geom](radius: Length, cenX: Length, cenY: Length) extends EllipseLen2
{ type ThisT = CircleLen2
  override def slate(operand: VecPtLen2): CircleLen2 = CircleLen2(radius, cenX + operand.x, cenY + operand.y)
  override def slate(xOperand: Length, yOperand: Length): CircleLen2 = CircleLen2(radius, cenX + xOperand, cenY + yOperand)
  override def slateX(xOperand: Length): CircleLen2 = CircleLen2(radius, cenX + xOperand, cenY)
  override def slateY(yOperand: Length): CircleLen2 = CircleLen2(radius, cenX, cenY + yOperand)
  override def scale(operand: Double): CircleLen2 = CircleLen2(radius, cenX * operand, cenY * operand)
  override def mapGeom2(operand: Length): Circle = Circle(radius / operand, cenX / operand, cenY / operand)
  override def draw(lineWidth: Double, lineColour: Colour): CircleLen2Draw = CircleLen2Draw(this, lineWidth, lineColour)
  override def fill(fillFacet: FillFacet): CircleLen2Fill = CircleLen2Fill(this, fillFacet)

  override def fillDraw(fillFacet: FillFacet, lineColour: Colour = Black, lineWidth: Double = 2.0): CircleLen2Compound =
    CircleLen2Compound(this, RArr(fillFacet, DrawFacet(lineColour, lineWidth)), RArr())
}

/** Companion object for circle defined in [[Length]] units, contains factory methods. */
object CircleLen2
{ /** Factory apply method for creating a circle with [[Length]] units. The first parameter gives the radius of the circle. The default centre is at the origin.
 * There is an apply name overload that takes the X and Y centre [[Length]] values as parameters There are corresponding d methods that take a diameter as the
 * first parameter. */
  def apply(radius: Length, cenX: Length, cenY: Length): CircleLen2 = new CircleLen2(radius, cenX, cenY)

  /** Factory apply method for creating a circle with [[Length]] units. The first parameter gives the radius of the circle, followed by the X and Y centre
   * [[Length]] values. There is an apply method name overload that takes a [[PtLen2]] as a second parameter with a default value of the origin. */
  def apply(radius: Length, cen: PtLen2 = PtM2.origin): CircleLen2 = new CircleLen2(radius, cen.x, cen.y)

  /** Factory method for creating a circle. The first parameter gives the diameter of the circle. The default centre is at the origin. There is a name
   * overload that takes the X and Y centre values as parameters. There are corresponding to apply methods that take a radius as the first parameter. */
  def d(diameter: Length, cenX: Length, cenY: Length): CircleLen2 = new CircleLen2(diameter / 2, cenX, cenY)

  /** Factory method for creating a circle with [[Length]] units. The first parameter gives the diameter of the circle, followed by the X and Y centre values.
   * There is a method name overload that takes a [[PtLen2]] as a second parameter with a default value of the origin. There are corresponding to apply methods
   * that take a radius as the first parameter. */
  def d(diameter: Length, cen: PtLen2): CircleLen2 = new CircleLen2(diameter / 2, cen.x, cen.y)
}

trait CircleLen2Graphic extends ShapeLen2Graphic
{ override def shape: CircleLen2
}

case class CircleLen2Draw(shape: CircleLen2, lineWidth: Double = 2.0, lineColour: Colour = Black) extends ShapeLen2Draw with CircleLen2Graphic
{ override def slate(operand: VecPtLen2): CircleLen2Draw = CircleLen2Draw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Length, yOperand: Length): CircleLen2Draw = CircleLen2Draw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def slateX(xOperand: Length): CircleLen2Draw = CircleLen2Draw(shape.slateX(xOperand), lineWidth, lineColour)
  override def slateY(yOperand: Length): CircleLen2Draw = CircleLen2Draw(shape.slateY(yOperand), lineWidth, lineColour)
  override def scale(operand: Double): CircleLen2Draw = CircleLen2Draw(shape.scale(operand), lineWidth, lineColour)
  override def mapGeom2(operand: Length): CircleDraw = CircleDraw(shape.mapGeom2(operand))
}

case class CircleLen2Fill(shape: CircleLen2, fillFacet: FillFacet) extends ShapeLen2Fill with CircleLen2Graphic
{ override def slate(operand: VecPtLen2): CircleLen2Fill = CircleLen2Fill(shape.slate(operand), fillFacet)
  override def slate(xOperand: Length, yOperand: Length): CircleLen2Fill = CircleLen2Fill(shape.slate(xOperand, yOperand), fillFacet)
  override def slateX(xOperand: Length): CircleLen2Fill = CircleLen2Fill(shape.slateX(xOperand), fillFacet)
  override def slateY(yOperand: Length): CircleLen2Fill = CircleLen2Fill(shape.slateY(yOperand), fillFacet)
  override def scale(operand: Double): CircleLen2Fill = CircleLen2Fill(shape.scale(operand), fillFacet)
  override def mapGeom2(operand: Length): CircleFill = CircleFill(shape.mapGeom2(operand), fillFacet)
}

case class CircleLen2Compound(shape: CircleLen2, facets: RArr[GraphicFacet], fChilds: RArr[Circle => Graphic2Elem]) extends CircleLen2Graphic, ShapeLen2Compound
{ override def slate(operand: VecPtLen2): CircleLen2Compound = CircleLen2Compound(shape.slate(operand), facets, fChilds)
  override def slate(xOperand: Length, yOperand: Length): CircleLen2Compound = CircleLen2Compound(shape.slate(xOperand, yOperand), facets, fChilds)
  override def slateX(xOperand: Length): CircleLen2Compound = CircleLen2Compound(shape.slateX(xOperand), facets, fChilds)
  override def slateY(yOperand: Length): CircleLen2Compound = CircleLen2Compound(shape.slateY(yOperand), facets, fChilds)
  override def scale(operand: Double): CircleLen2Compound = CircleLen2Compound(shape.scale(operand), facets, fChilds)
  override def mapGeom2(operand: Length): CircleCompound = CircleCompound(shape.mapGeom2(operand), facets, fChilds)
}